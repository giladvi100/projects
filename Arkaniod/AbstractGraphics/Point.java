package AbstractGraphics;

/**
 * class of point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * put value in the x of the point.
     * @param valX the value to the x
     */
    public void setX(double valX) {
        this.x = valX;
    }

    /**
     * put value in the y of the point.
     * @param valY the value to the y
     */
    public void setY(double valY) {
        this.y = valY;
    }
    /**
     * put value in the point.
     * @param  x, y the value to the point
     */
    public Point(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * get point and musser the distance.
     * @param other the other point messure to.
     * @return the distance
     */
    public double distance(Point other) {
        double dis = Math.sqrt((this.x - other.getX()) * (this.x - other.getX()) + (this.y - other.getY()) * (this.y - other.getY()));
        return dis;
    }


    /**
     * true- this point == other point true until epsilon.
     * @param other the point to check
     * @return true- this point == other point
     */
    static private final double epsilon = 0.0001;
    public boolean equals(Point other) {
        if ((epsilon < (other.getY() - this.y)&&((other.getY() - this.y) < -epsilon)||(epsilon > (other.getY() - this.y))&&((other.getY() - this.y) > -epsilon))) {
            return true;
        }
        if ((epsilon < (other.getX() - this.x)&&(other.getX() - this.x) < -epsilon)||(epsilon > (other.getX() - this.x)&&(other.getX() - this.x) > -epsilon)) {
            return true;
        }
        return false;
    }

    /**
     * return x value
     * @return return x value
     */
    public double getX() {
        return this.x;
    }
    /**
     * return y value
     * @return return y value
     */
    public double getY() {
        return this.y;
    }

    /**
     * func that indicate if this point is on the other line
     * @param other the line we check if the point is on it.
     * @return true if it is ot the line false otherwise.
     */
    public boolean isOn(Line other){
        if(this.equals(other.start())||this.equals(other.end())){
            return true;
        }
        return false;
    }

    /*
    static final int LEFT=  0, UP = 1, RIGHT = 2, BUTTEM = 3;
    public boolean needToChangeVec(Rectangle rec){
        Line[] arr = new Line[4];
        arr = rec.getLineOfRectangle();
        if (this.y < arr[UP].start().y&&this.x>arr[RIGHT].start().x){
            return true;
        }
        return false;
    }
     */
}
