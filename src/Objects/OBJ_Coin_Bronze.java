package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    public static final String objName = "Bronze Coin";
    public OBJ_Coin_Bronze(GamePanel gp){
        super(gp);
        type = type_pickup;
        name = objName;
        down1 = setup("Objects/coin_bronze",gp.tileSize,gp.tileSize);
        value = 1;
    }

    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
        return true;
    }


}
