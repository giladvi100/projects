package Game;

import SpritePack.SpriteCollection;
import biuoop.DrawSurface;

import java.awt.*;

public class CountdownAnimation implements Animation {
    boolean stop = false;
    private double numOfSeconds;
    private int countFrom;
    SpriteCollection gameScreen;
    private long startTime;
    private long curntTime;
    private double timeForNum;

    /**
     * constractor
     * @param numOfSeconds the num of seccend we want the countDown will play.
     * @param countFrom the number we stating the count from.
     * @param gameScreen the SpriteCollection for draw the game
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        //initilaize the filed of the class with the given args
        this.gameScreen = gameScreen;
        this.countFrom = countFrom;
        this.numOfSeconds = numOfSeconds;
        this.startTime = System.currentTimeMillis();
        this.timeForNum = this.numOfSeconds / countFrom;
    }
    //parametrs for the animeition
    int preNum = this.countFrom;
    int size=32;
    int i = 0;

    /**
     * method that draw one frame each time
     * @param d dhe surface to draw on.
     */
    public void doOneFrame(DrawSurface d) {
        //print the level on the surface.
        this.gameScreen.drawAllOn(d);
        //update the used tome and tiome left
        long usedTime = System.currentTimeMillis() - this.startTime;
        int timeLeft = this.numToPrint(usedTime);
        //while we show the same number get it bigger
        if(timeLeft == preNum){
            i +=2;
        }else{
            //else the num of the count in changed do reupdate the i to 0 and the size again to 32
            i = 0;
            size = 32;
            preNum = timeLeft;
        }
        //if time left == -1 its time to end
        if (timeLeft == -1) {
            this.stop = true;
        } else if (timeLeft == 0) {
            //if time left is 0 print go
            d.setColor(Color.black);
            //400 is the x val and d.getHeight() is the y val.
            //the calaulate is for remaine the text in the middel of the screen.
            d.drawText(400 - (int)(i/1.5), (d.getHeight() / 2)+i/4, "GO", size+i);
        } else {
            //if it isnt 0 so print the number to count
            d.setColor(Color.black);
            //400 is the x val and d.getHeight() is the y val.
            //the calaulate is for remaine the text in the middel of the screen.
            d.drawText(400 - i/4, (d.getHeight() / 2)+i/4, String.valueOf(timeLeft), size+i);
        }
    }

    /**
     * method the return stop true when the animeition shuild stop.
     * @return true when the animeition shuild stop ane false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * ethod that caculate the number to print whith the left time.
     * @param usedTine the time that we used.
     * @return the number to print and return -1 insiud of 0.
     */
    public int numToPrint(long usedTine){
        //total time divide by the numbers we count is the time for one number.
        double timeForNum = ((this.numOfSeconds * 1000)/ this.countFrom + 1);
        //for the first count
        if(usedTine >= 0 && usedTine < timeForNum){
            return this.countFrom;
        }
        //for all the other count find the number.
        for(int i = 1 ;i<=this.countFrom;i++){
            if(usedTine >= i * timeForNum && usedTine<(i+1)*timeForNum){
                return countFrom - i;
            }
        }
        return -1;
    }
}