package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true;

    ArrayList<String> fileName = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp){
        this.gp = gp;

        //READ Tile DATA
        InputStream is = getClass().getClassLoader().getResourceAsStream("maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        try{
            while((line = br.readLine()) != null){
                fileName.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        tile = new Tile[fileName.size()];
        getTileImage();

        is = getClass().getClassLoader().getResourceAsStream("maps/worldmap.txt");
        br = new BufferedReader(new InputStreamReader(is));
        try{
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");
            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
            br.close();
        }catch (IOException e){
           System.out.println("TILE EXCPETION");
        }

        //mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        //getTileImage();
        loadMap("maps/worldmap.txt",0);
        loadMap("maps/indoor01.txt",1);
        loadMap("maps/dungeon01.txt",2);
        loadMap("maps/dungeon02.txt",3);


       /* loadMap("maps/interior01.txt",0);
             loadMap("maps/worldV3.txt",1);

        loadMap("maps/dungeon01.txt",2);
        loadMap("maps/dungeon02.txt",3);

        */



    }

    public void getTileImage(){
        for(int i = 0; i < fileName.size(); i++){
            String name;
            boolean collision;
            //GET FILE NAME
            name = fileName.get(i);
            if(collisionStatus.get(i).equals("true")){
                collision = true;
            }
            else{
                collision = false;
            }
            setUp(i,name,collision);
        }
    }

    public void setUp(int index, String imagePath, boolean collision){

        UtilityTool utilityTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + imagePath));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map){
        try{
            InputStream is  = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader( new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine(); // read line from config file
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" "); //splits line into string of one each

                    int num = Integer.parseInt(numbers[col]); //array of ints
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int scrrenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.worldX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.worldX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.worldY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.worldY
            ){
                g2.drawImage(tile[tileNum].image,screenX,scrrenY, null);

            }
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
/*
        if(drawPath == true){
            g2.setColor(new Color(255,0,0,70));

            for(int i = 0; i<gp.pathfinder.pathList.size(); i++){
                int worldX = gp.pathfinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pathfinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX,screenY, gp.tileSize,gp.tileSize);



            }
        }

 */

    }
}
