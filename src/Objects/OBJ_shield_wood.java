package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_shield_wood extends Entity {
    public static final String objName = "Wood Shield";
    public OBJ_shield_wood(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/shield_wood", gp.tileSize,gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]" + "\nmade by wood.";
        type = type_shield;

        price = 100;

    }
}
