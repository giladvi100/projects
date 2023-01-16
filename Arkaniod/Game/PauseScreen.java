package Game;
import SpritePack.Sprite;
import SpritePack.SpriteCollection;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private GUI gui;
    SpriteCollection sprites;
    int size = 0;
    int bigger = 2;
    int smaller = 2;
    boolean getBigger = true;
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * constractor
     * @param k the KeyboardSensor of the game
     * @param sprites all tyhe game to print also SpriteCollection
     */
    public PauseScreen(KeyboardSensor k, SpriteCollection sprites) {
        this.keyboard = k;
        this.stop = false;
        this.sprites = sprites;
    }

    /**
     * method that print one frame at a time of the pausScreen
     * @param d the surface to print on.
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.black);
        //print all the game
        this.sprites.drawAllOn(d);
        //print three circles
        d.setColor(Color.blue.brighter().brighter().brighter());
        d.fillCircle(400,300,253);
        d.setColor(Color.blue.brighter().brighter());
        d.fillCircle(400,300,250);
        d.setColor(Color.blue.darker());
        d.fillCircle(400,300,245);


        //make a little animation
        if(size <= 245 && getBigger) {
            size = size + bigger;
            d.setColor(new Color(3, 152, 218).darker());
            d.fillCircle(400, 300, size);
            d.setColor(new Color(3, 152, 218));
            d.fillCircle(400, 300, size - 10);
        }else{
            getBigger = false;
            d.setColor(new Color(3, 152, 218).darker());
            d.fillCircle(400, 300, size);
            d.setColor(new Color(3, 152, 218));
            d.fillCircle(400, 300, size - 10);

            size = size - smaller;
        }
        if(size <= 0){
            getBigger = true;
        }


        //print to rectangles for the pause shape
        d.setColor(Color.blue.brighter().brighter());
        d.fillRectangle(340,200,40,200);
        d.fillRectangle(420,200,40,200);

        //print the text for the pause.
        d.setColor(Color.gray.brighter());
        d.drawText(180, d.getHeight() / 2, "paused -- press space to continue", 30);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    /**
     * methos that tell ass to stop this animation of the pause screen.
     * @return true if we want to end the animation false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}