package Enviroment;

import Main.GamePanel;

import java.awt.*;

public class EnviromentManager {
    GamePanel gp;
    public Lighting lighting;

    //Constructor
    public EnviromentManager(GamePanel gp){
        this.gp = gp;
    }

    public void setUp(){
        lighting = new Lighting(gp);
    }

    public void update(){
        lighting.update();
    }

    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }

}
