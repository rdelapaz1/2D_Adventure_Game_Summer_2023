package Tiles_Interactive;

import Entity.Entity;
import Main.GamePanel;

    public class IT_Trunk extends InteractiveTile{
        public IT_Trunk(GamePanel gp, int col, int row){
            super(gp,col,row);
            down1 = setup("Tiles_Interactive/trunk",gp.tileSize,gp.tileSize);
            destructible = false;
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
