package ListenerAction;

import SpritePack.Sprite;
import biuoop.DrawSurface;

import java.awt.*;

public class LevelNameDetector implements Sprite {
    private String levelName;
    private int levelNum;

    /**
     * constractor
     * @param levelName the name level we want to print
     */
    public LevelNameDetector(String levelName, int levelNum){
        this.levelName = levelName;
        this.levelNum = levelNum;
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
    public void drawOn(DrawSurface d){
        d.setColor(Color.black);
        d.drawText(100,20,"Levels: "+this.levelNum,15);
        d.drawText(540,20,"Level Name: "+this.levelName,15);
    }

    /**
     * notify the sprite that time has passed
     */
    public void timePassed(){
    }

}
