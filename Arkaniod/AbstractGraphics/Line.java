package AbstractGraphics;
import java.awt.Color;


/**
 * geometry.Line class.
 */
public class Line {

    private final Point origStart;
    private final Point origEnd;
    private Point start;
    private Point end;
    private double incline;
    private double yAxisIntersection;
    private final double epsilon = 0.0003;
    private Color color;

    /**
     * constructor that creates a line from two geometry.Point arguments.
     *
     * @param start is the point at which the line will start
     * @param end   is the point at which the line will end
     */
    public Line(Point start, Point end) {
        this.origStart = start;
        this.origEnd = end;

        //make left side of segment 'start', and if it is a vertical line - make higher point 'start'
        if (start.getX() < end.getX() || (start.getX() == end.getX() && start.getY() < end.getY())) {
            this.start = start;
            this.end = end;
        } else if (start.getX() > end.getX() || (start.getX() == end.getX() && start.getY() > end.getY())) {
            this.start = end;
            this.end = start;
        }
        //set slope and y axis intersection of line
        setEquationFormula();
        this.color = Color.BLACK; //default
    }

    /**
     * constructor that creates a line from 4 double.
     *
     * @param x1 is the x value of the point at which the line will start
     * @param y1 is the y value of the point at which the line will start
     * @param x2 is the x value of the point at which the line will end
     * @param y2 is the y value of the point at which the line will end
     */
    public Line(double x1, double y1, double x2, double y2) {

        this.origStart = new Point(x1, y1);
        this.origEnd = new Point(x2, y2);
        //make left side of segment 'start', and if it is a vertical line - make higher point 'start'
        if (x1 < x2 || (x1 == x2 && y1 > y2)) {
            this.start = new Point(x1, y1);
            this.end = new Point(x2, y2);
        } else if (x1 > x2 || (x1 == x2 && y1 < y2)) {
            this.start = new Point(x2, y2);
            this.end = new Point(x1, y1);
        }
        //set formula and y axis intersection of line
        setEquationFormula();
    }

    /**
     * method that defines slope and y axis intersection of line.
     */
    private void setEquationFormula() {

        //to find slope(m)==> (y1-y2)/(x1-x2)
        if(this.start.getX() != this.end.getX()) {
            this.incline = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
            //to find where line intersects y axis(b)==> y-(m*x)
            this.yAxisIntersection = this.start.getY() - (this.incline * this.start.getX());
        }else {
            //parrel
            this.yAxisIntersection = this.start.getX();
        }
    }

    /**
     * method the returns length of segment.
     *
     * @return the distance between start and end points, which is length of segment
     */
    public double length() {
        return (this.start).distance(this.end);
    }

