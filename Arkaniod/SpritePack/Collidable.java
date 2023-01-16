package SpritePack;
import AbstractGraphics.Line;
import AbstractGraphics.Point;
import AbstractGraphics.Rectangle;
import AbstractGraphics.Velocity;
import Game.GameEnvironment;
import biuoop.DrawSurface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface Collidable {
    // Return the "collision shape" of the object.

    /**
     * func the give the rentangke of the object.
     * @return Rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * making up Casting to Collidable
     * @param c the Collidable
     * @return sprite.
     */
    public Sprite castToSprite(Collidable c);
    /**
     * frow the shpe on given surface.
     * @param surface the gioven surface.
     */
    void drawOn(DrawSurface surface);

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     *  the force the object inflicted on us).
     * @param collisionPoint the interaction point
     * @param currentVelocity the old vec
     * @return the vec after the hit.
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball b);
    public Point getIntarectionPoint(Line trajectory, Point p1, GameEnvironment ge);
}