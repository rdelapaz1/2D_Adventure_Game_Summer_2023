package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_IronDoor extends Entity {
    public static final String objName = "Iron Door";
    public OBJ_IronDoor(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/door_iron",gp.tileSize,gp.tileSize);
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
        dialogues[0][0] = "Wont move.";
    }
}
