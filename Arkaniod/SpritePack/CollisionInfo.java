package SpritePack;
import AbstractGraphics.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionInfo {
    private Collidable collidable;
    private Point collisionPoint;


    // the point at which the collision occurs.
    public void setCollidable(Collidable c){
        this.collidable = c;
    }

    public void setCollisionPoint(Point p){
        this.collisionPoint= p;
    }

    public Point collisionPoint(){
        return this.collisionPoint;
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject(){
        return this.collidable;
    }
}