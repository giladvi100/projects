package Backgrounds;

import AbstractGraphics.Point;
import SpritePack.Block;
import SpritePack.Sprite;
import biuoop.DrawSurface;

import java.awt.*;
import java.util.Random;

public class BackgraoundThree implements Sprite{
    private int[] arr;
    private Random rnd = new Random();
    public BackgraoundThree(){
        this.arr = new int[81];
        for(int i =0;i< arr.length;i++){
            arr[i] = rnd.nextInt(50,150);
        }
    }

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

        int n =60;
        int size = 80;
        for(int i = 1;i<n;i++){
            d.drawLine( 400, 0,(i *  size)- 1000,600);
        }
        int x = 0;
        int y = 0;
        int coloR = 23;
        int colorG = 63;
        int colorB = 88;
        for(int i = 0;i<255;i++) {
            if(colorB < 255) {
                if(i%2 == 255) {
                    colorB++;
                }
            }
            if(colorG<255) {
                //if(colorB%2 == 0) {
                    colorG++;
                //}
            }
            if(coloR < 255) {
                if(coloR %2 == 0) {
                    coloR++;
                }
            }
            d.setColor(new Color(coloR, colorG, colorB + 20));
            d.drawRectangle(x+ i, y + i, 800 -(i * 2), 600 - (i*2));
            d.setColor(new Color(coloR, colorG, colorB));
            d.fillRectangle(x+ i, y + i, 800 -(i * 2), 600 - (i*2));
        }



        d.setColor(new Color(10,60,192));



        for(int i =0;i<9;i++){
            int x1 = i*100;
            d.drawLine(400,300,x1,0);
        }
        for(int i =0;i<9;i++){
            int x1 = i*100;
            d.drawLine(400,300,x1,600);
        }
        for(int i =0;i<9;i++){
            int x1 = i*100;
            d.drawLine(400,300,800,x1);
        }
        for(int i =0;i<9;i++){
            int x1 = i*100;
            d.drawLine(400,300,0,x1);
        }
    }

    /**
     * notify the sprite that time has passed
     */
    public void timePassed(){

    }
}
