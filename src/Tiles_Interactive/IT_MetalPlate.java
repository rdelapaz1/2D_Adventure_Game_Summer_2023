package Tiles_Interactive;

import Main.GamePanel;

public class IT_MetalPlate extends InteractiveTile {
    GamePanel gp;
    public static final String itemName = "Metal Plate";

    public IT_MetalPlate(GamePanel gp, int col, int row){
        super(gp,col,row);
        this.gp = gp;
        name = itemName;
        down1 = setup("Tiles_Interactive/metalplate",gp.tileSize,gp.tileSize);
        this.worldX = col * gp.tileSize;
        this.worldY = gp.tileSize * row;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

    }
}
