package Tiles_Interactive;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class IT_DesWall extends  InteractiveTile{
    public IT_DesWall(GamePanel gp, int col, int row){
        super(gp,col,row);
        down1 = setup("Tiles_Interactive/destructiblewall",gp.tileSize,gp.tileSize);
        destructible = true;
        this.worldX = col * gp.tileSize;
        this.worldY = gp.tileSize * row;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity){
        boolean correctItem = false;
        if(entity.currentWeapon.type == type_pickaxe){
            correctItem = true;
        }
        return correctItem;
    }

    public void PlaySE(){
        gp.playSE(20);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }

    public Color getParticleColor(){
        Color color = new Color(65,65,65);
        return  color;
    }

    public int getParticleSize(){
        int size = 6;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return  speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
