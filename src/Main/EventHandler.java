package Main;

import Data.Progress;
import Entity.Entity;
import Objects.OBJ_IronDoor;
import Objects.OBJ_ManaCrystal;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];
    Entity gameSpeaker;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol,tempRow;

    //Constructor
    public EventHandler(GamePanel gp){
        this.gp = gp;
        gameSpeaker = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        setDialogue();
        while(map < gp.maxMap && col <  gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23; //23 pixels
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2; //2x2 box
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void setDialogue(){
        gameSpeaker.dialogues[0][0] =  "You fall into a pit!";
        gameSpeaker.dialogues[1][0] = "You've healed and regained mana.\nProgressed saved!";
    }

    public void checkEvent() {
        //check if player character is more than 1 tile away form last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance,yDistance);
        if(distance > gp.tileSize * 2){
            canTouchEvent = true;
        }
        if(canTouchEvent == true) {
            if (hit(0,26, 16, "right") == true){damagePit(gp.dialogueState);}
            else if (hit(0,23, 12, "up") == true) {healPool(gp.dialogueState);}
            else if(hit(0,10,39,"any") == true){teleport(1,12,13,gp.indoor);}
            else if(hit(1,12,13,"any") == true){teleport(0,10,39,gp.outside);}
            else if(hit(1,12,9,"up") == true){speak(gp.npc[1][0]);}
            else if(hit(0,12,9,"any") == true){teleport(2,9,41,gp.dungeon);} // enter dungeon
            else if(hit(2,9,41,"any") == true){teleport(0,12,9,gp.outside);} // leave dungeon
            else if(hit(2,8,7,"any") == true){teleport(3,26,41,gp.dungeon);gp.player.atLastStage = true;} // leave dungeon
            else if(hit(3,26,41,"any") == true){teleport(2,8,7,gp.dungeon);} // leave dungeon
            else if(hit(3,25,27,"any") == true){triggerBossScene();} // leave dungeon
            else if(hit(0,38,12,"up") == true){gp.cutsceneManger.redSlimeScene();}

            }


        }

    //checks collision
    public boolean hit(int map, int eventCol, int eventRow, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap){
            //player's solid area
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            //eventRec's solid area
            eventRect[map][eventCol][eventRow].x = gp.tileSize * eventCol + eventRect[map][eventCol][eventRow].x;
            eventRect[map][eventCol][eventRow].y = gp.tileSize * eventRow + eventRect[map][eventCol][eventRow].y;
            //checking intersection
            if(gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow]) && eventRect[map][eventCol][eventRow].eventDone == false){
                if(gp.player.direction == reqDirection || reqDirection.contentEquals( "any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            //reset player rectangle
            gp.player.solidArea.x = gp.player.defaultSolidAreaX;
            gp.player.solidArea.y = gp.player.defaultSolidAreaY;
            //reset eventRec's rectangle
            eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
            eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
        }


        //return value
        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.playSE(6);
        gameSpeaker.startDialogue(gameSpeaker,0);
        gp.player.life--;
        if(gp.player.life < 0){
            gp.player.life = 0;
        }
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;

    }

    public void healPool(int gameState){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            gp.playSE(2);
            gameSpeaker.startDialogue(gameSpeaker,1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.assetSetter.setMonster();
            gp.saveLoad.save();
        }
    }

    public void teleport(int map, int col, int row,int area){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempRow = row;
        tempCol = col;
        gp.nextArea = area; //get area ahead
        canTouchEvent = false;
        gp.playSE(13);
    }

    public void speak(Entity entity){
        if(gp.keyH.enterPressed == true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }

    public void triggerBossScene(){
        if(!gp.bossbattleStart && !Progress.skeletonBossDefeated){
            gp.gameState = gp.custsceneState;
            gp.cutsceneManger.sceneNum = gp.cutsceneManger.skeletonScene;
        }
    }

}
