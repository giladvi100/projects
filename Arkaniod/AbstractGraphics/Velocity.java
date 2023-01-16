package AbstractGraphics;

import SpritePack.*;


public class Velocity {
    //the x and y value for the vector.
    private double x;
    private double y;


    private final int BIGGER_THEN_FRAME = 10000;
    // constructor
    public Velocity(double dx, double dy) {
        this.x = dx;
        this.y = dy;
    }
    /**
     * get the x value.
     * @return this x.
     */
    public double getDx(){
        return this.x;
    }
    /**
     * set valueto x
     * @param dx
     */
    public void setX(double dx){
        this.x = dx;
    }

    /**
     * set value to y
     * @param dy
     */
    public void setY(double dy){
        this.y = dy;
    }
    /**
     * get the y vakue.
     * @return this y.
     */
    public double getDy(){
        return this.y;
    }

    /**
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy)
     * @param p point to update
     * @return the updated point.
     */
    public Point applyToPoint(Point p){
        p.setX(p.getX() + this.x);
        p.setY(p.getY() + this.y);
        return p;
    }

    /**
     * if the ball is tuch the edges of the frame we chang the vector.
     * @param b the ball we check.
     * @param frameSize the frame size.
     */
    public void changVec(Ball b, int frameSize){
        //each if check if the ball tuch one of the edges
        //if the ball tuch we change the vector.
        if(b.getX() >= frameSize - b.getSize()){
            b.setVelocity(-1*b.getVelocity().getDx(),b.getVelocity().getDy());
        }
        if( b.getX() <= b.getSize()){
            b.setVelocity(-1*b.getVelocity().getDx(),b.getVelocity().getDy());
        }
        if(b.getY() >= frameSize - b.getSize()) {
            b.setVelocity(b.getVelocity().getDx(), -1 * b.getVelocity().getDy());
        }
        if( b.getY() <= b.getSize()){
            b.setVelocity(b.getVelocity().getDx(), -1 * b.getVelocity().getDy());
        }
    }

    /**
     * get a point of start of the line and make from this vec line
     * @param p start o the line.
     * @return dhe created line.
     */
    public Line makeLineOfNexSteps(Point p, double r){
        Point start = new Point(p.getX(),p.getY());
        //make a line of ine step of the ball
        Point end = new Point(start.getX() + this.getDx()*1.4,start.getY()+ this.getDy()*1.4);
        Line l = new Line(start,end);
        if(end== null||p==null||start==null)
            System.out.println("error with making a infLine");
        return l;
    }

    public Line makeBiglineOfPreSteps(Point p, double r){
        Point start = new Point(p.getX(),p.getY());
        //make a line of ine step of the ball
        Point end = new Point(p.getX() - this.getDx()*10,p.getY()- this.getDy()*10);
        Line l = new Line(start,end);
        if(end== null||p==null||start==null)
            System.out.println("error with making a infLine");
        return l;
    }

    /**
     * get vector with angle and speed and change ib to x,y vector.
     * @param angle the anngle the the ball mobe(0 it up)
     * @param speed the speed of the ball.
     * @return the vector with x,y value.
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        //we need to change the angke becouse in the unit circle 0 angke is righr
        angle= angle - 90;
        //change degree to radian.
        double degrees = Math.toRadians(angle);
        //sin(angle) = y* cos(angle) = y.
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = Math.sin(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * get value of vector and return the value of the same vector with angke and speed.
     * @param x value
     * @param y value
     * @return the vaector euth angle and speed.
     */
    public Velocity getVecWithAngleAndSpeed(double x,double y){
        //speed is the distance between the start and the end of the vector.
        double speed = (new Point(0,0)).distance(new Point(x,y));
        //the start is(0,0) so we get y-0/x-0
        double inclineVec = y/x;
        double angle = Math.atan(inclineVec);
        angle = Math.toDegrees(angle);
        //up is zero
        angle += 90;
        return new Velocity(angle,speed);

    }

    /**
     * get an angle and rotate the vec acordingly
     * we use to caculte with  a rotation matrix informatin here
     * https://en.wikipedia.org/wiki/Rotation_matrix
     * @param angle the dgree to rotate.
     */
    public  void changByAngle(double angle){
        angle = Math.toRadians(angle);
        Velocity vec = new Velocity(0,0);
        vec = this.getVecWithAngleAndSpeed(this.x, this.y);
        double oldAngle = vec.getDx();
        double newX = (Math.cos(angle)* this.x)- (Math.sin(angle)*this.y);
        double newY = (Math.sin(angle)*this.x) +(Math.cos(angle)*this.y);
        vec.setX(newX);
        vec.setY(newY);
        vec = vec.getVecWithAngleAndSpeed(vec.getDx(),vec.getDy());
        this.x = newX;
        this.y  = newY;
    }
}
