package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_BlueHeart  extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp){
        super(gp);

        this.gp = gp;
        type = type_pickup;
        name = objName;
        down1 = setup("objects/blueheart",gp.tileSize,gp.tileSize);


    }

    public void setDialogue(){
        dialogues[0][0] = "Legendary tressure acquired!";
        dialogues[0][1] = "You found the the rare \nblue heart!";
    }

    public boolean use(Entity entity){
        gp.gameState = gp.custsceneState;
        gp.cutsceneManger.sceneNum = gp.cutsceneManger.ending;
        return true;
    }
}
