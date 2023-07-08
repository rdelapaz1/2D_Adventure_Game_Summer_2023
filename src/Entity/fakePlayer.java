package Entity;

import Main.GamePanel;

public class fakePlayer extends  Entity{
    public static final String npcName = "Fake";
    public fakePlayer(GamePanel gp){
        super(gp);
        name = npcName;
        getImage();
    }

    public void getImage(){
        down1 = setup("player/boy_down_1",gp.tileSize, gp.tileSize);
        down2 = setup("player/boy_down_2",gp.tileSize, gp.tileSize);
        up1 = setup("player/boy_up_1",gp.tileSize, gp.tileSize);
        up2 = setup("player/boy_up_2",gp.tileSize, gp.tileSize);
        left1 = setup("player/boy_left_1",gp.tileSize, gp.tileSize);
        left2 = setup("player/boy_left_2",gp.tileSize, gp.tileSize);
        right1 = setup("player/boy_right_1",gp.tileSize, gp.tileSize);
        right2 =setup("player/boy_right_2",gp.tileSize, gp.tileSize);
    }

}
