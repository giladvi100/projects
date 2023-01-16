package Backgrounds;

import SpritePack.Sprite;
import biuoop.DrawSurface;

import java.awt.*;

public class BckgraundFour implements Sprite {
    /**
     * func that give the color of the object.
     *
     * @return the color
     */
    public Color getColor() {
        return Color.black;
    }


    /**
     * func that drow on given surface.
     *
     * @param d the given surface.
     */
    public void drawOn(DrawSurface d) {
        for(int i = 0;i< 100;i++){
            d.setColor(new Color(70 + i, 210+ i/4, 234));
            d.fillRectangle(0,(i * 4),800,600 - (i * 4));
        }
        for(int i =0;i<10;i++) {
            d.setColor(new Color(255 - i*2, 255 - i*2, 255 - i*2));
            d.fillCircle(105, 140, 30 - i*3);
            d.fillCircle(135, 140, 30 - i*3);

            d.fillCircle(50, 150, 30 - i*3);
            d.fillCircle(80, 150, 30 - i*3);
            d.fillCircle(120, 150, 30- i*3);
            d.fillCircle(150, 150, 30 - i*3);
            d.fillCircle(90, 160, 30 - i*3);
            d.fillCircle(120, 165, 30 - i*3);
        }


        d.setColor(new Color(59, 114, 22));
        d.fillOval(200,440,200,250);
        d.setColor(Color.black);
        d.drawOval(200,440,200,250);
        d.setColor(new Color(57, 126, 2));
        d.fillOval(350,480,120,250);
        d.setColor(Color.black);
        d.drawOval(350,480,120,250);


        d.setColor(new Color(204, 72, 16));
        d.fillRectangle(0, 550, 800, 100);
        d.setColor(new Color(71, 176, 35));
        d.fillRectangle(0, 530, 800, 30);
        d.setColor(new Color(46, 152, 8));
        d.fillRectangle(0, 540, 800, 15);

        d.setColor(new Color(71, 176, 35));
        d.fillRectangle(500,430,70,100);
        d.setColor(new Color(84, 201, 46));
        d.fillRectangle(500,430,60,100);
        d.setColor(new Color(124, 225, 89));
        d.fillRectangle(500,430,50,100);
        d.setColor(new Color(151, 232, 124));
        d.fillRectangle(510,430,20,100);



        d.setColor(Color.black);
        d.drawRectangle(500,430,70,100);



        d.setColor(new Color(71, 176, 35));
        d.fillRectangle(490,400,90,30);
        d.setColor(new Color(84, 201, 46));
        d.fillRectangle(490,400,80,30);
        d.setColor(new Color(124, 225, 89));
        d.fillRectangle(490,400,60,30);
        d.setColor(new Color(151, 232, 124));
        d.fillRectangle(490,400,20,30);



        d.setColor(Color.black);
        d.drawRectangle(490,400,90,30);
    }

    /**
     * notify the sprite that time has passed
     */
    public void timePassed() {

    }
}