    /**
     * method the returns middle point of line.
     * wth the formula of it(avege)
     *
     * @return the middle point of line
     */
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2;
        double y = (this.start.getY() + this.end.getY()) / 2;
        return new Point(x, y);
    }

    /**
     * getter that returns start point of line.
     *
     * @return start point of line
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return original start point of line.
     */
    private Point originalStart() {
        return this.origStart;
    }

    /**
     * @return original end point of line.
     */
    private Point originalEnd() {
        return this.origEnd;
    }

    /**
     * getter that returns end point of line.
     *
     * @return end point of line
     */
    public Point end() {
        return this.end;
    }

    /**
     * getter that returns slope of line.
     *
     * @return slope of line
     */
    public double getIncline() {
        return this.incline;
    }

    /**
     * getter that returns y value of intersection with y axis of line.
     *
     * @return y value of intersection with y axis of line
     */
    public double getYAxisIntersection() {
        return this.yAxisIntersection;
    }

    /**
     * method to find intersection of two segments, if exists, and return intersection geometry.Point.
     * -if start of one and end of other are same, that is the intersection.
     * -if lines are not parallel, and have same start or end point, that is the only intersection
     * -if one of the lines is vertical, it hax 1 x coordinate, so insert that x to (m*x+b) to find y coordinate,
     * and check if that y coordinate is in range of other segment
     * -if these cases are not true, then find x and y coordinates of intersection by equations
     *
     * @param other is another line
     * @return intersection point if exists, null if does not
     */
    public Point getIntersection(Line other) {

        if (this.start().isOn(other)) {
            return this.start();
        } else if (this.end().isOn(other)) {
            return this.end();
        } else if (other.start().isOn(this)) {
            return other.start();
        } else if (other.end().isOn(this)) {
            return other.end();
        }
        //if start and end points of two line are equal, that is their intersection
        if (this.start().equals(other.end())) {
            return this.start();
        } else if (this.end().equals(other.start())) {
            return this.end();

            //if lines intersect at both start points or end points but are not parallel, they have one intersection
        } else if (!this.parallel(other) && this.start().equals(other.start())) {
            return this.start();
        } else if (!this.parallel(other) && this.end().equals(other.end())) {
            return this.end();

            //if one line is vertical, insert x coordinate to other line's equation and see if y coordinate is
            //in range of segment
        } else if (this.vertical() && other.start().getX() <= this.start().getX()
                && other.end().getX() >= this.end().getX()) {
            if (other.getIncline() * this.start().getX() + other.getYAxisIntersection() > this.start().getY()
                    && other.getIncline() * this.start().getX() + other.getYAxisIntersection() < this.end().getY()) {
                return new Point(this.start().getX(),
                        other.getIncline() * this.start().getX() + other.getYAxisIntersection());
            }
        } else if (other.vertical() && this.start().getX() <= other.start().getX()
                && this.end().getX() >= other.end().getX()) {
            if (this.getIncline() * other.start().getX() + this.getYAxisIntersection() > other.start().getY()
                    && this.getIncline() * other.start().getX() + this.getYAxisIntersection() < other.end().getY()) {
                return new Point(other.start().getX(),
                        this.getIncline() * other.start().getX() + this.getYAxisIntersection());
            }
        }

        // to find x of intersection ==> (y2-y1)/(m1-m2)
        double xIntersection = (other.getYAxisIntersection() - this.getYAxisIntersection())
                / (this.getIncline() - other.getIncline());

        // to find y of intersection ==> m1 * x + b1
        double yIntersection = this.getIncline() * xIntersection + this.getYAxisIntersection();
        Point intersection = new Point(xIntersection, yIntersection);

        //check if distance from: start to intersection + intersection to end = length of line
        //if so, intersection is on both segments and they indeed intersect
        if (Math.abs(this.start().distance(intersection) + intersection.distance(end()) - this.length()) < epsilon
                && Math.abs(other.start().distance(intersection) + intersection.distance(other.end())
                - other.length()) < epsilon) {
            return intersection;
        }
        return null;
    }

    /**
     * method that checks if lines intersect. if they are equal or overlap it counts as intersecting.
     * using getIntersection method to find if intersection point exists
     *
     * @param other another line
     * @return true or false depending if lines intersect or not
     */
    public boolean isIntersecting(Line other) {

        //if both segments are exactly the same line or overlap
        if (this.equals(other) || this.overlap(other)) {
            return true;

            //if start of one and end of other are equal
        } else if (this.start().distance(other.end()) < epsilon || this.end().distance(other.start()) < epsilon
                || this.start().distance(other.start()) < epsilon || this.end().distance(other.end()) < epsilon) {
            return true;
        }
        return this.getIntersection(other) != null;
    }

    /**
     * method that returns intersection point if exists.
     *
     * @param other another line
     * @return intersection point if exist, null if not
     */
    public Point intersectionWith(Line other) {

        if (this.perellelY(other)) {
            return this.getIntarctionPrellelLine(other);
        }
        if (other.perellelY(other)) {
            return other.getIntarctionPrellelLine(other);
        }

        //if null was returned from getIntersection there is no intersection point.
        //if lines are equal there is no 1 intersection point to return
        if (!this.isIntersecting(other) || this.equals(other)) {
            return null;
        } else {
            return this.getIntersection(other);
        }
    }

    /**
     * func that return true uf the this line or the other line are perellely to y.
     * @param other the othe line
     * @return
     */
    public boolean perellelY(Line other) {
        if (this.start.getX() == this.end.getX() && other.start().getX() != other.end.getX()) {
            return true;
        }
        if (this.start.getX() != this.end.getX() && other.start().getX() == other.end.getX()) {
            return true;
        }
        return false;
    }

    /**
     * chek if line othe and line interction while we know our line is perelley to y.
     * @param other the othe line.
     * @return true if ther is intarction.
     */
    public boolean inttarctionToPerllelY(Line other) {
        if ((this.start.getX() <= other.start().getX() && this.end().getX() >= other.end().getX()) || (this.start.getX() >= other.start().getX() && this.end().getX() <= other.end().getX())) {
                return true;
        }
        return false;
    }

    /**
     * func check if this line is perelley to y.
     * @return
     */
    public boolean parellelY() {
        if (this.start.getX() == this.end.getX() && this.start().getY() != this.end().getY()) {
            return true;
        }
        return false;
    }

    /**
     * func that return the point of intarction tow line when one of them perelley to y.
     * @param other the othe line we want to know about.
     * @return the Point of intarcion.
     */
    public Point getIntarctionPrellelLine(Line other) {
        Point p = null;
        //we check if thger is any intarction between the line
        //if ther is, we find the point with the y value thatt we know.
        if (this.perellelY(other) && this.inttarctionToPerllelY(other) && !this.overlap(other)) {
            if (this.parellelY()) {
                p = other.findPointWithXval(this.start().getX());
                if (p != null) {
                    if (this.start.getY() <= p.getY() && p.getY() <= this.end.getY()) {
                        return p;
                    }
                    if (this.start.getY() >= p.getY() && p.getY() >= this.end.getY()) {
                        return p;
                    }
                }
            }
        }
        //we do the same in case the othe line is perelley line.
        if (other.parellelY() && other.inttarctionToPerllelY(other) && !other.overlap(other)) {
            p = this.findPointWithXval((other.start().getX()));
            if (p != null) {
                if (other.start().getY() <= p.getY() && p.getY() <= other.end().getY()) {
                    return p;
                }
                if (other.start().getY() >= p.getY() && p.getY() >= other.end().getY()) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * find the value of y on line with y value.
     * @param x the y value
     * @return the point.
     */
    public Point findPointWithXval(double x) {
        double y = (this.incline * x) + this.yAxisIntersection;
        Point p = new Point(x, y);
        return p;
    }

    /**
     * method that checks if two lines are the same line.
     *
     * @param other another line
     * @return true or false depending if lines are equal or not
     */
    public boolean equals(Line other) {
        if (this.start().equals(other.start())) {
            return this.end().equals(other.end());
        }
        return false;
    }

    /**
     * checks if lines overlap.
     *
     * @param other other line
     * @return true/false if overlap or not
     */
    private boolean overlap(Line other) {

        //vertical lines overlap if x coordinate of start and end of segment are equal
        if (Math.abs(this.start().getX() - this.end().getX()) < epsilon
                && Math.abs(other.start().getX() - other.end().getX()) < epsilon
                && Math.abs(this.start().getX() - other.start().getX()) < epsilon) {
            return (other.start().getY() < this.start().getY() && other.start().getY() > this.end().getY())
                    || (other.end().getY() < this.start().getY() && other.end().getY() > this.end().getY());

            //if intersection of lines are equal, check if start or end of one segment lies between
            //other lines start and end
        } else if (Math.abs(this.getIncline() - other.getIncline()) < epsilon
                && Math.abs(this.getYAxisIntersection() - other.getYAxisIntersection()) < epsilon) {
            return (other.start().getX() > this.start().getX() && other.start().getX() < this.end().getX())
                    || (other.end().getX() > this.start().getX() && other.end().getX() < this.end().getX());
        }
        return false;
    }

    /**
     * func that checks if lines are parallel by checking if slopes are equal.
     *
     * @param other another line
     * @return true/false if lines are parallel or not
     */
    private boolean parallel(Line other) {
        if (this.equals(other) || this.overlap(other)) {
            return true;
        }
        return Math.abs(this.getIncline() - other.getIncline()) < epsilon;
    }

    /**
     * func to check if a line is vertical by checking if x coordinate of start and end are equal.
     *
     * @return true/false if vertical or not
     */
    public boolean vertical() {
        return Math.abs(this.start().getX() - this.end().getX()) < epsilon;
    }

    /*
    public Point closestIntersectionToStartOfLine(Rectangle rec) {
        Line[] arr = rec.getLineOfRectangle();
        Point p = null;
        double min = this.start.distance(this.end);
        for (int i = 0; i < arr.length; i++) {
            Point temp = arr[i].intersectionWith(this);
            if (temp != null) {
                if (this.start.distance(temp) < min) {
                    p = temp;
                    min = this.start.distance(temp);
                }
            }
        }
        return p;
    }
     */
}