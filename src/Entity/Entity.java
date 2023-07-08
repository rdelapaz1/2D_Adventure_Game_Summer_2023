package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//Super class for all player classes
public class Entity {
    public GamePanel gp;

    public int worldX,worldY;
    public int playerSpeed;
    public int speakState;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2;
    public String direction = "down"; //always down by default, for objects

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;

    public Rectangle solidArea = new Rectangle(0,0,40,40);
    public int defaultSolidAreaX, defaultSolidAreaY;
    public boolean collisionOn = false;
    public int actionLockCounter;
    public boolean dying = false;
    public boolean alive = true;

    public String dialogues[][] = new String[20][20];
    public int dialougeSet = 0;
    public int dialogueIndex = 0;

    public boolean invincible = false;


    //PLAYER LIVES
    public int maxLife;
    public int life;

    public int level;
    public int strength;
    public int dexterity;
    public int defense;
    public int attack;
    public int xp;
    public int nextLvlXP;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;


    //variable from old superObject class
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    public int type; // 0 for player, 1 for npc, etc;
    public boolean attacking = false;
    public boolean hpBarOn = false;

    public Rectangle attackArea = new Rectangle(0,0,0,0);

    //WEAPON ATTRIBUTES
    public int attackValue;
    public int defenseValue;

    public String description;

    public final int type_boots = 0;
    public final int type_NPC = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickup = 7;
    public final int type_pickaxe = 10;

//startig at end of fight
   public boolean atLastStage = false;

    public int value;

    public int maxMana;
    public int mana;
    public Projectile projectile;
    public int useCost;
public int shotAvailableCounter;
    public int ammo;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventorySize = 20;
    public int price;

    public boolean onPath = false;

    //KNOCK BACK
    public boolean knockBack;
    public int defaultSpeed;
    public int knockBackCounter;
    public int knockBackPower = 0;
    public Entity attacker;
    public String knockBackDirection;

    //OBSTACLE
    public int type_obstacle = 8;

    //STACKABLE
    public boolean stackAble = false;
    public int amount = 1;

    //Lighting object
    public Entity currentLight;
    public int lightRadius;
    public int type_light = 9;

    //Attack image speed
    public int attack_motion1_duration;
    public int attack_motion2_duration;

    //GUARD AND PARRY
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;
    public boolean guarding = false;

    //TRANSPARENT
    public boolean transparent = false;

    //PARRY
    public int guardCounter = 0;
    public int offBalanceCounter = 0;
    public boolean offBalance = false;

    //LOOT REORGANIZED
    public Entity loot;
    public boolean opened = false;

    //ROCK,METAL PLATE
    Entity entityTouching;

    //END GAME RAGE STATE
    public boolean rageState = false;
    public boolean boss = false;
    public boolean sleep = false;


