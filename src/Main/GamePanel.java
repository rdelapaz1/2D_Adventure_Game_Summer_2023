package Main;/* package main; */

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import AI.Pathfinder;
import Data.SaveLoad;
import Entity.Entity;
import Entity.Player;
import Enviroment.EnviromentManager;
import Tile.Map;
import Tile.TileManager;
import Monster.MON_greenSlime;
import Tiles_Interactive.InteractiveTile;
import Entity.Projectile;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS//
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

   public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTING
    public int maxWorldCol = 0; //50x50 tile world size
    public int maxWorldRow = 0;
    //public final int worldWidth = tileSize * maxWorldCol; //true size of world
    //public final int worldHeigth = tileSize * maxWorldRow;
    //MAPS
    public int maxMap = 10;
    public int currentMap = 0;
    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn;

    //FPS
    final int fps = 60;

    public TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public Config config = new Config(this);
    Thread gameThread;


    public Player player = new Player(this,keyH);
    public Entity superObject[][] = new Entity[maxMap][10]; //can display up to 10 objects: after correction superObject is entity
    public Entity npc[][] = new Entity[maxMap][20];
    public Entity monster[][] = new Entity[maxMap][20];
    ArrayList<Entity> arrayList = new ArrayList<>();

    //Projectiles
    public Entity[][] projectiles = new Projectile[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();



    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;


    //Interactive tile
    public InteractiveTile interactiveTile[][] = new InteractiveTile[maxMap][50];

    //Particles
    public ArrayList<Entity> particleList = new ArrayList<>();

    //Pathfinder
    public Pathfinder pathfinder = new Pathfinder(this);

    //Enviromental effects
    EnviromentManager enviromentManager = new EnviromentManager(this);

    //MAP
    public Map map = new Map(this);

    //DATA
    public SaveLoad saveLoad = new SaveLoad(this);

    //Ccreate entity
    public CreateEntity createEntity = new CreateEntity(this);

    //MAP DAY NIGHT FILTERS
    public int currentArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;
    public int nextArea;

    //CUTSCENE
    public final int custsceneState = 11;
    public boolean bossbattleStart = false;
    public CutsceneManger cutsceneManger = new CutsceneManger(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        assetSetter.setObject();
        assetSetter.setInteractiveTile();
        assetSetter.setNPC();
        assetSetter.setMonster();
        enviromentManager.setUp();
        currentArea = outside;
        //playMusic(0);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB_PRE);
        g2 = (Graphics2D) tempScreen.getGraphics(); //everything drawn to tempScreen recorded into g2, then resize
        if(fullScreenOn == true) {
            setFullScreen();
        }
    }

    public void resetGame(boolean restart){
        if(player.atLastStage){
            playMusic(19);
        }
        else{
            playMusic(0);
        }

        currentArea = outside;
        removeTempEntities();
        bossbattleStart = false;
        player.setDefaultPosition();
        player.restoreValues();
        assetSetter.setMonster();
        assetSetter.setNPC();
        player.resetCounters();
        if(restart) {
            player.setDefaultValue();
            assetSetter.setObject();
            assetSetter.setInteractiveTile();
            enviromentManager.lighting.resetDay();
        }
    }

    public void setFullScreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        //get full screen dimensions
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer  += currentTime- lastTime;
            lastTime = currentTime;

            if(delta >= 1){
                //1 UPDATE: update info i.e. character position
               update();
                //2 DRAW: draw the screen
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update(){
        if(gameState == playState) {
            //PLAYER
            player.update();
            //NPC
            for(int i=0; i<npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            //monster
            for(int i=0; i<monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }
            for(int i=0; i<projectiles[1].length; i++){
                if(projectiles[currentMap][i]!= null){
                    if(projectiles[currentMap][i].alive == true){
                        projectiles[currentMap][i].update();
                    }
                    if(projectiles[currentMap][i].alive == false){
                        projectiles[currentMap][i] = null;
                    }
                }
            }

            for(int i=0; i<particleList.size(); i++){
                if(particleList.get(i)!= null){
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                    }
                }
            }

            for(int i = 0; i< interactiveTile[1].length; i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].update();
                }
            }
            enviromentManager.update();
        }
        if(gameState == pauseState){
            //nothing for now
        }
    }

    public void drawToTempScreen(){
        //DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        //TITLESCREEN starts
        if(gameState == titleState){
            //implement title scrren
            ui.draw(g2);
        }
        else if(gameState == mapState){
            map.drawFullMapScreen(g2);
        }
        else {
            //TILE
            tileManager.draw(g2);
            for (int i = 0; i < interactiveTile[1].length; i++) {
                if (interactiveTile[currentMap][i] != null) {
                    interactiveTile[currentMap][i].draw(g2);
                }
            }
            //ENTITY LIST: player, npc,monster, obj
            arrayList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    arrayList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < superObject[1].length; i++) {
                if (superObject[currentMap][i] != null) {
                    arrayList.add(superObject[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    arrayList.add(monster[currentMap][i]);
                }
            }
            for (int i = 0; i < projectiles[1].length; i++) {
                if (projectiles[currentMap][i] != null) {
                    arrayList.add(projectiles[currentMap][i]);
                }
            }
            for(int i = 0; i<particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    arrayList.add(particleList.get(i));
                }
            }

            //sorting entities
            Collections.sort(arrayList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });
            //draw entitites
            for(int i = 0; i< arrayList.size(); i++){
                arrayList.get(i).draw(g2);
            }
            //empty list
            arrayList.clear();

            //ENVIROMENT
            enviromentManager.draw(g2);

            //MINI MAP
            map.drawMiniMap(g2);

            //CUTSCENE
            cutsceneManger.draw(g2);

            //UI
            ui.draw(g2);
        }

        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

    public void changeArea(){
        if(currentArea != nextArea){
            //different areas
            stopMusic();
            if(nextArea == outside){
                playMusic(0);
            }
            else if(nextArea == indoor){
                playMusic(18);
            }
            else if(nextArea == dungeon){
                playMusic(19);
            }
        }
        currentArea = nextArea;
        assetSetter.setMonster();
        assetSetter.setNPC();
    }

    public void removeTempEntities(){
        for(int mapNum = 0; mapNum < maxMap; mapNum++){
            for(int i = 0; i < superObject[1].length; i++){
                if(superObject[mapNum][i] != null &&
                superObject[mapNum][i].temp == true){
                    superObject[mapNum][i] = null;
                }
            }
        }
    }

    }


