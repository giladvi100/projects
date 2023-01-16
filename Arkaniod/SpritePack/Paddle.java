package SpritePack;
import AbstractGraphics.*;
import AbstractGraphics.Point;
import AbstractGraphics.Rectangle;
import AbstractGraphics.Velocity;
import Game.GameEnvironment;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import Game.*;

import java.awt.*;

public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Point paddelLocetion;
    private Collidable paddelBlock;
    private Color color;
    private  double speedOfPaddel = 12;


    /**
     * consractor
     */
    public Paddle(GUI gui){
        this.keyboard = gui.getKeyboardSensor();
        this.paddelLocetion = new Point(360,570);
        this.paddelBlock = new Block(paddelLocetion,15,200);
        this.color = Color.CYAN;
    }
    public Paddle(GUI gui, int speed, int w){
        this.keyboard = gui.getKeyboardSensor();
        this.paddelLocetion = new Point(400-w/2,570);
        this.paddelBlock = new Block(paddelLocetion,15,w);
        this.color = Color.CYAN;
        this.speedOfPaddel = speed;
    }

    /**
     * get the color of the paddle.
     * @return
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * get the intarscion point with this paddel
     * @param l line of the moovment of the ball.
     * @param p the intarction poiint
     * @param ge the game envirment.
     * @return the interscion point.
     */
    public Point getIntarectionPoint(Line l, Point p, GameEnvironment ge){
        return this.paddelBlock.getIntarectionPoint(l,p,ge);
    }

    /**
     * doing upCasting
     * @param c as Collidable
     * @return as sprite
     */
    public Sprite castToSprite(Collidable c){
        return this;
    }


    public void incrisSpeed(int num){
        this.speedOfPaddel += num;
    }
    /**
     * func that move left in speed of speedOfPaddel
     */
    public void moveLeft(){
        if(this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            if (this.paddelLocetion.getX() > -this.paddelBlock.getCollisionRectangle().getWidth() + 40) {
                this.paddelLocetion.setX(this.paddelLocetion.getX() - this.speedOfPaddel);
            }
        }
    }
    /**
     * func that move right in speed of speedOfPaddel
     */
    public void moveRight(){
        if(this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            if (this.paddelLocetion.getX() < 760) {
                this.paddelLocetion.setX(this.paddelLocetion.getX() + this.speedOfPaddel);
            }
        }
    }

    // Sprite

    /**
     * func that make the moovment of the paddel.
     */
    public void timePassed(){
        this.moveLeft();;
        this.moveRight();

    }

    /**
     * this func draw on givven surface the paddel.
     * @param d the givven surface.
     */
    public void drawOn(DrawSurface d){
        d.setColor(Color.red);
        this.paddelBlock.drawOn(d);
    }


    // Collidable

    /**
     * getter for the rectangle
     * @return the rectangle.
     */
    public Rectangle getCollisionRectangle(){
        return this.paddelBlock.getCollisionRectangle();
    }

    //the number of parts in the paddel.
    static final int rigionNum = 5;

    /**
     *func that split this paddel to five section and with givven point return the section the point is in.
     * @param collisionPoint the point we check in what he is.
     * @return the number of the section of the paddel.
     */
    public int wichRigionPaddel(Point collisionPoint){
        //regionSize is the size of each part in the paddel and each part cgange the direction of the hitten ball diffrent
        double regionSize =  this.paddelBlock.getCollisionRectangle().getWidth() / rigionNum;
        //check wich part the ball hgit and return his number
        if(this.paddelLocetion.getX() <= collisionPoint.getX() && collisionPoint.getX() <= regionSize + this.paddelLocetion.getX()){
            return 1;
        }
        else if(this.paddelLocetion.getX() + regionSize < collisionPoint.getX() && collisionPoint.getX() < (2 *regionSize) + (this.paddelLocetion.getX())){
            return 2;
        }
        else if(this.paddelLocetion.getX() + (regionSize * 3) < collisionPoint.getX() && collisionPoint.getX() < (regionSize *4)+this.paddelLocetion.getX()){
            return 4;
        }else if(this.paddelLocetion.getX() + (regionSize * 4) <= collisionPoint.getX() && collisionPoint.getX() <= (regionSize *5)+this.paddelLocetion.getX()){
                return 5;
        }
        else {
            return 3;
        }
    }

    /**
     * func that withe given point and vector return the the new vector
     * that after we considered the section in the paddel that the ball hit.
     * @param collisionPoint the point of interaction.
     * @param currentVelocity the old vector.
     * @return the nre vector.
     */
    static final double chngeDegreeForEdges = 60, chngeDegreeForMiddel = 30;
    public Velocity changVecByRegionIntarcionPaddle(Point collisionPoint, Velocity currentVelocity){
        //get the rigion of the interaction
        int rigionPaddle = this.wichRigionPaddel(collisionPoint);
        Velocity newVec = new Velocity(currentVelocity.getDx(),currentVelocity.getDy());
        //change the vec acordinbg to the section in the paddel
        if(rigionPaddle == 1){
            newVec.changByAngle(-chngeDegreeForEdges);
        }
        else if(rigionPaddle == 2){
            newVec.changByAngle(-chngeDegreeForMiddel);
        }
        else if(rigionPaddle == 4){
            newVec.changByAngle(chngeDegreeForMiddel);
        }
        else if(rigionPaddle == 5){
            newVec.changByAngle(chngeDegreeForEdges);
        }
        else if(newVec.getDy() > 0){
            //newVec.setY(-newVec.getDy());
        }

        return newVec;
    }

    //cost that will save as the index of the line around the paddel.
    static final int LEFT =  0, UP = 1, RIGHT = 2, BUTTEM = 3;

    /**
     * chang the vector of the ball acording to wich side od the paddel the ball hit.
     * @param collisionPoint the point of interaction
     * @param currentVelocity the old vec of the ball.
     * @return the new vec
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball b){
        Line[] arr = new Line[4];

        arr =  this.paddelBlock.getCollisionRectangle().getLineOfRectangle();
        Velocity newVec = new Velocity(currentVelocity.getDx(),currentVelocity.getDy());
        Line l = new Line(collisionPoint.getX() + 1,collisionPoint.getY()+1,collisionPoint.getX()-1,collisionPoint.getY()-1);

        //after ew have arr of the line that around the paddel we checl for each of them
        //if the interaction point is on them and chand the vec acordinglly
        if ((arr[BUTTEM].intersectionWith(l)!=null)&&newVec.getDy()<0){
            newVec.setY(-1 * currentVelocity.getDy());
        }
        else if(((arr[UP].intersectionWith(l)!=null)&&newVec.getDy()>0)){
            newVec.setY(-1 * currentVelocity.getDy());
            newVec= this.changVecByRegionIntarcionPaddle(collisionPoint,newVec);
        }
        else if ((arr[RIGHT].intersectionWith(l)!=null)&&newVec.getDx()<0){
            newVec.setX(-1 * currentVelocity.getDx());
        }
        else if((arr[LEFT].intersectionWith(l)!=null)&&newVec.getDx()>0) {
            newVec.setX(-1 * currentVelocity.getDx());
        }
        if(newVec.getDy()>0){
            newVec.setY(newVec.getDy()*-1);
        }
        if(newVec.getDy() == 0){
            newVec.setY(0.5);
        }
        return newVec;
    }

    /**
     * the func add this paddel to a given game.
     * @param g the game
     */
   // public void addToGame(GameLevel g){
   //     g.addCollidable(this);
   // }
}