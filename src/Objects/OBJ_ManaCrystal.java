package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    public static final String objName = "Mana Crystal";
    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        type = type_pickup;
        value  = 1;
        down1 = image = setup("Objects/manacrystal_full",gp.tileSize,gp.tileSize);
        name = objName;
        image = setup("Objects/manacrystal_full",gp.tileSize,gp.tileSize);
        image2 = setup("Objects/manacrystal_blank",gp.tileSize,gp.tileSize);
        price = 100;

    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Mana +" + value);
        entity.mana += value;
        return true;
    }
}
