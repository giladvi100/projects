package Backgrounds;

import SpritePack.Sprite;
import biuoop.DrawSurface;
import java.awt.*;

public class BackgraoundOne implements Sprite {

    /**
     * func that give the color of the object.
     * @return the color
     */
    public Color getColor(){
        return Color.black;
    }


    /**
     * func that drow on given surface.
     * @param d the given surface.
     */
    public void drawOn(DrawSurface d){



        d.setColor(Color.blue.brighter().brighter().brighter().brighter().brighter().brighter().brighter());
        d.fillRectangle(0,0,800,600);
        d.setColor(Color.green.darker().darker());
        d.fillCircle(150,550,200);
        d.setColor(Color.green.darker());
        d.fillCircle(0,600,200);
        d.fillCircle(800,550,200);
        d.setColor(Color.green);
        d.fillCircle(500,700,400);
        d.setColor(Color.black);
        d.drawCircle(500,700,400);

        d.setColor(Color.YELLOW.brighter().brighter().brighter());
        d.fillCircle(180,180,60);
        d.setColor(Color.orange.brighter().brighter());
        d.fillCircle(180,180,55);
        d.setColor(Color.yellow);
        d.fillCircle(180,180,50);
        d.setColor(Color.gray.brighter());
        d.fillRectangle(200,200,150,50);
        d.fillCircle(210,200,25);
        d.fillCircle(200,225,25);
        d.fillCircle(225,215,25);
        d.fillCircle(250,190,25);
        d.fillCircle(275,180,25);
        d.fillCircle(300,190,25);
        d.fillCircle(325,215,25);
        d.fillCircle(350,225,25);



    }

    /**
     * notify the sprite that time has passed
     */
    public void timePassed(){

    }
}
