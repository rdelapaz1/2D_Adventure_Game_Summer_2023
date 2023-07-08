package Monster;

import Data.Progress;
import Entity.Entity;
import Main.GamePanel;
import Objects.*;

import java.util.Random;

public class MON_Skeleton extends Entity {
    public static final String monsterName = "Skeleton";
    public MON_Skeleton(GamePanel gp){
        super(gp);
        //basic stats
        name = monsterName;
        defaultSpeed = 1;
        playerSpeed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        type = type_monster;
        attack = 10;
        defense = 0;
        xp = 50;
        knockBackPower = 5;
        sleep = true;
        //solid area
        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
        boss = true;
        //atack area
        attackArea.width = 170;
        attackArea.height = 170;
        attack_motion1_duration = 25;
        attack_motion2_duration = 75;
        //call image
        getImage();
        getAttackImage();
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You won't make out alive.";
        dialogues[0][1] = "Im a big body.";
        dialogues[0][2] = "Get put on a shirt.";


    }

    public void getImage(){
        int i = 5;
        //setting direction images
        if(!rageState){
            up1 = setup("monster/skeletonlord_up_1",gp.tileSize*i,gp.tileSize*i);
            up2 = setup("monster/skeletonlord_up_2" ,gp.tileSize*i,gp.tileSize*i);
            down1 = setup("monster/skeletonlord_down_1",gp.tileSize*i,gp.tileSize*i);
            down2= setup("monster/skeletonlord_down_2",gp.tileSize*i,gp.tileSize*i);
            left1 = setup("monster/skeletonlord_left_1",gp.tileSize*i,gp.tileSize*i);
            left2 = setup("monster/skeletonlord_left_2",gp.tileSize*i,gp.tileSize*i);
            right1 = setup("monster/skeletonlord_right_1",gp.tileSize*i,gp.tileSize*i);
            right2 = setup("monster/skeletonlord_right_2",gp.tileSize*i,gp.tileSize*i);
        }
        else{
            up1 = setup("monster/skeletonlord_phase2_up_1",gp.tileSize*i,gp.tileSize*i);
            up2 = setup("monster/skeletonlord_phase2_up_2" ,gp.tileSize*i,gp.tileSize*i);
            down1 = setup("monster/skeletonlord_phase2_down_1",gp.tileSize*i,gp.tileSize*i);
            down2= setup("monster/skeletonlord_phase2_down_2",gp.tileSize*i,gp.tileSize*i);
            left1 = setup("monster/skeletonlord_phase2_left_1",gp.tileSize*i,gp.tileSize*i);
            left2 = setup("monster/skeletonlord_phase2_left_2",gp.tileSize*i,gp.tileSize*i);
            right1 = setup("monster/skeletonlord_phase2_right_1",gp.tileSize*i,gp.tileSize*i);
            right2 = setup("monster/skeletonlord_phase2_right_2",gp.tileSize*i,gp.tileSize*i);
        }

    }

    public void getAttackImage(){
        int i = 5;
        if(!rageState ){
            attackUp1 = setup("monster/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * 2 * i);
            attackUp2 = setup("monster/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown1 = setup("monster/skeletonlord_attack_down_1",gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown2 = setup("monster/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * 2 * i);
            attackRight1 = setup("monster/skeletonlord_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("monster/skeletonlord_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft1 = setup("monster/skeletonlord_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("monster/skeletonlord_attack_left_2", gp.tileSize * i * 2, gp.tileSize *i);
        }
        else{
            attackUp1 = setup("monster/skeletonlord_phase2_attack_up_1", gp.tileSize * i, gp.tileSize * 2 * i);
            attackUp2 = setup("monster/skeletonlord_phase2_attack_up_2", gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown1 = setup("monster/skeletonlord_phase2_attack_down_1",gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown2 = setup("monster/skeletonlord_phase2_attack_down_2", gp.tileSize * i, gp.tileSize * 2 * i);
            attackRight1 = setup("monster/skeletonlord_phase2_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("monster/skeletonlord_phase2_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft1 = setup("monster/skeletonlord_phase2_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("monster/skeletonlord_phase2_attack_left_2", gp.tileSize * i * 2, gp.tileSize *i);
        }

    }

    public void setAction(){

        if(rageState == false && life < maxLife/2){
            rageState = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            attack *= 2;
        }

    if(getTileDistance(gp.player) < 10){
        moveTowardPlayer(60); //checks direction every second
        }
        else {
            getRandomDirection(120);
        }

        //check attack
        if(attacking == false){
            checkAttackOrNot(60,gp.tileSize * 7, gp.tileSize * 5);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
    }

    public void checkDrop(){
        gp.bossbattleStart = false;
        Progress.skeletonBossDefeated = true;
        //restore previous music
        gp.stopMusic();
        gp.playMusic(19);
        //delete iron doors
        for(int i = 0; i < gp.superObject[1].length; i++){
            if(gp.superObject[gp.currentMap][i] != null && gp.superObject[gp.currentMap][i].name.equals(OBJ_IronDoor.objName)){
                gp.playSE(21);
                gp.superObject[gp.currentMap][i] = null;
            }
        }


        int i = new Random().nextInt(100) + 1;

        //SET DROP
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
