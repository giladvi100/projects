package ListenerAction;
import biuoop.DrawSurface;
import Game.*;
import SpritePack.*;

import java.awt.Color;
public class ScoreIndicator implements Sprite{
    private Color color;
    private Counter score;

    /**
     * constractor
     * @param c the counter we want to print
     */
    public ScoreIndicator(Counter c){
        this.score = c;
        this.color = Color.black;
    }

    /**
     * func that give the color of the object.
     * @return the color
     */
    public Color getColor(){
        return this.color;
    }


    /**
     * func that drow on given surface.
     * @param d the given surface.
     */
    public void drawOn(DrawSurface d){
        d.drawText(380,20,"score: "+this.score.getValue(),22);
    }

    /**
     * notify the sprite that time has passed
     */
    public void timePassed(){
    }
}
