package Monster;

import Entity.Entity;
import Main.GamePanel;
import Objects.*;

import java.util.Random;

public class MON_Orc extends Entity {
    public MON_Orc(GamePanel gp){
        super(gp);
        //basic stats
        name = "Orc";
        playerSpeed = 1;
        maxLife = 12;
        life = maxLife;
        type = type_monster;
        attack = 4;
        defense = 3;
        xp = 10;
        projectile = new OBJ_Rock(gp);
        knockBackPower = 2;
        //solid area
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
        defaultSpeed = 1;
        //atack area
        attackArea.width = 48;
        attackArea.height = 48;
        attack_motion1_duration = 40;
        attack_motion2_duration = 85;
        //call image
        getImage();
        getAttackImage();
    }

    public void getImage(){
        //setting direction images
        up1 = setup("monster/orc_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("monster/orc_up_2" ,gp.tileSize,gp.tileSize);
        down1 = setup("monster/orc_down_1",gp.tileSize,gp.tileSize);
        down2= setup("monster/orc_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("monster/orc_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("monster/orc_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("monster/orc_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("monster/orc_right_2",gp.tileSize,gp.tileSize);
    }

    public void getAttackImage(){
        attackUp1 = setup("monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackRight1 = setup("monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
        attackLeft1 = setup("monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
    }

    public void setAction(){

        if(onPath == true){
            //check to see if it stop chasing
            checkStopChasing(gp.player,15,100);

            //serach direction to go
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        }
        else {
            //check if starts chasing
            startsChasing(gp.player,100,5);
            //get random path else
            getRandomDirection(120);
        }

        if(attacking == false){
            checkAttackOrNot(30,gp.tileSize * 4, gp.tileSize);
        }

    }

    public void damageReaction(){
        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100) + 1;
        gp.player.killedCounter++;
        if(gp.player.killedCounter == 6){
            for (int j = 0; j < gp.superObject[1].length; j++) {
                if (gp.superObject[gp.currentMap][j] == null) {
                    gp.superObject[gp.currentMap][j] = new OBJ_Key(gp);
                    gp.superObject[gp.currentMap][j].worldX = gp.tileSize * 12;
                    gp.superObject[gp.currentMap][j].worldY = gp.tileSize * 33;
                    gp.superObject[gp.currentMap][j].temp = true;
                    break;
                }
            }
        }

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
