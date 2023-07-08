package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {
    public static final String objName = "Boots";
    public OBJ_Boots(GamePanel gp){
        super(gp);
        name = objName;
        type = type_boots;
        description = "[Boots] Increase \nmovement speed.";
        down1 = setup("objects/boots",gp.tileSize,gp.tileSize);
        price = 10;
    }

}
