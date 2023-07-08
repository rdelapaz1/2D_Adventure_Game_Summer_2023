package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends Entity {
    public static final String objName = "Key";
    public OBJ_Key(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("objects/key",gp.tileSize,gp.tileSize);
        description = "[" + name + "]" + "\nIt opens doors.";
        price = 100;
        type = type_consumable;
        stackAble = true;
        setDialogue();
    }

    public boolean use(Entity entity){
        //open inventory, use key, open door
        int objIndex = getDetected(entity,gp.superObject,"Door");

        if(objIndex != 999){
           startDialogue(this,0);
            gp.playSE(3);
            gp.superObject[gp.currentMap][objIndex] = null;
            return true;
        } else{
           startDialogue(this,1);
            return false;
        }

    }

    public void setDialogue(){
        dialogues[0][0] =  "Key used. Door open!";
        dialogues[1][0] =  "Can't use here";
    }

}