    public boolean temp = false;
    public boolean drawing = true;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public int getLeftX(){
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY(){
        return worldY + solidArea.y;
    }

    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol(){
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow(){
        return (worldY + solidArea.y) / gp.tileSize;
    }

    public void resetCounters(){
        spriteCounter = 0;
        invincibleCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        actionLockCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }

    public void interact(){

    }

    public void speak(){ }

    public void facePlayer(){
        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void startDialogue(Entity entity, int setNum){
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialougeSet = setNum;
    }

    public void damageReaction(){ }

    public void setAction(){ }

    public void dropItem(Entity itemDropped){
        for(int i = 0; i<gp.superObject[1].length; i++){
            if(gp.superObject[gp.currentMap][i] == null){
                gp.superObject[gp.currentMap][i] = itemDropped;
                gp.superObject[gp.currentMap][i].worldX = worldX;
                gp.superObject[gp.currentMap][i].worldY= worldY;
                break;
            }
        }
    }

    public void checkDrop(){
    }

    public boolean use(Entity entity){return false;}

    public Color getParticleColor(){
        Color color = null;
        return  color;
    }

    public int getParticleSize(){
        int size = 0;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 0;
        return  speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }

    public void  generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp,target,color,-2,-1,size,speed,maxLife);
        Particle p2 = new Particle(gp,target,color,2,-1,size,speed,maxLife);
        Particle p3 = new Particle(gp,target,color,-2,1,size,speed,maxLife);
        Particle p4 = new Particle(gp,target,color,2,1,size,speed,maxLife);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);

    }

    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.monster);
        gp.cChecker.checkEntity(this,gp.interactiveTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
        }
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter <= attack_motion1_duration){
            spriteNum = 1;
        }
        if(spriteCounter > attack_motion1_duration && spriteCounter <= attack_motion2_duration){
            spriteNum = 2;
            //Current poisition for future use
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //adjust player's world x and y for attackArea
            switch (direction){
                case "up": worldY -= attackArea.height;break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //atack area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == type_monster){
                if(gp.cChecker.checkPlayer(this) == true){
                    damagePlayer(attack);
                }
            }else{
                //check collision with world x,y, solid area
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,attack,currentWeapon.knockBackPower,this);
                //check tile IT collision
                int ITindex = gp.cChecker.checkEntity(this,gp.interactiveTile);
                gp.player.damageInteractiveTile(ITindex);

                int projectileIndex = gp.cChecker.checkEntity(this,gp.projectiles);
                gp.player.damageProjectile(projectileIndex);
            }




            //restore areas and solid area
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > attack_motion2_duration){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    //super function used to move all npc's
    public void update(){
        if(sleep == false){
            if(knockBack == true){
                //Check collision with solid obj's, reset values
                checkCollision();
                if(collisionOn == true){
                    knockBackCounter = 0;
                    knockBack = false;
                    playerSpeed = defaultSpeed;
                }else if(collisionOn == false){
                    switch (knockBackDirection) {
                        case "up":
                            worldY -= playerSpeed;
                            break;
                        case "down":
                            worldY += playerSpeed;
                            break;
                        case "left":
                            worldX -= playerSpeed;
                            break;
                        case "right":
                            worldX += playerSpeed;
                            break;
                    }
                }
                knockBackCounter++;
                if(knockBackCounter == 10){
                    knockBackCounter = 0;
                    knockBack = false;
                    playerSpeed = defaultSpeed;
                }
            }
            else if(attacking == true){
                attacking();
            }
            else{
                setAction();
                checkCollision();

                if (collisionOn == false) {
                    switch (direction) {
                        case "up":
                            worldY -= playerSpeed;
                            break;
                        case "down":
                            worldY += playerSpeed;
                            break;
                        case "left":
                            worldX -= playerSpeed;
                            break;
                        case "right":
                            worldX += playerSpeed;
                            break;
                    }
                }
                spriteCounter++;
                if(spriteCounter > 30){
                    if(spriteNum == 1) {
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }


            if(invincible == true){
                invincibleCounter++;
                if(invincibleCounter > 40){
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
            if(shotAvailableCounter < 30){
                shotAvailableCounter++;
            }
            if(offBalance){
                offBalanceCounter++;
                if(offBalanceCounter > 60){
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }


    }

    public void damagePlayer(int attack){
        if(gp.player.invincible == false){
            int damage = attack - gp.player.defense;

            //GET OPPOSITE DIRECTION
            String guardDirection = getOppositeDirection(direction);
            if(gp.player.guarding == true && gp.player.direction.equals(guardDirection)){
                //PARRY
                if(gp.player.guardCounter < 10){
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(gp.player,this,knockBackPower);
                    offBalance = true;
                    spriteCounter -= 60;
                }else {
                    //normal
                    damage /= 3;
                    gp.playSE(15);
                }
            }
            else{
                if(damage < 1){
                    damage = 1;
                }
                gp.playSE(6);

            }
            if (damage != 0) {
                gp.player.transparent = true;
                setKnockBack(this,gp.player,knockBackPower);
            }
            gp.player.life-= damage;
            gp.player.invincible = true;

        }

    }

    public void setKnockBack(Entity attacker, Entity target,int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.playerSpeed += knockBackPower;
        target.knockBack = true;
    }

    public void moveTowardPlayer(int interval){
        actionLockCounter++;
        if(actionLockCounter > interval){
            if(getXDistance(gp.player) > getYDistance(gp.player)){
                if(gp.player.getCenterX() < getCenterX()){ //player on left side
                    direction = "left";
                }
                else{   //playeron right
                    direction = "right";
                }
            }
            else if (getYDistance(gp.player) > getXDistance(gp.player)){
                if(gp.player.getCenterY() < getCenterY()){ //player above
                    direction = "up";
                }
                else{   //player below
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }

    public void setLoot(Entity loot){}

    public boolean inCamera(){
        boolean inCamera = false;
        if(worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.worldX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.worldX &&
                worldY + gp.tileSize * 5> gp.player.worldY - gp.player.worldY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.worldY
        ) {
            inCamera = true;
        }
        return  inCamera;
    }

    public int getScreenX(){
        return worldX - gp.player.worldX + gp.player.screenX;
    }

    public int getScreenY(){
        return worldY - gp.player.worldY + gp.player.screenY;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if(inCamera()){
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        //set new direction
        switch (direction){
            case "up":
                if(attacking == false){
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum ==2){ image = up2; }
                }
                if(attacking == true){
                    tempScreenY = screenY - up1.getHeight();
                    if(spriteNum == 1) {image = attackUp1;}
                    if(spriteNum ==2){ image = attackUp2; }
                }
                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1) { image = down1;}
                    if(spriteNum == 2){ image = down2; }
                }
                if(attacking == true){
                    if(spriteNum == 1) { image = attackDown1;}
                    if(spriteNum == 2){ image = attackDown2; }
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2){ image = left2; }
                }
                if(attacking == true){
                    tempScreenX = screenX - left1.getWidth();
                    if(spriteNum == 1) { image = attackLeft1; }
                    if(spriteNum == 2){ image = attackLeft2; }
                }
                break;
            case "right":
                if(attacking == false)
                {
                    if(spriteNum == 1) { image = right1; }
                    if(spriteNum == 2){ image = right2; }
                }
                if(attacking == true){
                    if(spriteNum == 1) { image = attackRight1; }
                    if(spriteNum == 2){ image = attackRight2; }
                }
                break;
        }
       /*
        switch (direction){
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }

        */

        //Monster HP bar


        if(invincible == true) {
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2, 0.4F);
        }
        if(dying == true){
            dyingAnimation(g2);
        }

        g2.drawImage(image,tempScreenX,tempScreenY, null);

        changeAlpha(g2,1f);
    }
}

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i){ changeAlpha(g2,0); }
        if(dyingCounter > i && dyingCounter <= i*2){ changeAlpha(g2,1); }
        if(dyingCounter > i*2 && dyingCounter <= i*3){ changeAlpha(g2,0); }
        if(dyingCounter > i*3 && dyingCounter <= i*4){ changeAlpha(g2,1); }
        if(dyingCounter > i*4 && dyingCounter <= i*5){ changeAlpha(g2,0); }
        if(dyingCounter > i*5 && dyingCounter <= i*6){ changeAlpha(g2,1); }
        if(dyingCounter > i*6 && dyingCounter <= i*7){ changeAlpha(g2,0); }
        if(dyingCounter > i*7 && dyingCounter <= i*8){ changeAlpha(g2,1); }
        if(dyingCounter > i*8){
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String name, int width, int height){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(name + ".png"));
            image = utilityTool.scaleImage(image,width,height);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pathfinder.setNodes(startCol,startRow,goalCol,goalRow,this);

        if(gp.pathfinder.search() == true){
            //System.out.println("Found Path.");
            //next world x and y location
            int nextX = gp.pathfinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pathfinder.pathList.get(0).row * gp.tileSize;
            //entity solid area position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                //left or right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextX && enLeftX > nextX){
                // up or left
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                //up  or right
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }
            /*
            //reaches goal, stop
            int nextCol = gp.pathfinder.pathList.get(0).col;
            int nextRow = gp.pathfinder.pathList.get(0).row ;
            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
                System.out.println("Found");
                }
             */

        }

    }

    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;
        //check surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY() - gp.player.playerSpeed;
                break;
            case "down":
                nextWorldY = user.getBottomY() + gp.player.playerSpeed;
                break;
            case "left":
                nextWorldX = user.getLeftX() - gp.player.playerSpeed;
                break;
            case "right":
                nextWorldX = user.getRightX() + gp.player.playerSpeed;
                break;
        }

        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col && target[gp.currentMap][i].getRow() == row && target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }
        System.out.println(index);
        return  index;
    }

    public int getXDistance(Entity target){
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYDistance(Entity target){
        int yDistance = Math.abs(getCenterY()- target.getCenterY());
        return yDistance;
    }

    public int getTileDistance(Entity target){
        int tileDistance = (getXDistance(target) + getYDistance(target)) / gp.tileSize;
        return tileDistance;
    }

    public int getGoalCol(Entity target){
        int goalCol = goalCol = (target.worldX + target.solidArea.x)/ gp.tileSize;
        return goalCol;
    }

    public int getGoalRow(Entity target){
        int goalRow = goalRow = (target.worldY + target.solidArea.y)/ gp.tileSize;
        return goalRow;
    }

    public void checkStopChasing(Entity target, int distance, int rate){
        if(getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }
    }

    public void checkShoots(int rate, int shotInterval){
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);
            //check projectile array
            for(int j = 0; j < gp.projectiles[1].length; j++){
                if(gp.projectiles[gp.currentMap][j] == null){
                    gp.projectiles[gp.currentMap][j] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }

    public void startsChasing(Entity target, int rate, int distance){
        if(getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }
    }

    public void getRandomDirection(int interval){
        actionLockCounter++;
        if (actionLockCounter > interval) { //changes every 120 frames
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

    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange = false;

        int xDistance = getXDistance(gp.player);
        int yDistance = getYDistance(gp.player);

        switch (direction){
            case "up":
                if(gp.player.getCenterY() < getCenterY() && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.getCenterY() > getCenterY() && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.getCenterX() < getCenterX() && xDistance < straight && yDistance < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.getCenterX() > getCenterX() && xDistance < straight && yDistance < horizontal){
                    targetInRange = true;
                }
                break;
        }

        if(targetInRange){
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public String getOppositeDirection(String direction){
        String opposite = "";
                switch (direction){
                    case "up": opposite = "down"; break;
                    case "down": opposite = "up"; break;
                    case "left": opposite = "right"; break;
                    case "right": opposite = "left"; break;
                }
                return opposite;
    }

    public void move(String direction){

    }

    public int getCenterX(){
        return worldX + (left1.getWidth()/2);
    }
    public  int getCenterY(){
        return worldY + (up1.getHeight()/2);
    }
}
