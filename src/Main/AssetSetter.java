package Main;

import Data.Progress;
import Entity.NPC_Merchant;
import Entity.NPC_OldMan;
import Entity.NPC_Rock;
import Monster.*;
import Objects.*;
import Tiles_Interactive.IT_DesWall;
import Tiles_Interactive.IT_DryTree;
import Tiles_Interactive.IT_MetalPlate;
import Tiles_Interactive.InteractiveTile;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    //set objects, place on map
    public void setObject(){
        int mapNum = 0;
        int i = 0;
        gp.superObject[mapNum][i] = new OBJ_Door(gp);
        gp.superObject[mapNum][i].worldX = gp.tileSize * 14;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 28;
        i++;
        gp.superObject[mapNum][i] = new OBJ_Door(gp);
        gp.superObject[mapNum][i].worldX = gp.tileSize * 12;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 12;
        i++;
        gp.superObject[mapNum][i] = new OBJ_Chest(gp);
        gp.superObject[mapNum][i].setLoot(new OBJ_BlueShield(gp));
        gp.superObject[mapNum][i].worldX = gp.tileSize * 30;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 29;



        i = 0;
        mapNum = 2;
        gp.superObject[mapNum][i] = new OBJ_Chest(gp);
        gp.superObject[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.superObject[mapNum][i].worldX = gp.tileSize * 40;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 41;

        i++; gp.superObject[mapNum][i] = new OBJ_Chest(gp);
        gp.superObject[mapNum][i].setLoot(new OBJ_PotionRed(gp));
        gp.superObject[mapNum][i].worldX = gp.tileSize * 13;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 16;

        i++; gp.superObject[mapNum][i] = new OBJ_Chest(gp);
        gp.superObject[mapNum][i].setLoot(new OBJ_PotionRed(gp));
        gp.superObject[mapNum][i].worldX = gp.tileSize * 26;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 34;

        i++; gp.superObject[mapNum][i] = new OBJ_Chest(gp);
        gp.superObject[mapNum][i].setLoot(new OBJ_PotionRed(gp));
        gp.superObject[mapNum][i].worldX = gp.tileSize * 27;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 15;

        i++; gp.superObject[mapNum][i] = new OBJ_IronDoor(gp);
        gp.superObject[mapNum][i].worldX = gp.tileSize * 18;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 23;

        i = 0;
        mapNum = 3;

        gp.superObject[mapNum][i] = new OBJ_IronDoor(gp);
        gp.superObject[mapNum][i].worldX = gp.tileSize * 18;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 23;
        i++;
        gp.superObject[mapNum][i] = new OBJ_BlueHeart(gp);
        gp.superObject[mapNum][i].worldX = gp.tileSize * 25;
        gp.superObject[mapNum][i].worldY = gp.tileSize * 8;

    }

    public void setNPC(){
        int mapNum = 0;
        int i = 0;
        //MAP 0
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        //MAP 1
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 12;
        gp.npc[mapNum][i].worldY = gp.tileSize * 7;

        mapNum = 2;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 20;
        gp.npc[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.npc[mapNum][i] = new NPC_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 11;
        gp.npc[mapNum][i].worldY = gp.tileSize * 18;
        i++;
        gp.npc[mapNum][i] = new NPC_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 23;
        gp.npc[mapNum][i].worldY = gp.tileSize * 14;

        //gp.npc[0] = new NPC_OldMan(gp);
        //gp.npc[0].worldX = gp.tileSize * 9;
        //.gp.npc[0].worldY = gp.tileSize * 10;

    }

    public void setMonster() {
        int mapNum = 0;

        int i = 0;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 36;
        i++;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 38;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;
        i++;
        gp.monster[mapNum][i] = new MON_greenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;
        i++;
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 14;
        gp.monster[mapNum][i].worldY = gp.tileSize * 33;

         i = 0;
         mapNum = 2;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 36;
        gp.monster[mapNum][i].worldY = gp.tileSize * 25;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 39;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 10;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;

        i= 0;
        mapNum = 3;
        if(!Progress.skeletonBossDefeated) {
            gp.monster[mapNum][i] = new MON_Skeleton(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize * 23;
            gp.monster[mapNum][i].worldY = gp.tileSize * 16;
        }

    }

    public void setRedSlimes(){
        int i = 0;
        int mapNum = 0;

        gp.monster[mapNum][i] = new MON_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 36;
        gp.monster[mapNum][i].worldY = gp.tileSize * 10;
        i++;
        gp.monster[mapNum][i] = new MON_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 40;
        gp.monster[mapNum][i].worldY = gp.tileSize * 10;
        i++;
        gp.monster[mapNum][i] = new MON_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 40;
        gp.monster[mapNum][i].worldY = gp.tileSize * 8;
        i++;
        gp.monster[mapNum][i] = new MON_redSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 36;
        gp.monster[mapNum][i].worldY = gp.tileSize * 8;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,28,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,29,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,31,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,32,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,33,12);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,25,27);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,26,27);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,27);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,28);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,29);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,30);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,27,31);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,28,31);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,29,31);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,31);
        i++;
        gp.interactiveTile[mapNum][i] = new IT_DryTree(gp,30,30);



        i = 0;
        mapNum = 2;
        gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,18,30);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,17,31);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,17,32);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,17,34);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,18,34);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,18,33);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,10,22);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,10,24);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,38,18);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,38,19);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,38,20);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,38,21);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,18,13);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,18,14);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,22,28);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,22,28);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,30,28);
        i++;gp.interactiveTile[mapNum][i] = new IT_DesWall(gp,22,28);

        i++;gp.interactiveTile[mapNum][i] = new IT_MetalPlate(gp,20,22);
        i++;gp.interactiveTile[mapNum][i] = new IT_MetalPlate(gp,8,17);
        i++;gp.interactiveTile[mapNum][i] = new IT_MetalPlate(gp,39,31);




    }
}
