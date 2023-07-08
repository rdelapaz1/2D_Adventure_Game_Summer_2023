package Objects;


import Entity.Entity;
import Main.GamePanel;


public class OBJ_Axe extends Entity {

    public static final String objName = "Axe";

    public OBJ_Axe(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/axe", gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Axe]\nShorter but stronger.";
        type = type_axe;
        price = 100;
        knockBackPower = 10;
        attack_motion1_duration = 20;
        attack_motion2_duration = 40;
    }

}
