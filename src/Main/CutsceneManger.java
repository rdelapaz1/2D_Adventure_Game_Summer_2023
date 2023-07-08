package Main;

import Entity.Particle;
import Entity.fakePlayer;
import Monster.MON_Skeleton;
import Monster.MON_redSlime;
import Objects.OBJ_BlueHeart;
import Objects.OBJ_Chest;
import Objects.OBJ_IronDoor;
import Objects.OBJ_Key;

import java.awt.*;

public class CutsceneManger {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum = 0;
    public int scenePhase = 0;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;

    //SCENE NUMBER
    public final int NA = 0; //no scene, game state
    public final int skeletonScene = 1; //boss battle
    public final int ending = 2;
    public final int fight1 = 3;

    public CutsceneManger(GamePanel gp){
        this.gp = gp;
        endCredit = "2023 Summer Programming Project\n" +
                "Special thanks to RySnow, \nwho's guide made this possible\n" +
                "Thank you for playing!!!";
    }

    public void redSlimeScene(){
        //close door
        if(scenePhase == 0){
            for (int i = 0; i < gp.superObject[1].length; i++) {
                if (gp.superObject[gp.currentMap][i] == null) {
                    gp.superObject[gp.currentMap][i] = new OBJ_IronDoor(gp);
                    gp.superObject[gp.currentMap][i].worldX = gp.tileSize * 38;
                    gp.superObject[gp.currentMap][i].worldY = gp.tileSize * 13;
                    gp.superObject[gp.currentMap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }
            scenePhase++;
        }
        //set redslimes
        if(scenePhase == 1){
            gp.assetSetter.setRedSlimes();
        }
        //defeat redSlimes
       if(scenePhase == 2) {
           System.out.println("ACCOMPLISHED");
               //create chest
               for (int i = 0; i < gp.superObject[1].length; i++) {
                   if (gp.superObject[gp.currentMap][i] == null) {
                       gp.superObject[gp.currentMap][i] = new OBJ_Chest(gp);
                       gp.superObject[gp.currentMap][i].setLoot(new OBJ_Key(gp));
                       gp.superObject[gp.currentMap][i].worldX = gp.tileSize * 38;
                       gp.superObject[gp.currentMap][i].worldY = gp.tileSize * 8;
                       gp.superObject[gp.currentMap][i].temp = true;
                       break;
                   }
               }
               for(int i = 0; i < gp.superObject[1].length; i++){
                   if(gp.superObject[gp.currentMap][i] != null && gp.superObject[gp.currentMap][i].name.equals(OBJ_IronDoor.objName)){
                       gp.playSE(21);
                       gp.superObject[gp.currentMap][i] = null;
                       break;
                   }
               }
           scenePhase = 0;
           sceneNum = NA;
        }


    }

    public void bossScene() {

        if (scenePhase == 0) {
            //setting iron doors
            gp.bossbattleStart = true;
            gp.stopMusic();

            for (int i = 0; i < gp.superObject[1].length; i++) {
                if (gp.superObject[gp.currentMap][i] == null) {
                    gp.superObject[gp.currentMap][i] = new OBJ_IronDoor(gp);
                    gp.superObject[gp.currentMap][i].worldX = gp.tileSize * 25;
                    gp.superObject[gp.currentMap][i].worldY = gp.tileSize * 28;
                    gp.superObject[gp.currentMap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }

            //Add fake player, moving screen
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new fakePlayer(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }
            gp.player.drawing = false;
            scenePhase++;

        }
        if(scenePhase == 1){
            gp.player.worldY -= 2;
            if(gp.player.worldY < gp.tileSize * 16){
                scenePhase++;
            }
        }

        if(scenePhase == 2){
            for(int i = 0; i < gp.monster[1].length; i++){
                if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_Skeleton.monsterName)){
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3){
            gp.ui.drawDialogueScreen();

        }
        if(scenePhase == 4){
            //return camera to player
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(fakePlayer.npcName)){
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    gp.npc[gp.currentMap][i] = null;
                    break;
                }
            }
            gp.player.drawing = true;
            //reset phase and num
            scenePhase = NA;
            sceneNum = 0;
            gp.gameState = gp.playState;
            gp.stopMusic();
            gp.playMusic(22);
        }
    }


    public void endingScene(){
        if(scenePhase == 0){
            gp.stopMusic();
            gp.ui.npc = new OBJ_BlueHeart(gp);
            scenePhase++;
        }

        if(scenePhase == 1){
            //dialogue
            gp.ui.drawDialogueScreen();
        }

        if(scenePhase == 2){
            gp.playSE(4);
            scenePhase++;
        }

        if(scenePhase == 3){
            //SE finishes
            if(sceneCounter(300)){ //5 second
                scenePhase++;
            }
        }

        if(scenePhase == 4){
            //screen fades to black
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }
            drawBlackBackground(alpha);
            if(alpha == 1f){
                alpha = 0;
                scenePhase++;
            }
        }

        if(scenePhase == 5){
            drawBlackBackground(1f);
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }
            String text = "After a long journey, the battle ends.\n" +
                    "Blue Boy finally unlock the legendary tressure.\n" +
                    "But can he do it faster next time?";
            drawString(200,alpha,30f,70,text);
            //wait
            if(sceneCounter(600)){
                gp.playMusic(0);
                scenePhase++;
            }
        }

        if(scenePhase == 6){
            drawBlackBackground(1f);
            drawString(gp.screenHeight/2, 1f,36f,40, "Blue Boy Adventure");

            if(sceneCounter(480)){
                scenePhase++;
            }
        }

        if(scenePhase == 7){
            drawBlackBackground(1f);
            //end credits
             drawString(gp.screenHeight/2,1f,30,40,endCredit);
            if(sceneCounter(480)){
                System.exit(0);
            }
        }


    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        switch (sceneNum){
            case skeletonScene: bossScene(); break;
            case fight1: redSlimeScene(); break;
            case ending: endingScene(); break;

        }
    }

    public boolean sceneCounter(int interval){
        counter++;
        boolean reached = false;
        if(counter > interval){
            reached = true;
            counter = 0;
        }
        return reached;
    }

    public void drawBlackBackground(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g2.setColor(Color.black);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

    }

    public void drawString(int y, float alpha, float fontSize, int lineHieght, String text){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for(String line: text.split("\n")){
            int x = gp.ui.getXforCenterText(line);
            g2.drawString(line,x,y);
            y += lineHieght;
        }
    }

}
