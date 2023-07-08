package Tiles_Interactive;

import Entity.Entity;
import Main.GamePanel;


public class InteractiveTile extends Entity {

    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row){
        super(gp);

    }

    public boolean isCorrectItem(Entity entity){
        boolean correctItem = false;
        return correctItem;
    }

    public void update(){
        if(invincible == true){
            invincibleCounter++;
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void PlaySE(){
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
}
