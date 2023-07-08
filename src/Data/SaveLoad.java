package Data;

import Entity.Entity;
import Main.GamePanel;
import Objects.*;

import java.awt.*;
import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }


    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage dataStorage = new DataStorage();

            //PLAYER STATS
            dataStorage.level = gp.player.level;
            dataStorage.maxLife = gp.player.maxLife;
            dataStorage.life = gp.player.life;
            dataStorage.mana = gp.player.mana;
            dataStorage.maxMana = gp.player.maxMana;
            dataStorage.strength = gp.player.strength;
            dataStorage.dexterity = gp.player.dexterity;
            dataStorage.xp  = gp.player.xp;
            dataStorage.nextLevelXP = gp.player.nextLvlXP;
            dataStorage.coins = gp.player.coin;

            //PLAYER INVENTORY
            for(int i = 0; i < gp.player.inventory.size(); i++){
                dataStorage.itemNames.add(gp.player.inventory.get(i).name);
                dataStorage.amount.add(gp.player.inventory.get(i).amount);
            }

            //PLAYER CURRENT SWORD AND SHIELD
            dataStorage.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            dataStorage.currentShieldSlot = gp.player.getCurrentShieldSlot();

            //OBJ on map
            dataStorage.mapObjNames = new String[gp.maxMap][gp.superObject[1].length];
            dataStorage.mapObjWorldX = new int[gp.maxMap][gp.superObject[1].length];
            dataStorage.mapObjWorldY = new int[gp.maxMap][gp.superObject[1].length];
            dataStorage.mapLootNames = new String[gp.maxMap][gp.superObject[1].length];
            dataStorage.objOpened = new boolean[gp.maxMap][gp.superObject[1].length];

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++){
                for(int i = 0; i < gp.superObject[1].length; i++){
                    if(gp.superObject[mapNum][i] == null){
                        dataStorage.mapObjNames[mapNum][i] = "NULL";
                    }else{
                        dataStorage.mapObjNames[mapNum][i] = gp.superObject[mapNum][i].name;
                        dataStorage.mapObjWorldX[mapNum][i] = gp.superObject[mapNum][i].worldX;
                        dataStorage.mapObjWorldY[mapNum][i] = gp.superObject[mapNum][i].worldY;
                        if(gp.superObject[mapNum][i].loot != null){
                            dataStorage.mapLootNames[mapNum][i] = gp.superObject[mapNum][i].loot.name;
                        }
                        dataStorage.objOpened[mapNum][i] = gp.superObject[mapNum][i].opened;
                    }
                }
            }


            //WRITE
            oos.writeObject(dataStorage);
        }catch (Exception e){
            System.out.println("Save Exception");
        }
    }

    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            //Read data
            DataStorage dataStorage = (DataStorage) ois.readObject();
            gp.player.level = dataStorage.level;
            gp.player.life = dataStorage.life;
            gp.player.maxLife = dataStorage.maxLife;
            gp.player.mana = dataStorage.mana;
            gp.player.maxMana = dataStorage.maxMana;
            gp.player.strength = dataStorage.strength;
            gp.player.dexterity = dataStorage.dexterity;
            gp.player.xp = dataStorage.xp;
            gp.player.nextLvlXP = dataStorage.nextLevelXP;
            gp.player.coin = dataStorage.coins;

            //PLAYER INVENTORY
            gp.player.inventory.clear();
            for(int i = 0; i<dataStorage.itemNames.size(); i++){
                gp.player.inventory.add(gp.createEntity.getObject(dataStorage.itemNames.get(i)));
                gp.player.inventory.get(i).amount = dataStorage.amount.get(i);
            }

            //CURRENT WEAPON AND SHIELD
            gp.player.currentWeapon = gp.player.inventory.get(dataStorage.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(dataStorage.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for (int i = 0; i < gp.superObject[1].length; i++) {
                    if(dataStorage.mapObjNames[mapNum][i].equals("NULL")){
                        gp.superObject[mapNum][i] = null;
                    }
                    else{
                        gp.superObject[mapNum][i] = gp.createEntity.getObject(dataStorage.mapObjNames[mapNum][i]);
                        gp.superObject[mapNum][i].worldX = dataStorage.mapObjWorldX[mapNum][i];
                        gp.superObject[mapNum][i].worldY = dataStorage.mapObjWorldY[mapNum][i];
                        if(dataStorage.mapLootNames[mapNum][i] != null){
                            gp.superObject[mapNum][i].setLoot(gp.createEntity.getObject(dataStorage.mapLootNames[mapNum][i]));
                        }
                        gp.superObject[mapNum][i].opened = dataStorage.objOpened[mapNum][i];
                        if(gp.superObject[mapNum][i].opened == true){
                            gp.superObject[mapNum][i].down1 = gp.superObject[mapNum][i].image2;
                        }

                    }
                }
            }



        }catch (Exception e){
            System.out.println("Load Exception");
        }
    }

}
