package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_BlueShield extends Entity {
    public static final String objName = "Blue Shield";
    public OBJ_BlueShield(GamePanel gp){
        super(gp);
        type = type_shield;
        name = objName;
        down1 = setup("objects/shield_blue",gp.tileSize,gp.tileSize);
        defenseValue = 2;
        description = "[ " + name + "]\nShiny blue shield.";
        price = 100;

    }
}
