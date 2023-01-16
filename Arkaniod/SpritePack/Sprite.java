package SpritePack;
import biuoop.DrawSurface;
import java.awt.Color;
public interface Sprite {
    /**
     * func that give the color of the object.
     * @return the color
     */
    Color getColor();


    /**
     * func that drow on given surface.
     * @param d the given surface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed
      */
    void timePassed();
}