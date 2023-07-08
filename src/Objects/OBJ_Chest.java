package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp){
        super(gp);
        name = objName;
        price = 100;
        image = setup("objects/chest",gp.tileSize,gp.tileSize);
        image2 = setup("objects/chest_opened",gp.tileSize,gp.tileSize);
        down1 = image;
        type = type_obstacle;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
    }

    public void setLoot(Entity loot){
         this.loot = loot;
         setDialogue();
    }

    public void interact(){

        if(opened == false){
            gp.playSE(3);

            if(gp.player.canObtainItem(loot) == false){
               startDialogue(this,0);
            }else{
                startDialogue(this,1);
                down1 = image2;
                opened = true;
            }
        }else{
            startDialogue(this,2);
        }
    }

    public void setDialogue(){
        dialogues[0][0] = "You open the chest and find a " +  loot.name + "!\nBut inventory is full.";
        dialogues[1][0] = "You open the chest and find a " +  loot.name + "!\nnYou obtain the " + loot.name + "!";
        dialogues[2][0] = "Empty";

    }

}
