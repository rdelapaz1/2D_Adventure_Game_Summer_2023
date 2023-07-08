package Main;

import Entity.Entity;
import Objects.OBJ_Coin_Bronze;
import Objects.OBJ_Heart;
import Objects.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font PurisaBold;
    public boolean messageOn = false;
    //public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNum = 0;
    public int titleScreenState = 0; //0 is for main title scrren
    public int playerslotCol = 0;
    public int playerslotRow = 0;
    public int npcSlotRow = 0;
    public  int npcSlotCol = 0;
    int subState = 0;
    int counter;
    public Entity npc;

    BufferedImage heart_blank,heart_full,heart_half,crystal_full,crystal_blank,coin;

    int charIndex = 0;
    String combineText = "";

    public  UI(GamePanel gp){
        this.gp = gp;

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("font/Purisa Bold.ttf");
            PurisaBold = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        //Create HUD Object
        Entity heart = new OBJ_Heart(gp);
        heart_blank = heart.image3;
        heart_full = heart.image;
        heart_half = heart.image2;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

   public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(PurisaBold);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        if(gp.gameState == gp.titleState){
            g2.setColor(new Color(0,0,0,255));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            subState = 0;
            drawTitleScreen();
        }
        //GAME STATE
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();
        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        //CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawPlayerInventory(gp.player,true);
        }
        //OPTIONS STATE
        if(gp.gameState == gp.optionsState){
            drawOptionScreen();
        }
        //GAMEOVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }

    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        int iconSize = 32;
        int manaStartX = (gp.tileSize/2) - 5;
        int manaStartY = 0;

        //Drawing blank hearts template
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_blank,x,y,iconSize,iconSize,null);
            i++;
            x += iconSize;
            manaStartY = y + 32;
            if(i % 8 == 0){
                x = (gp.tileSize/2);
                y += iconSize;
            }
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.life){
            g2.drawImage(heart_half, x,y,iconSize,iconSize,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,iconSize,iconSize,null);
            }
            i++;
            x+= iconSize;
        }

        //Draw blank crystals
        x = (gp.tileSize / 2) - 5;
        y = (int)(gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank,x,y,iconSize,iconSize,null);
            i++;
            x+= iconSize;
        }
        //draw mana
        x = (gp.tileSize / 2) - 5;
        y = (int)(gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full,x,y,iconSize,iconSize,null);
            i++;
            x += iconSize;
        }

    }

    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX,messageY);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX+1,messageY+1);

                int counter = messageCounter.get(i) + 1; //messageCounter++
                messageCounter.set(i,counter);
                messageY +=50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
            String text = "Blue Boy Adventure";
            int x = getXforCenterText(text);
            int y = gp.tileSize * 3;

            //SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 3, y + 3);
            //MAIN COLOR
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //BLUE BOY IMAGE
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            text = "NEW GAME";
            x = getXforCenterText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class.";
            int x = getXforCenterText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text,x,y);

            text = "Fighter";
            x = getXforCenterText(text);
            y += gp.tileSize *3;
            g2.drawString(text,x,y);
            if(commandNum == 0){
                g2.drawString(">",x - gp.tileSize,y);
            }

            text = "Thief";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 1){
                g2.drawString(">",x - gp.tileSize,y);
            }

            text = "Sorcerer";
            x = getXforCenterText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNum == 2){
                g2.drawString(">",x - gp.tileSize,y);
            }

            text = "Back";
            x = getXforCenterText(text);
            y += gp.tileSize * 2;
            g2.drawString(text,x,y);
            if(commandNum == 3){
                g2.drawString(">",x - gp.tileSize,y);
            }


        }
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenterText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen() {
        //WINDOW
        int x = gp.tileSize * 3; //2 tiles to the left
        int y = gp.tileSize / 2;
        int width = gp.getWidth() - (gp.tileSize*6);
        int height = gp.tileSize * 4;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,24));
        x += gp.tileSize;
        y += gp.tileSize;

        if(npc.dialogues[npc.dialougeSet][npc.dialogueIndex] != null){
           // currentDialogue = npc.dialogues[npc.dialougeSet][npc.dialogueIndex];
            char characters[] = npc.dialogues[npc.dialougeSet][npc.dialogueIndex].toCharArray();
            if(charIndex < characters.length){
                gp.playSE(17);
                String s = String.valueOf(characters[charIndex]);
                combineText = combineText + s;
                currentDialogue = combineText;
                charIndex++;
            }

            if(gp.keyH.enterPressed == true) {

                charIndex = 0;
                combineText = "";

                if (gp.gameState == gp.dialogueState || gp.gameState == gp.custsceneState) {

                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }
        else{
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState) {
                gp.gameState = gp.playState;
            }
            if(gp.gameState == gp.custsceneState){
                gp.cutsceneManger.scenePhase++;
            }
        }



        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y+=40;
        }
    }

    public void drawCharacterScreen(){
        //CREATE FRAME
        final int frameX  = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(20F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int height = 34;

        //NAMES
        g2.drawString("Level", textX,textY);
        textY += height;
        g2.drawString("Life", textX,textY);
        textY += height;
        g2.drawString("Mana", textX,textY);
        textY += height;

        g2.drawString("Strength", textX,textY);
        textY += height;
        g2.drawString("Dexterity", textX,textY);
        textY += height;
        g2.drawString("Attack", textX,textY);
        textY += height;
        g2.drawString("Defense", textX,textY);
        textY += height;
        g2.drawString("XP", textX,textY);
        textY += height;
        g2.drawString("Next Level", textX,textY);
        textY += height;
        g2.drawString("Coins", textX,textY);
        textY += height + 10;
        g2.drawString("Weapon", textX,textY);
        textY += height + 15;
        g2.drawString("Shield", textX,textY);

        //VALUES
        int tailX = (frameX + frameWidth) - 35;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.strength);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.dexterity);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.attack);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.defense);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.xp);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.nextLvlXP);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        value = String.valueOf(gp.player.coin);
        textX = getXAllignRight(value,tailX);
        g2.drawString(value,textX,textY);
        textY += height;

        g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize + 10, textY-15,null);
        textY += 48;


        g2.drawImage(gp.player.currentShield.down1,tailX - gp.tileSize + 10, textY-10,null);
        //textY += 48;



    }

    public void drawOptionScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(20F));
        //SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch (subState){
            case 0: options_Top(frameX,frameY); break;
            case 1: options_fullScreenNotification(frameX,frameY); break;
            case 2: options_control(frameX,frameY); break;
            case 3: options_confirmation(frameX,frameY); break;
        }
        gp.keyH.enterPressed = false;
    }

    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0, gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        //SHADOW
        text = "Game Over";
        g2.setColor(Color.black);
        x = getXforCenterText(text);
        y = gp.tileSize * 4;
        g2.drawString(text,x,y);
        //MAIN
        g2.setColor(Color.WHITE);
        g2.drawString(text,x -4, y - 4);
        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenterText(text);
        y += gp.tileSize * 4;
        g2.drawString(text,x,y);
        if(commandNum == 0){
            g2.drawString(">", x - 25, y);
        }
        //Back to title screen
        text = "Quit";
        y += 80;
        g2.drawString(text,x,y);
        if(commandNum == 1){
            g2.drawString(">", x - 25, y);
        }
    }

    public void drawPlayerInventory(Entity entity, boolean cursor){
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.tileSize * 9;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerslotCol;
            slotRow = playerslotRow;
        }
        else{
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow =npcSlotRow;
        }

        //FRAME
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);
        //SLOT
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;
        //DRAW PLAYER ITEMS
        for(int i = 0; i < entity.inventory.size(); i++){
            //EQUIP CURSOR
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentLight){
                g2.setColor(new Color(240,190,190));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }

            g2.drawImage(entity.inventory.get(i).down1,slotX,slotY,null);

            //draw amount if greater than 1 and stackable
            if(entity.inventory.get(i).amount > 1 && entity == gp.player){
                g2.setFont(g2.getFont().deriveFont(26f));
                int amountX;
                int amountY;
                String s = "" + entity.inventory.get(i).amount;
                amountX = getXAllignRight(s,slotX+44);
                amountY = slotY + gp.tileSize;
                //shadow
                g2.setColor(new Color(60,60,60));
                g2.drawString(s,amountX,amountY);
                //number
                g2.setColor(Color.WHITE);
                g2.drawString(s,amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }
        //CURSOR
        if(cursor == true) {
            int cursorX = slotXStart + (slotCol * slotSize);
            int cursorY = slotYStart + (slotRow * slotSize);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;
            //DRAW CURSOR
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);
            //DESCRIPTION FRAME
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;
            //DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));
            int itemIndex = getSlotIndex(slotCol,slotRow);
            if(itemIndex < entity.inventory.size()){
                for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
                    drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }

    }

    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter * 5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eventHandler.tempMap;
            gp.player.worldY = gp.eventHandler.tempRow * gp.tileSize;
            gp.player.worldX = gp.eventHandler.tempCol * gp.tileSize;
            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }

    public void drawTradeScreen(){
        switch (subState){
            case 0: tradeSelect(); break;
            case 1: tradeBuy(); break;
            case 2: tradeSell(); break;
        }
        gp.keyH.enterPressed = false;
    }

    public void drawSleepScreen(){
        counter++;
        if(counter < 120){
            gp.enviromentManager.lighting.filterAlpha += 0.001f;
            if(gp.enviromentManager.lighting.filterAlpha > 1f){
                gp.enviromentManager.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120){
            gp.enviromentManager.lighting.filterAlpha -= 0.001f;
            if(gp.enviromentManager.lighting.filterAlpha < 0f){
                gp.enviromentManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.enviromentManager.lighting.dayCounter = 0;
                gp.enviromentManager.lighting.dayState = gp.enviromentManager.lighting.day;
                gp.gameState = gp.playState;
                gp.player.getImage();

            }
        }
    }

    public void tradeSelect(){
        npc.dialougeSet = 0;
        drawDialogueScreen();

        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x,y,width,height);
        //DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy",x,y);
        if(commandNum == 0){
            g2.drawString(">", x-25,y);
            if(gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Sell",x,y);
        if(commandNum == 1){
            g2.drawString(">", x-25,y);
            if(gp.keyH.enterPressed == true){
                subState = 2;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave",x,y);
        if(commandNum == 2){
            g2.drawString(">", x-25,y);
            if(gp.keyH.enterPressed == true){
               commandNum = 0;
               npc.startDialogue(npc,1);

            }
        }
    }

    public void tradeBuy(){
        //DRAW PLAYER INVENTORY
        drawPlayerInventory(gp.player,false);
        //DRAW NPC INVENTORY
        drawPlayerInventory(npc,true);
        //DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x + 25, y + 60);
        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coins: " + gp.player.coin, x + 25, y + 60);
        //DRAW PRICE SUB WINDOW
        int itemIndex = getSlotIndex(npcSlotCol,npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin,x + 10,y + 8,32,32,null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXAllignRight(text,gp.tileSize * 8 - 20);
            g2.drawString(text, x, y+32);

            //BUY ITEM
            if(gp.keyH.enterPressed == true){
                if(npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    npc.startDialogue(npc,2);
                }
                else{
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true){
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else{
                        subState = 0;
                        npc.startDialogue(npc,3);
                    }
                }
            }
        }

    }

    public void tradeSell(){
        //DRAW PLAYER INVENTORY
        drawPlayerInventory(gp.player, true);
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x + 25, y + 60);
        //DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 9;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coins: " + gp.player.coin, x+25, y + 60);
        //DRAW PRICE SUB WINDOW
        int itemIndex = getSlotIndex(playerslotCol,playerslotRow);
        if(itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 12.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXAllignRight(text, gp.tileSize * 15 - 20);
            g2.drawString(text, x, y + 32);
            //SELL ITEM
            if(gp.keyH.enterPressed == true){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc,4);
                }
                else{
                    if(gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }

        }
        }

    public void options_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;
        currentDialogue = "Change will take effect \nafter restart.";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY += 40;
        }
        //BACK
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }
    }

    public void options_control(int frameX,int frameY){
        int textX;
        int textY;
        //TITLE
        String text = "Controls";
        textX = getXforCenterText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move",textX,textY); textY += gp.tileSize;
        g2.drawString("Attack/Interact",textX,textY); textY += gp.tileSize;
        g2.drawString("Shoot",textX,textY); textY += gp.tileSize;
        g2.drawString("Character Screen",textX,textY); textY += gp.tileSize;
        g2.drawString("Pause",textX,textY); textY += gp.tileSize;
        g2.drawString("Options",textX,textY);

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD",textX,textY); textY += gp.tileSize;
        g2.drawString("ENTER",textX,textY); textY += gp.tileSize;
        g2.drawString("F",textX,textY); textY += gp.tileSize;
        g2.drawString("C",textX,textY); textY += gp.tileSize;
        g2.drawString("P",textX,textY); textY += gp.tileSize;
        g2.drawString("ESC",textX,textY);

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back",textX,textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25,textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_confirmation(int frameX, int frameY){
        int textX;
        int textY;
        //TITLE
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*3;

        currentDialogue = "Quit game and\nreturn to title screen?";
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line,textX,textY);
            textY+=40;
        }
        //YES
        String text = "YES";
        textX = getXforCenterText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text,textX,textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.titleState;
                gp.resetGame(true);
                gp.stopMusic();
            }
        }
        //NO
        text = "NO";
        textX = getXforCenterText(text);
        textY += gp.tileSize;
        g2.drawString(text,textX,textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public int getSlotIndex(int slotCol, int slotRow){
        int slotIndex = slotCol + (slotRow * 5);
        return  slotIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5,width-10,height-10,25,25);
    }

    public void options_Top(int x, int y){
        int textX;
        int textY;
        //TITLE
        String text = "Options";
        textX = getXforCenterText(text);
        textY = y + gp.tileSize;
        g2.drawString(text,textX,textY);

        //FULL SCREEN ON/OFF
        textX = x + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen",textX,textY);
        if(commandNum == 0){
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed == true){
                if(gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                }
                else if(gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                }
                subState = 1;
            }
        }
        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music",textX,textY);
        if(commandNum == 1){
            g2.drawString(">",textX-25,textY);
        }
        //SE
        textY += gp.tileSize;
        g2.drawString("SE",textX,textY);
        if(commandNum == 2){
            g2.drawString(">",textX-25,textY);
        }
        //Control
        textY += gp.tileSize;
        g2.drawString("Control",textX,textY);
        if(commandNum == 3){
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }
        //END GAME
        textY += gp.tileSize;
        g2.drawString("End Game",textX,textY);
        if(commandNum == 4){
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed == true){
              commandNum = 0;
              subState = 3;
            }
        }
        //BACK
        textY += gp.tileSize * 2;
        g2.drawString("Back",textX,textY);
        if(commandNum == 5){
            g2.drawString(">",textX-25,textY);
            if(gp.keyH.enterPressed == true){
               commandNum = 0;
               gp.gameState = gp.playState;
            }
        }
        //FULL SCREEN CHECKBOX
        textX = x + (int)(gp.tileSize*5);
        textY = y + (int)(gp.tileSize*2.5);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24,24);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX,textY,24,24);
        }
        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY,120,24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);
        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY,120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();
    }

    public int getXforCenterText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXAllignRight(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void drawMonsterLife(){
        //normal monster health barx
        for(int i = 0; i<gp.monster[1].length; i++){
            if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].inCamera()){
                if(gp.monster[gp.currentMap][i].hpBarOn == true && gp.monster[gp.currentMap][i].boss == false) {

                    double oneScale = (double)gp.tileSize / gp.monster[gp.currentMap][i].maxLife;
                    double hpBarValue = oneScale * gp.monster[gp.currentMap][i].life;
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(gp.monster[gp.currentMap][i].getScreenX() - 1, gp.monster[gp.currentMap][i].getScreenY() - 16, gp.tileSize+2, 12);
                    g2.setColor(Color.red);
                    g2.fillRect(gp.monster[gp.currentMap][i].getScreenX(), gp.monster[gp.currentMap][i].getScreenY()- 15, (int)hpBarValue, 10);

                    gp.monster[gp.currentMap][i].hpBarCounter++;
                    if(gp.monster[gp.currentMap][i].hpBarCounter > 600){
                        gp.monster[gp.currentMap][i].hpBarCounter = 0;
                        gp.monster[gp.currentMap][i].hpBarOn = false;
                    }
                }
                else if (gp.monster[gp.currentMap][i].hpBarOn == true && gp.monster[gp.currentMap][i].boss == true){

                    double oneScale = (double)gp.tileSize*8 / gp.monster[gp.currentMap][i].maxLife;
                    double hpBarValue = oneScale * gp.monster[gp.currentMap][i].life;

                    int x = gp.screenWidth/2 - gp.tileSize*4;
                    int y = gp.tileSize * 10;
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize* 8 + 2, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(x, y, (int)hpBarValue, 10);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD,20f));
                    g2.setColor(Color.WHITE);
                    g2.drawString(gp.monster[gp.currentMap][i].name, x + 4, y -10);
                }
            }
        }

    }

}
