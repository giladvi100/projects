package Game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;


public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;

    public AnimationRunner(GUI gui){
        this.framesPerSecond = 60;
        this.gui = gui;
    }

    public void run(Animation animation){
        Sleeper sleeper = new Sleeper();
        //millisecondsPerFrame is the speed of the game
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            //d is our surface
            //the color of the background is blue
            //the size is 800X600
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);


            //show all the object on the screen

            // the whiting time
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            gui.show(d);

        }
    }
}
