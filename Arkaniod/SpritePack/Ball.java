package SpritePack;

import AbstractGraphics.*;
import Game.*;
import biuoop.DrawSurface;
import ListenerAction.*;

import java.awt.Color;
import java.util.List;

public class Ball implements Sprite, HitNotifier {
    private List<HitListener> hitListeners;

    //the loction of the center of the ball
    private Point center;
    //the size of the ball(radis of the circle)
    private int radius;
    //color of the ball
    private java.awt.Color color;
    //vector for the movment of the ball.
    private Velocity vec;

    private GameEnvironment gameEn;

    /**
     * constructor for defolt ball
     */
    public Ball() {
        Point p = new Point(0, 0);
        this.center = p;
        this.radius = 10;
        this.color = Color.black;
        this.vec = new Velocity(0, 0);
    }

    /**
     * constructor for ball with parametrs and point of the center of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.vec = new Velocity(0, 0);
    }

    /**
     * constructor for ball with parametrs and x and y value of the point of the center of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        Point p = new Point(x, y);
        this.center = p;
        this.radius = r;
        this.color = color;
        this.vec = new Velocity(0, 0);
    }

    /**
     * getter methd thet return the center point of the ball.
     *
     * @return point of the the center of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * getter methd thet return the speed of the ball.
     *
     * @return the speed of the ball
     */
    public double getSpeedOfTheBall() {
        return Math.sqrt((this.vec.getDx() * this.vec.getDx()) + (this.vec.getDy() * this.vec.getDy()));
    }

    /**
     * seter method the set the GameEnvironment to the ball
     *
     * @param ge the given GameEnvironment
     */
    public void setGameEnvironment(GameEnvironment ge) {
        this.gameEn = ge;
    }

    /**
     * set vectir in this ball
     *
     * @param v the vector
     */
    public void setVelocity(Velocity v) {
        this.vec = v;
    }

    /**
     * set a vector with tow doubls
     *
     * @param dx the move on the aixs x
     * @param dy the move on the aixs y
     */
    public void setVelocity(double dx, double dy) {
        this.vec = new Velocity(dx, dy);
    }

    /**
     * get the x of the ball loction
     *
     * @return the x value
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * get the y of the ball loction
     *
     * @return the y value
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * get the size of the ball.
     *
     * @return the size value
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * get the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on the given DrawSurface
     *
     * @param surface the frame we work on for this ball.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color.darker());
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.getSize() + 2);
        surface.setColor(this.color.brighter().brighter());
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), getSize() - 1);
        surface.setColor(Color.white);
        surface.fillCircle((int) this.center.getX() - 2, (int) this.center.getY() - 2, 1);

    }

    /**
     * get the vector of the boll movment.
     *
     * @return the vector of this ball/
     */
    public Velocity getVelocity() {
        return this.vec;
    }

    /**
     * update the loction of the center of the ball with the vector value.
     *
     * @return the color of the ball
     */
    public void moveOneStep() {

        //male line l the step of the ball of this step
        Line l = this.vec.makeLineOfNexSteps(this.center, this.radius);
        if (this.vec.getDx() == 0) {
            this.vec.setX(0.1);
        }
        Velocity oldVec = new Velocity(this.vec.getDx(), this.vec.getDy());
        //get point of the InterctionPoint with our l and the closest Obsicle if there is
        Point p = this.gameEn.getClosestInterctionPoint(l, this.center);

        //get a list of all Intarcton Obsocile
        List<Collidable> obsicles = this.gameEn.getIntarctonObsocile(l);
        Collidable c = this.gameEn.getClosestObsicle(l, this.center);


        //check that there is ane interactionn
        if (p != null && c != null) {
            // check for another interction at the same point.
            for (int i = 0; i < obsicles.size() && i < 2; i++) {
                //change the vec acording to the hit
                this.setVelocity(obsicles.get(i).hit(p, this.vec, this));
            }
        }
        //update the location after the hit
        this.center = this.vec.applyToPoint(this.center);
        this.returnBallToGme();
    }

    private void getOut(Collidable c){
       // Line l = this.vec.makeBiglineOfPreSteps(this.center,this.radius);
       // //Point p = c.getIntarctonObsocile(l);
       // this.center.setX(p.getX() - 1);
       // this.center.setY(p.getY() -1);
    }
    /**
     * func for the interface for all the thing than need to be done at every moovment of the ball.
     */
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * this method remove from given Game this ball
     *
     * @param g the given game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    public Collidable isInside(List<Collidable> obsicles) {
        double epsilon = 1;
        for (int i = 0; i < obsicles.size(); i++) {
            double xOfObStrat = obsicles.get(i).getCollisionRectangle().getUpperLeftPoint().getX();
            double yOfObStart = obsicles.get(i).getCollisionRectangle().getUpperLeftPoint().getY();
            double xOfObEnd = obsicles.get(i).getCollisionRectangle().getUpperLeftPoint().getX() + obsicles.get(i).getCollisionRectangle().getWidth();
            double yOfObEnd = obsicles.get(i).getCollisionRectangle().getUpperLeftPoint().getY() + obsicles.get(i).getCollisionRectangle().getHeight();

            if (this.center.getX() < xOfObEnd && this.center.getX() > xOfObStrat) {
                if (this.center.getY() < yOfObEnd && this.center.getY() > yOfObStart) {
                    System.out.println("inside");
                    return obsicles.get(i);

                }
            }
        }
        return null;
    }

    /**
     * method that take the ball and move it one and a little more step vack.
     */
    private void moveBack(){
        this.vec.setX((this.vec.getDx() * -1));
        this.vec.setY((this.vec.getDy() * -1));
        this.center.setX(this.center.getX() + this.vec.getDx()*0.3);
        this.center.setY(this.center.getY() + this.vec.getDy()*0.3);
        this.vec.setX((this.vec.getDx() * -1));
        this.vec.setY((this.vec.getDy() * -1));
    }



    /**
     * method that add listener to this ball
     *
     * @param hl the listener
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * method that remove given listener from this ball
     *
     * @param hl the listener
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * method that check if the ball is out of the screen
     */
    private void returnBallToGme() {
        if (this.center.getX() >= 795) {
            this.center.setX(750);
        }
        if (this.center.getX() <= 5) {
            this.center.setX(15);
        }
        if (this.center.getY() <= 5) {
            this.center.setY(25);
        }
        if (this.center.getY() >= 850) {
            this.center.setY(800);
        }
    }
}
