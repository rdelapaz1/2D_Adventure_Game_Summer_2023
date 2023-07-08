package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Pickaxe extends Entity {
    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/pickaxe", gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Pickaxe]\nUsed for digging.";
        type = type_pickaxe;
        price = 10;
        knockBackPower = 10;
        attack_motion1_duration = 10;
        attack_motion2_duration = 20;
    }
}
