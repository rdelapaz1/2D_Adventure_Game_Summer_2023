package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_sword_normal extends Entity {
    public static final String objName = "Normal Sword";
    //CONSTRCUTOR
    public OBJ_sword_normal(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/sword_normal", gp.tileSize,gp.tileSize);
        attackValue = 4;
        description = "[" + name + "]" + "\nan old sword.";
        attackArea.width = 36;
        attackArea.height = 36;
        type = type_sword;
        price = 100;
        knockBackPower = 2;
        attack_motion1_duration = 5;
        attack_motion2_duration = 25;

    }
}
