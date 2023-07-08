package Objects;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class OBJ_Tent extends Entity {
    public static final String objName = "Tent";
    //CONSTRUCTOR
    public OBJ_Tent(GamePanel gp){
        super(gp);

        //VARIABLES
        type = type_consumable;
        name = objName;
        down1 = setup("objects/tent",gp.tileSize,gp.tileSize);
        description = "[Tent]\nSkip night time\n and go to sleep.";
        price = 2;
        stackAble = true;

    }

    public boolean use(Entity entity){
        gp.player.getSleepImage(down1);
        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        return true;
    }
}
