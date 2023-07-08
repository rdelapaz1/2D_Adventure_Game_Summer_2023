package Enviroment;

import Main.GamePanel;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.BufferedReader;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class Lighting {
    GamePanel gp;
    BufferedImage darkFilter;

    //DAY NIGHT VARIABLES
    public float filterAlpha = 0f;
   public  final int day = 0;
    public final int dusk = 1;
    final int night = 2;
    public final int dawn = 3;
    public int dayState = day;
    public int dayCounter = 0;

    //Constructor
    public Lighting(GamePanel gp) {
       this.gp = gp;
       setLightSource();
    }

    public void draw(Graphics2D g2){

        if(gp.currentArea == gp.outside){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,filterAlpha));
        }
        if(gp.currentArea == gp.outside ||gp.currentArea == gp.dungeon){
            g2.drawImage(darkFilter,0,0,null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));


        //DEBUG: Show current state on screen
        String dayTime = "";
        switch (dayState){
            case day: dayTime = "Day"; break;
            case dusk:dayTime = "Dusk"; break;
            case night:dayTime = "Night"; break;
            case dawn: dayTime = "Dawn"; break;
        }
        g2.setColor(Color.WHITE);
        g2.drawString(dayTime,800,500);
    }

    public void setLightSource(){
        //Buffered image
        darkFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darkFilter.getGraphics();

        if(gp.player.currentLight == null){
            g2.setColor(new Color(0,0,0,0.98f));
        }
        else{
            //get top center coordiantes for circle
            int centerX = gp.player.screenX + (gp.tileSize / 2);
            int centerY = gp.player.screenY + (gp.tileSize / 2);

            //GRADATION effect setup
            Color color[] = new Color[5];
            float fraction[] = new float[5];

            color[0] = new Color(0,0,0,0f);
            color[1] = new Color(0,0,0,0.25f);
            color[2] = new Color(0,0,0,0.5f);
            color[3] = new Color(0,0,0,0.75f);
            color[4] = new Color(0,0,0,0.98f);

            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.75f;
            fraction[4] = 1f;

            //Gradation paint settings for light circle
            RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX,centerY,gp.player.currentLight.lightRadius,fraction,color);

            //set gradiant data to g2 and draw circle
            g2.setPaint(gradientPaint);
        }

        //draw light circle
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.dispose();
    }

    public void update(){
        if(gp.player.lightUpdate == true){
            setLightSource();
            gp.player.lightUpdate = false;
        }
        //CHECK DAY STATE
        if(dayState == day){
            dayCounter++;
            if(dayCounter > (1800)){
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk){
            filterAlpha += 0.001f;
            if(filterAlpha > 1f){
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night){
            dayCounter++;
            if(dayCounter > 1800){
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn){
            filterAlpha -= 0.001f;
            if(filterAlpha < 0f){
                filterAlpha = 0;
                dayState = day;
            }
        }
    }

    public void resetDay(){
        dayState = day;
        filterAlpha = 0f;
    }

}
