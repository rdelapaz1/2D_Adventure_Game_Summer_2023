package Main;

import Entity.Entity;
import Objects.*;

public class CreateEntity {
    GamePanel gp;
    public CreateEntity(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;
        switch (itemName){
            case OBJ_IronDoor.objName:
                obj  = new OBJ_IronDoor(gp);
                break;
            case OBJ_Pickaxe.objName:
                obj  = new OBJ_Pickaxe(gp);
                break;
            case OBJ_Rock.objName:
                obj = new OBJ_Rock(gp);
                break;
            case OBJ_ManaCrystal.objName:
                obj = new OBJ_ManaCrystal(gp);
                break;
            case OBJ_Heart.objName:
                obj = new OBJ_Heart(gp);
                break;
            case OBJ_Axe.objName:
                obj = new OBJ_Axe(gp);
                break;
            case OBJ_BlueShield.objName:
                obj = new OBJ_BlueShield(gp);
                break;
            case OBJ_Boots.objName:
                obj = new OBJ_Boots(gp);
                break;
            case OBJ_Key.objName:
                obj = new OBJ_Key(gp);
                break;
            case OBJ_Lantern.objName:
                obj = new OBJ_Lantern(gp);
                break;
            case OBJ_PotionRed.objName:
                obj = new OBJ_PotionRed(gp);
                break;
            case OBJ_shield_wood.objName:
                obj = new OBJ_shield_wood(gp);
                break;
            case OBJ_sword_normal.objName:
                obj = new OBJ_sword_normal(gp);
                break;
            case OBJ_Tent.objName:
                obj = new OBJ_Tent(gp);
                break;
            case OBJ_Door.objName:
                obj = new OBJ_Door(gp);
                break;
            case OBJ_Chest.objName:
                obj = new OBJ_Chest(gp);
                break;
            case OBJ_Coin_Bronze.objName:
                obj = new OBJ_Coin_Bronze(gp);
                break;
            case OBJ_Fireball.objName:
                obj = new OBJ_Fireball(gp);
                break;
        }
        return obj;
    }
}
