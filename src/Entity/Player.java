package Entity;
 import Main.GamePanel;
 import Main.KeyHandler;
 import Main.UtilityTool;
 import Objects.*;

 import javax.imageio.ImageIO;
 import javax.swing.*;
 import java.awt.*;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.util.ArrayList;


public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCancel = false;
   // public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventorySize = 20;
    public boolean lightUpdate = false;
    public int standCounter = 0;
    public int killedCounter = 0;

    //constructor
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,32,32);

        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        //attackArea.width = 36;
        //attackArea.height = 36;

        setDefaultValue();

    }

    public void setDefaultValue(){



        if(atLastStage == true){
            worldX = gp.tileSize * 26;
            worldY = gp.tileSize * 40;
        }
        else{
            worldX = gp.tileSize * 23;
            worldY = gp.tileSize * 21;
        }

        playerSpeed = 4;
        direction = "down";

        //Player status
        maxLife = 6;
        life = maxLife;
        level = 1;
        strength = 1;
        dexterity = 1;
        xp = 0;
        nextLvlXP = 5;
        coin = 0;
        currentWeapon =  new OBJ_sword_normal(gp);
        currentShield = new OBJ_shield_wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();
        maxMana = 4;
        mana = maxMana;
        defaultSpeed = 4;
        transparent = false;
        currentLight = null;
        attack_motion1_duration = 2;
        attack_motion2_duration = 10;
        getImage();
        getAttackImage();
        getGuardImage();
        setItem();
        setDialogue();
    }

    public void setDefaultPosition(){

        if(atLastStage == true){
            worldX = gp.tileSize * 26;
            worldY = gp.tileSize * 40;
            gp.currentMap = 3;
        }
        else{
            worldX = gp.tileSize * 23;
            worldY = gp.tileSize * 21;
            gp.currentMap = 0;
        }

        direction = "down";

    }

    public void restoreValues(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdate = true;
        playerSpeed = defaultSpeed;
    }

    public void setItem(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Lantern(gp));
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        attack_motion1_duration = currentWeapon.attack_motion1_duration;
        attack_motion2_duration = currentWeapon.attack_motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getImage(){
        down1 = setup("player/boy_down_1",gp.tileSize, gp.tileSize);
        down2 = setup("player/boy_down_2",gp.tileSize, gp.tileSize);
        up1 = setup("player/boy_up_1",gp.tileSize, gp.tileSize);
        up2 = setup("player/boy_up_2",gp.tileSize, gp.tileSize);
        left1 = setup("player/boy_left_1",gp.tileSize, gp.tileSize);
        left2 = setup("player/boy_left_2",gp.tileSize, gp.tileSize);
        right1 = setup("player/boy_right_1",gp.tileSize, gp.tileSize);
        right2 =setup("player/boy_right_2",gp.tileSize, gp.tileSize);
    }

    public void getAttackImage(){
        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackRight1 = setup("player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
            attackLeft1 = setup("player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackRight1 = setup("player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
            attackLeft1 = setup("player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe) {
            attackUp1 = setup("player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackRight1 = setup("player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
            attackLeft1 = setup("player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
        }

    }

    public void getGuardImage(){
        guardUp = setup("Player/boy_guard_up",gp.tileSize,gp.tileSize);
        guardDown = setup("Player/boy_guard_down",gp.tileSize,gp.tileSize);
        guardRight = setup("Player/boy_guard_right",gp.tileSize,gp.tileSize);
        guardLeft = setup("Player/boy_guard_left",gp.tileSize,gp.tileSize);
    }

    public int getCurrentWeaponSlot(){
        int currentSlot = 0;
        for(int i = 0; i< inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentSlot = i;
            }
        }
        return currentSlot;
    }

    public int getCurrentShieldSlot(){
        int currentSlot = 0;
        for(int i = 0; i< inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentSlot = i;
            }
        }
        return currentSlot;
    }

    public void update(){
        if(knockBack == true){
            //Check collision with solid obj's, reset values
            gp.cChecker.checkTile(this);
            //check npc collision
            gp.cChecker.checkEntity(this, gp.npc);
            //check object collision
           gp.cChecker.checkObject(this,true);
            //check monster collision
            gp.cChecker.checkEntity(this,gp.monster);
            //check intereactive tiles
           gp.cChecker.checkEntity(this,gp.interactiveTile);


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
        else if(keyH.spacePressed == true){
            guarding = true;
            guardCounter++;
        }

        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }


            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check npc collision
            int npc_index = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npc_index);

            //check object collision
            int objectIndex = gp.cChecker.checkObject(this,true);
            pickUpObj(objectIndex);
            //check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            monsterContact(monsterIndex);
            //check intereactive tiles
            int ITindex = gp.cChecker.checkEntity(this,gp.interactiveTile);

            //CHECK EVENT
            gp.eventHandler.checkEvent();

            //if collision if false player moves
            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up": worldY -= playerSpeed;break;
                    case "down": worldY += playerSpeed;break;
                    case "left": worldX -= playerSpeed;break;
                    case "right": worldX += playerSpeed;break;
                }
            }

            if(keyH.enterPressed == true && attackCancel == false){
                //gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
                guardCounter = 0;
            }
            //after events, set to false
            attackCancel = false;
            keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if(spriteCounter > 20){
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            standCounter++;
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
                guarding = false;
                guardCounter = 0;
            }

        }

        if(keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
        && projectile.haveResource(this) == true) {
            System.out.println(shotAvailableCounter);
            projectile.set(worldX,worldY,direction,true,this);
            projectile.subtractResource(this);
            //Changed projectile list to array
            //gp.projectileList.add(projectile);
            //Check for empty spot int array
            for(int i = 0; i < gp.projectiles[1].length; i++){
                if(gp.projectiles[gp.currentMap][i]== null){
                    gp.projectiles[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(10);
        }

        //incvinclibe counter
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

        if(life > maxLife){
            life = maxLife;
        }
        if(mana> maxMana){
            mana = maxMana;
        }

        if(life <= 0 ){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.playSE(12);
            gp.stopMusic();
            life+=1;
        }


    }

    public void pickUpObj(int i){
        if(i != 999) { ///999 signifies no obje touched
            if(gp.superObject[gp.currentMap][i].type == type_pickup){
                gp.superObject[gp.currentMap][i].use(this);
                gp.superObject[gp.currentMap][i] = null;
            }

            else if(gp.superObject[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed == true){
                    attackCancel = true;
                    gp.superObject[gp.currentMap][i].interact();
                }
            }

            else{
                String text;
                if(canObtainItem(gp.superObject[gp.currentMap][i]) == true){
                    gp.playSE(1);
                    text = "Got a " + gp.superObject[gp.currentMap][i].name + "!";
                }
                else {
                    text = "Cannot carry more!";
                }
                gp.ui.addMessage(text);
                gp.superObject[gp.currentMap][i] = null;
            }
        }
    }

    public void interactNPC(int i){
        if(i != 999) {
            if (keyH.enterPressed == true) { ///999 signifies no object touched
                attackCancel = true;
                gp.npc[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);
        }
    }

    public void monsterContact(int i){
        if(i != 999){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){
                invincible = true;
                gp.playSE(7);
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                transparent = true;
                if(life < 0){
                    life = 0;
                }

            }
        }
    }

    public void damageMonster(int i, int attack, int knockBackPower,Entity attacker){
        if(i != 999){
            if(gp.monster[gp.currentMap][i].invincible == false){
                gp.playSE(5);

                if(knockBackPower > 0){
                    setKnockBack(attacker,gp.monster[gp.currentMap][i],knockBackPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance == true){
                    attack *= 5;
                }


                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[gp.currentMap][i].life-= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    xp += gp.monster[gp.currentMap][i].xp;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + "  + gp.monster[gp.currentMap][i].xp + "!");
                    checkLevelUp();
                }

            }
        }

    }

    public void damageInteractiveTile(int index){
        if(index != 999 && gp.interactiveTile[gp.currentMap][index].destructible == true && gp.interactiveTile[gp.currentMap][index].isCorrectItem(this) == true
        && gp.interactiveTile[gp.currentMap][index].invincible == false){
            gp.interactiveTile[gp.currentMap][index].life--;
            gp.interactiveTile[gp.currentMap][index].PlaySE();
            gp.interactiveTile[gp.currentMap][index].invincible = true;

            generateParticle(gp.interactiveTile[gp.currentMap][index],gp.interactiveTile[gp.currentMap][index]);

            if(gp.interactiveTile[gp.currentMap][index].life == 0) {
                gp.interactiveTile[gp.currentMap][index] = gp.interactiveTile[gp.currentMap][index].getDestroyedForm();

            }
        }
    }

    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectiles[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }

    public void checkLevelUp(){
        if(xp >= nextLvlXP){
            level++;
            nextLvlXP *= 5;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this,0);
        }
    }

    public void setDialogue(){
        dialogues[0][0]= "You are level " + level + " now!";
    }

    public void selectItem(){
        int itemIndex = gp.ui.getSlotIndex(gp.ui.playerslotCol,gp.ui.playerslotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){
                if(currentLight == selectedItem){
                    currentLight = null;
                }else{
                    currentLight = selectedItem;
                }
                lightUpdate = true;
            }
            if(selectedItem.type == type_boots){
                inventory.remove(itemIndex);
                gp.player.playerSpeed += 2;
                gp.player.defaultSpeed += 2;

            }
            if(selectedItem.type == type_consumable){
               if(selectedItem.use(this) == true) {
                   if(selectedItem.amount > 1){
                       selectedItem.amount--;
                   }
                   else {
                       inventory.remove(itemIndex);
                   }
               }
            }
        }
    }

    public int searchItemInventory(String itemName){
        int itemIndex = 999;

        for(int i = 0; i < gp.player.inventory.size(); i++) {
            if(gp.player.inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }    return  itemIndex;
    }

    public boolean canObtainItem(Entity item){
        Entity newItem = gp.createEntity.getObject(item.name);
        boolean canObtain = false;
        //check stackable
        if(newItem.stackAble == true){
            int itemIndex = searchItemInventory(newItem.name);

            if(itemIndex != 999){
                inventory.get(itemIndex).amount++;
                canObtain = true;
            }
            else{
                if(inventory.size() != inventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else{
            //not stckables
            if(inventory.size() != inventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return  canObtain;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
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
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1) {image = attackUp1;}
                    if(spriteNum ==2){ image = attackUp2; }
                }
                if(guarding == true){
                    image = guardUp;
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
                if(guarding == true){
                    image = guardDown;
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2){ image = left2; }
                }
                if(attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1) { image = attackLeft1; }
                    if(spriteNum == 2){ image = attackLeft2; }
                }
                if(guarding == true){
                    image = guardLeft;
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
                if(guarding == true){
                    image = guardRight;
                }
                break;
        }

        if(transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        if(drawing == true){
            g2.drawImage(image,tempScreenX,tempScreenY, null);

        }
        //reset opacity of player
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        //g2.setColor(.WHITE);
        //g2.drawString("Invincible:" + invincibleCounter,10, 400);
    }

    public void getSleepImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }
}
