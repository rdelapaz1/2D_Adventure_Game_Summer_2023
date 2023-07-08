package Entity;

import Main.GamePanel;
import Objects.*;

import java.awt.*;

public class NPC_Merchant extends Entity{
    public NPC_Merchant(GamePanel gp) {
        super(gp);

        direction = "down";
        playerSpeed = 1;

        solidArea = new Rectangle(8,16,32,32);
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        down1 = setup("npc/merchant_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("npc/merchant_down_2",gp.tileSize,gp.tileSize);
        up1 = setup("npc/merchant_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("npc/merchant_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("npc/merchant_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("npc/merchant_down_2",gp.tileSize,gp.tileSize);
        right1 = setup("npc/merchant_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("npc/merchant_down_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0][0] = "Want to trade?";
        dialogues[1][0] = "Come again!";
        dialogues[2][0] = "Not enough coins.";
        dialogues[3][0] = "Not enough space.";
        dialogues[4][0] = "Cannot sell equipped item";


    }

    public void setItems(){
        inventory.add(new OBJ_PotionRed(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Tent(gp));
        inventory.add(new OBJ_Boots(gp));
    }

    public void speak(){
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
