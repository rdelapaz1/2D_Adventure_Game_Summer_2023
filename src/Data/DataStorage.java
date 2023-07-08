package Data;

//Allows writing and reading of DataStorage class
import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    //PLAYER STATS
    int level;
    int maxLife;
    int life;
    int mana;
    int maxMana;
    int strength;
    int dexterity;
    int xp;
    int nextLevelXP;
    int coins;

    //player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> amount = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    //map objects
    String mapObjNames[][];
    int mapObjWorldX[][];
    int mapObjWorldY[][];

    String mapLootNames[][];

    boolean objOpened[][];

}
