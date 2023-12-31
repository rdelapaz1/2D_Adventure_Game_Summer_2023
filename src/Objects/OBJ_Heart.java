package Objects;

import Entity.Entity;
import Main.GamePanel;



import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    public static final String objName = "heart";
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        type = type_pickup;
        value = 2;
        down1 = setup("objects/heart_full", gp.tileSize, gp.tileSize);
        name = objName;
        image = setup("objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("objects/heart_blank", gp.tileSize, gp.tileSize);
        price = 100;

    }

    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        entity.life += value;
        return true;
    }
}
