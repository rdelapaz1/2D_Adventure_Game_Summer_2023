package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {
    public static final String objName = "Door";
    public OBJ_Door(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/door",gp.tileSize,gp.tileSize);
        collision = true;
        price = 100;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        type = type_obstacle;
        setDialogue();
    }

    public void interact(){
       startDialogue(this,0);
    }

    public void setDialogue(){
        dialogues[0][0] = "You need a key.";
    }


}
