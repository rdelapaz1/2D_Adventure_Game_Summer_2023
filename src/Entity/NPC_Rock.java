package Entity;

import Main.GamePanel;
import Objects.OBJ_Door;
import Objects.OBJ_IronDoor;
import Tiles_Interactive.IT_MetalPlate;
import Tiles_Interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class NPC_Rock extends  Entity{
    public static final String NPCName = "Big Rock";
    public NPC_Rock(GamePanel gp) {
        super(gp);
        name = NPCName;
        direction = "down";
        playerSpeed = 4;
        dialougeSet = -1;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;

        getImage();
        setDialogue();
    }

    public void getImage() {
        down1 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        down2 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        up1 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        up2 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        left1 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        left2 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        right1 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
        right2 = setup("npc/bigrock",gp.tileSize,gp.tileSize);
    }

    public void update(){

    }

    public void move(String direction){
        this.direction = direction;
        checkCollision();
        if(collisionOn ==false){
            switch (direction){
                case "up": worldY-= playerSpeed; break;
                case "down": worldY += playerSpeed; break;
                case "left": worldX -= playerSpeed; break;
                case "right": worldX += playerSpeed; break;
            }
        }
        touchesPlate();
    }

    public void setDialogue(){
        //IMPLEMENT NEW DIALOGUES
        dialogues[0][0] = "Giant Rock.";

    }

    public void setAction() {

    }

    public void speak() {
        //character specific features possibly
        facePlayer();
        startDialogue(this,dialougeSet);
        dialougeSet++;
        if(dialogues[dialougeSet][dialogueIndex] == null) {
            dialougeSet--;
        }
    }

    public void touchesPlate(){
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        //PLATE LIST
        for(int i = 0; i < gp.interactiveTile[1].length; i++){
            if(gp.interactiveTile[gp.currentMap][i] != null &&
                    gp.interactiveTile[gp.currentMap][i].name != null &&
                    gp.interactiveTile[gp.currentMap][i].name.equals(IT_MetalPlate.itemName)){
                plateList.add(gp.interactiveTile[gp.currentMap][i]);
            }
        }
        //ROCK LIST
        for(int i = 0; i < gp.npc[1].length; i++){
            if(gp.npc[gp.currentMap][i] != null &&
                    gp.npc[gp.currentMap][i].name != null &&
                    gp.npc[gp.currentMap][i].name.equals(NPC_Rock.NPCName)){
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;

        //CHECK PLATE LIST
        for(int i = 0; i < plateList.size(); i++){
            int xD = Math.abs(worldX - plateList.get(i).worldX);
            int yD = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xD,yD);

            if(distance < 8){
                if(entityTouching == null){
                    entityTouching = plateList.get(i);
                    gp.playSE(3);
                    System.out.println("ROCK IS TOUCHING");
                }
            }
            else{
                if(entityTouching == plateList.get(i)){
                    entityTouching = null;
                }
            }
        }

        //CHECK ROCK
        for(int i = 0; i < rockList.size(); i++){
            if(rockList.get(i).entityTouching != null){
                count++;
            }
        }
        //check counter, open door
        if(count == rockList.size()){
            for(int i = 0; i < gp.superObject[1].length; i++){
                if(gp.superObject[gp.currentMap][i] != null && gp.superObject[gp.currentMap][i].name.equals(OBJ_IronDoor.objName)){
                    gp.superObject[gp.currentMap][i] = null;
                    gp.playSE(21);
                }
            }
        }
    }
}
