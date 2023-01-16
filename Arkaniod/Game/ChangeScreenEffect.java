package Game;

import SpritePack.Sprite;
import SpritePack.SpriteCollection;
import biuoop.DrawSurface;

import java.awt.*;

public class ChangeScreenEffect implements Animation {
    boolean stop = false;
    private double numOfSeconds;
    private int countFrom;
    Sprite gameScreen;
    private long startTime;
    private long curntTime;
    private double timeForNum;
    private int size;
    private boolean gointBig;

    public ChangeScreenEffect(double numOfSeconds, int countFrom, Sprite gameScreen) {
        this.gameScreen = gameScreen;
        this.countFrom = countFrom;
        this.numOfSeconds = numOfSeconds;
        this.startTime = System.currentTimeMillis();
        this.timeForNum = this.numOfSeconds / countFrom;
        this.size = -80;
        this.gointBig = true;
    }

    /**
     * method that print one frame to the game.
     *
     * @param d the surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
        //get the screen to fraw in
        this.gameScreen.drawOn(d);
        //put timer for the animeition
        long usedTime = System.currentTimeMillis() - this.startTime;
        int timeForAnim = 1350;
        // if the animeition id more than the time for the animeiton put in stop true
        if (usedTime > timeForAnim) {
            this.stop = true;
        }
        //draw circels in difrent colors and make them bigger
        if (this.size <= 500 && gointBig) {
            d.setColor(Color.YELLOW);
            d.fillCircle(400, 300, this.size + 80);
            d.setColor(Color.ORANGE.brighter());
            d.fillCircle(400, 300, this.size + 70);
            d.setColor(Color.BLUE.brighter().brighter());
            d.fillCircle(400, 300, this.size + 60);
            d.setColor(Color.GREEN.brighter().brighter().brighter().brighter().brighter());
            d.fillCircle(400, 300, this.size + 50);
            d.setColor(Color.YELLOW);
            d.fillCircle(400, 300, this.size + 40);
            d.setColor(Color.ORANGE.brighter());
            d.fillCircle(400, 300, this.size + 30);
            d.setColor(Color.BLUE.brighter().brighter());
            d.fillCircle(400, 300, this.size + 20);
            d.setColor(Color.ORANGE.brighter().brighter().brighter().brighter().brighter());
            d.fillCircle(400, 300, this.size + 10);
            d.setColor(Color.gray.brighter().brighter().brighter().brighter().brighter());
            d.fillCircle(400, 300, this.size);
            d.setColor(Color.GREEN.brighter().brighter().brighter().brighter().brighter());
            d.fillCircle(400, 300, this.size - 10);
            d.setColor(Color.blue.brighter().brighter().brighter().brighter().brighter());
            d.fillCircle(400, 300, this.size - 20);

            this.size += 10;
        } else {
            //that mean now we need to do the circle smaller.
            this.gointBig = false;
            this.size -= 10;
            //print the circle.
            d.setColor(Color.blue.brighter().brighter().brighter().brighter().brighter());
            d.fillCircle(400, 300, this.size);

        }
    }

    /**
     * method that ansser if we need to end the animeition.
     * @return true if we need to stop and false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
