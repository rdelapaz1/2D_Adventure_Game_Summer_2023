package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_PotionRed extends Entity {
    public static final String objName = "Red Potion";
    public OBJ_PotionRed(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/potion_red",gp.tileSize,gp.tileSize);
        description = "[Red Potion]\nHeals life by " + value + ".";
        type = type_consumable;
        value = 5;
        price = 2;
        stackAble = true;
        setDialogue();
    }

    public boolean use(Entity entity){
       startDialogue(this,0);
        entity.life += value;
        gp.playSE(2);
        return true;
    }

    public void setDialogue(){
        dialogues[0][0] = "You drink the " + name + "!\nLife +5";
    }

}
