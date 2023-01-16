package Backgrounds;

import AbstractGraphics.Point;
import SpritePack.Block;
import SpritePack.Sprite;
import biuoop.DrawSurface;

import java.awt.*;

public class BackgraounTwo implements Sprite{
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
    public void drawOn(DrawSurface d) {


        Color graound = new Color(201, 166, 10);
        Color sky = new Color(10, 130, 230);
        Color peramida = new Color(120, 69, 46);
        Color peramidaDeep = new Color(133, 91, 15);
        Color send = new Color(175, 146, 37);


        for(int i = 0;i< 5;i++){
            d.setColor(new Color(70 + i*5, 210+ i/20, 234));
            d.fillRectangle(0,(i * 4),800,600 - (i * 20));
        }
        d.setColor(send);
        send = send.darker();
        d.fillCircle(150, 550, 200);
        send = send.brighter();
        d.setColor(send);
        d.fillCircle(0, 600, 200);
        send = send.brighter();
        d.setColor(send);
        d.fillCircle(800, 550, 200);
        d.fillCircle(500, 700, 400);
        d.setColor(Color.black);
        d.drawCircle(500, 700, 400);


        d.setColor(graound);
        d.fillRectangle(0, 500, 800, 100);


        int counter = 5;
        for (int y = 470; y > 320; y -= 30) {
            int x1 = 50 + (5 - counter) * 20;
            for (int i = 1; i <= counter; i++) {
                d.setColor(peramida);
                d.fillRectangle(x1, y, 40, 30);
                d.setColor(peramidaDeep);
                d.fillRectangle(x1, y, 35, 25);
                x1 = x1 + 40;
            }
            counter--;
            x1 = x1 + 20;
        }

        counter = 15;
        int y1 = 520;
        for (int j = 0; j < 15; j++) {
            y1 = y1 -15;
            int x1 = 600 + (16 - counter) * 10;
            for (int i = 1; i <= counter; i++) {
                d.setColor(peramida);
                d.fillRectangle(x1, y1, 20, 15);
                d.setColor(peramidaDeep);
                d.fillRectangle(x1, y1, 15, 12);
                x1 = x1 + 20;
            }
            counter--;
            x1 = x1 + 10;
        }


        counter = 7;
        for (int y = 520; y > 320; y -= 30) {
            int x1 = 300 + (8 - counter) * 20;
            for (int i = 1; i <= counter; i++) {
                d.setColor(peramida);
                d.fillRectangle(x1, y, 40, 30);
                d.setColor(peramidaDeep);
                d.fillRectangle(x1, y, 35, 25);
                x1 = x1 + 40;
            }
            counter--;
            x1 = x1 + 20;
        }
    }

    /**
     * notify the sprite that time has passed
     */
    public void timePassed(){

    }
}
