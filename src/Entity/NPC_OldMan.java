package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity {
    int start = 10;
    int secondBattle = 11;
    public static final String NPCname = "Old Man";
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        name = NPCname;
        speakState = start;
        direction = "down";
        playerSpeed = 1;
        dialougeSet = -1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        down1 = setup("npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("npc/oldman_down_2",gp.tileSize,gp.tileSize);
        up1 = setup("npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("npc/oldman_right_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        //IMPLEMENT NEW DIALOGUES
            dialogues[0][0] = "Hello traveler.";
            dialogues[0][1] = "Welcome to the island of treasure";
            dialogues[0][2] = "I've been a guide for\nadventurers all around the world.";
            dialogues[0][3] = "To get to the legendary blue heart\nYou need to explore the\ndepths of the dungeon.";
            dialogues[0][4] = "Fear not, for I will help you.";
            dialogues[0][5] = "There are two doors required\nto pass to get to the dungeon,\neach needing a key";
            dialogues[0][6] = "Below are some low level slime.\nTrain up, gain some\ncoins and buy items\nfor battle.";
            dialogues[0][7] = "Use your minimap for\nrefrence [X for minimap]";
            dialogues[0][8] = "Once your ready, head\nnorth west for your first battle.";
            dialogues[0][10] = "Head over to the\npool of life above to\nrespawn slimes\nand save progress";

            dialogues[1][0] = "To the left,\nopen the first door.";
            dialogues[1][1] = "You'll face the orcs of power.";
            dialogues[1][2] = "Beating them will\ngrant you access to the dungeon.";





    }

    public void setAction() {

        if(onPath == true){
            int goalCol = 12;
            int goalRow = 9;
            goalCol = (gp.player.worldX + gp.player.solidArea.x)/ gp.tileSize;
            goalRow = (gp.player.worldY + gp.player.solidArea.y)/ gp.tileSize;;
            searchPath(goalCol,goalRow);
        }
        else {
            actionLockCounter++;

            if (actionLockCounter == 120) { //changes every 120 frames
                Random random = new Random();
                int i = random.nextInt(100) + 1; //number form 1 to 100

                //change direction randomly
                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }

                actionLockCounter = 0;

            }
        }

    }

    public void speak() {
        //character specific features possibly
        if(gp.player.killedCounter == 4){
            speakState = secondBattle;
        }
        facePlayer();
        startDialogue(this,dialougeSet);
        dialougeSet++;
        if(dialogues[dialougeSet][dialogueIndex] == null) {
            dialougeSet--;
        }
        }

        //onPath = true;
    }
