package Monster;

import Entity.Entity;
import Main.GamePanel;
import Objects.OBJ_Coin_Bronze;
import Objects.OBJ_Heart;
import Objects.OBJ_ManaCrystal;
import Objects.OBJ_Rock;

import java.util.Random;

public class MON_redSlime extends Entity {
    public static  final String monName = "Red Slime";
        public MON_redSlime(GamePanel gp){
            super(gp);
            //basic stats
            name = monName;
            playerSpeed = 2;
            maxLife = 6;
            life = maxLife;
            type = type_monster;
            attack = 2;
            defense = 2;
            xp = 5;
            projectile = new OBJ_Rock(gp);
            //solid area
            solidArea.x = 3;
            solidArea.y = 18;
            solidArea.width = 30;
            solidArea.height = 30;
            defaultSolidAreaX = solidArea.x;
            defaultSolidAreaY = solidArea.y;
            defaultSpeed = 1;

            //call image
            getImage();

        }

        public void getImage(){
            //setting direction images
            up1 = setup("monster/redslime_down_1",gp.tileSize,gp.tileSize);
            up2 = setup("monster/redslime_down_2" ,gp.tileSize,gp.tileSize);
            down1 = setup("monster/redslime_down_1",gp.tileSize,gp.tileSize);
            down2= setup("monster/redslime_down_2",gp.tileSize,gp.tileSize);
            left1 = setup("monster/redslime_down_1",gp.tileSize,gp.tileSize);
            left2 = setup("monster/redslime_down_2",gp.tileSize,gp.tileSize);
            right1 = setup("monster/redslime_down_1",gp.tileSize,gp.tileSize);
            right2 = setup("monster/redslime_down_2",gp.tileSize,gp.tileSize);
        }

        public void setAction(){

            if(onPath == true){
                //check to see if it stop chasing
                checkStopChasing(gp.player,15,100);

                //serach direction to go
                searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
                //damage slimes, projectiles
                checkShoots(100,30);
            }
            else {
                //check if starts chasing
                startsChasing(gp.player,100,5);
                //get random path else
                getRandomDirection(120);
            }

        }

        public void damageReaction(){
            actionLockCounter = 0;
            onPath = true;
        }

        public void checkDrop(){
            gp.player.killedCounter++;
            if(gp.player.killedCounter == 4){
                gp.cutsceneManger.scenePhase++;
                gp.cutsceneManger.redSlimeScene();

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

