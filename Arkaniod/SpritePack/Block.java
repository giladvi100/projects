package SpritePack;
import AbstractGraphics.Point;
import AbstractGraphics.Rectangle;
import Game.*;
import AbstractGraphics.Line;
import AbstractGraphics.Velocity;
import Game.GameEnvironment;
import ListenerAction.HitListener;
import ListenerAction.HitNotifier;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private Rectangle rec;
    private Color color;
    private String special;
    //const for the indexes in the array of the line around the block
    static final int LEFT =  0, UP = 1, RIGHT = 2, BUTTEM = 3;

    /**
     * constractor
     * @param p the pint of the right up point
     * @param whidth the whidth of the rectangle
     * @param hight the hight of the rectangle
     */
    public Block(Point p, double whidth, double hight){
        rec = new Rectangle(p,whidth,hight);
        this.color = Color.gray;
        this.hitListeners = new ArrayList<>();
        this.special = "regular";
    }

    /**
     * constractor
     * @param p the pint of the right up point
     * @param whidth the whidth of the rectangle
     * @param hight the hight of the rectangle
     * @param color the color of the block
     */
    public Block(Point p,double whidth, double hight,Color color,String kind){
        rec = new Rectangle(p,whidth,hight);
        this.color = color;
        this.hitListeners = new ArrayList<>();
        this.special = kind;
    }

    /**
     * getter method the retutn the color of the block
     * @return color of the block
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * get collidable and cast it to sprite
     * @param c the Collidable the object to cast
     * @return the Collidable as sprite
     */
    public Sprite castToSprite(Collidable c){
        Sprite s = this;
        return s;
    }

    /**
     * seter method the upgate the rec to be with the new value
     * @param p the pint of the right up point
     * @param whidth the whidth of the rectangle
     * @param hight the hight of the rectangle
     */
    public void setRectangle(Point p,double whidth, double hight){
        rec = new Rectangle(p, hight,whidth);
    }

    /**
     * method that reyturn the CollisionRectangle
     * @return the rectangle
     */
    public Rectangle getCollisionRectangle(){
        return this.rec;
    }

    /**
     * thr method get the collisionPoint and the currentVelocity and change the vec acording to where the hit acure
     * @param collisionPoint the interaction point
     * @param currentVelocity the old vec
     * @return new vec after update
     */
    //const epsilon is the range of the ball hit in the line
    static final double epsilon = 0.1;
    public Velocity hit(Point collisionPoint, Velocity currentVelocity,Ball b){
        //flag th see if hit ocure
        Boolean hitted = false;
        //arr is the line around the block
        Line[] arr = rec.getLineOfRectangle();
        //make a new vec to update
        Velocity newVec = new Velocity(currentVelocity.getDx(),currentVelocity.getDy());
        //makind a small line for checking inetaction with the line of the block
        Line l = new Line(collisionPoint.getX() + epsilon,collisionPoint.getY()+epsilon,collisionPoint.getX()-epsilon,collisionPoint.getY()-epsilon);

        //acording to the hitten line and the vec darection update the vec.
        if ((arr[BUTTEM].intersectionWith(l)!=null)&&newVec.getDy() < 0){
            newVec.setY(-1 * currentVelocity.getDy());
            hitted = true;
        }
        else if(((arr[UP].intersectionWith(l)!=null)&&newVec.getDy() > 0)){
            newVec.setY(-1 * currentVelocity.getDy());
            hitted = true;
        }
        else if ((arr[RIGHT].intersectionWith(l)!=null)&&newVec.getDx() < 0){
            newVec.setX(-1 * currentVelocity.getDx());
            hitted = true;
        }
        else if((arr[LEFT].intersectionWith(l)!=null)&&newVec.getDx() > 0) {
            newVec.setX(-1 * currentVelocity.getDx());
            hitted = true;
        }
        if(hitted){
            this.notifyHit(b);
        }
        return newVec;
    }


    /**
     * the method retuen the point of the closest ionterction point of the ball
     * @param trajectory the line of the direction of the ball
     * @param p1 the point of the ball
     * @param ge game invirment of all the objects in the game
     * @return IntarectionPoint
     */
    public Point getIntarectionPoint(Line trajectory, Point p1, GameEnvironment ge){
        Point interctionPoiont = null;
        //get the clossest obsicle
        Collidable clossestOb = ge.getClosestObsicle(trajectory, p1);
        Line[] arr = new Line[4];
        //check that there is any collidible we ganna hit
        if(clossestOb == null){
            return null;
        }
        //get to arr al the line around the block we ganna hit
        arr = clossestOb.getCollisionRectangle().getLineOfRectangle();
        double min = trajectory.length();
        //check of each line the intaraction (if there is)
        //and save the point the the distance from the ball is the smallest
        for(int i =0;i<arr.length;i++) {
            Point temp = arr[i].intersectionWith(trajectory);
            if (temp != null) {
                double distaceTemp = temp.distance(p1);

                if (distaceTemp < min) {
                    min = distaceTemp;
                    interctionPoiont = temp;
                }
            }
        }
        //return the clossest interaction point to the ball
        return interctionPoiont;
    }

    /**
     * the method update the surface and drow on the givven surface this block
     * @param surface the gioven surface.
     */
    public void drawOn(DrawSurface surface){
        Color c = new Color(this.color.brighter().getRGB());
        if(this.special == "bonus"){
            surface.setColor(Color.LIGHT_GRAY);
            c = Color.LIGHT_GRAY.brighter();
        }
        else if(this.special == "rmoveBall") {
            surface.setColor(Color.GRAY.darker());
            c = Color.GRAY.brighter();
        }else if((this.special == "forMorSpeed")||(this.special == "forLessSpeed")) {
            c = Color.LIGHT_GRAY;
        }else{
            //the color of the block
            surface.setColor(this.color);
        }
        //arr the line ariund the block
        Line[] arr = new Line[4];
        arr = this.rec.getLineOfRectangle();
        //drow the fill rectangle
        surface.fillRectangle((int)this.rec.getUpperLeftPoint().getX(),(int)this.rec.getUpperLeftPoint().getY(),(int)this.rec.getWidth(), (int)this.rec.getHeight());

        surface.setColor(c);
        surface.fillRectangle((int)this.rec.getUpperLeftPoint().getX(),(int)this.rec.getUpperLeftPoint().getY() ,(int)this.rec.getWidth() -5, (int)this.rec.getHeight() -5);
        //drow all the line around the blocks.
        for(int i =0;i< arr.length;i++){
            surface.setColor(Color.black);
            surface.drawLine((int)arr[i].start().getX(),(int)arr[i].start().getY(),(int)arr[i].end().getX(),(int)arr[i].end().getY());
        }
        if(this.special == "bonus"){
            surface.drawText((int)(this.rec.getUpperLeftPoint().getX()+ (this.rec.getHeight()/2)+10),(int)(this.rec.getUpperLeftPoint().getY() + (this.rec.getHeight()/2)+10),"+",20);
        }
        if(this.special == "rmoveBall"){
            surface.drawText((int)(this.rec.getUpperLeftPoint().getX()+ (this.rec.getHeight()/2)+10),(int)(this.rec.getUpperLeftPoint().getY() + (this.rec.getHeight()/2)+10),"!",20);
        }
        if(this.special == "forMorSpeed") {
            surface.drawText((int)(this.rec.getUpperLeftPoint().getX()+ 2),(int)(this.rec.getUpperLeftPoint().getY() + (this.rec.getHeight()/2+3)),"MORE speed",12);
        }
        if(this.special == "forLessSpeed"){
            surface.drawText((int)(this.rec.getUpperLeftPoint().getX()+ 2),(int)(this.rec.getUpperLeftPoint().getY() + (this.rec.getHeight()/2+3)),"LESS speed",12);
        }

    }



    /**
     * this method remove form the game(GameEnvironment and SpriteCollection) this block
     * @param game the playing game.
     */
    public void removeFromGame(GameLevel game){
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    public void timePassed(){
    }


    //--implements  HitNotifier--//

    /**
     * thith method add HitListener object to the list
     * @param hl the hitListener
     */
    public void addHitListener(HitListener hl){
        this.hitListeners.add(hl);
    }

    /**
     * this method remove from the list the givven hitListener
     * @param hl the hitListener
     */
    public void removeHitListener(HitListener hl){
        this.hitListeners.remove(hl);
    }

    /**
     * method that Notify all listeners about a hit event
     * @param hitter the ball tat make the hit
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    public String getKind(){
        return this.special;
    }
}
