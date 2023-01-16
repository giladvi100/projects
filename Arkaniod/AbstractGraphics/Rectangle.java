package AbstractGraphics;
import java.util.LinkedList;
import java.util.List;


public class Rectangle {
    //upperLeft- the point of the upper Left rectangle
    private Point upperLeft;
    //width and height are the size of the rectangle.
    private double width;
    double height;

    /**
     * constarctor that bilde from the given paramter rectangle.
     * @param upperLeft
     * @param height
     * @param width
     */
    public Rectangle(Point upperLeft, double height, double  width){
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    public Point getUpperLeftPoint(){
        return this.upperLeft;
    }

    /**
     * cgetter method that return the width
     * @return width of rec
     */
    public double getWidth(){
        return this.width;
    }

    /**
     * cgetter method that return the height
     * @return height of rec
     */
    public double getHeight(){
        return this.height;
    }

    //const of indexes for the line around the rec
    static final int LEFT=  0, UP = 1, RIGHT = 2, BUTTEM = 3;

    /**
     * method the return array of the line around this rec
     * @return array of line
     */
    public Line[] getLineOfRectangle(){
        //make array and of lines from the point of the right left and width and hight make all the line
        Line[] arrLine = new Line[4];
        Line l1 = new Line(this.upperLeft.getX(),this.upperLeft.getY(),this.upperLeft.getX(),this.upperLeft.getY() + this.height);
        arrLine[0] = l1;
        l1 = new Line(this.upperLeft.getX(),this.upperLeft.getY(),this.upperLeft.getX() + this.width,this.upperLeft.getY());
        arrLine[1] =l1;
        l1 = new Line(this.upperLeft.getX() + this.width,this.upperLeft.getY(),this.upperLeft.getX() + this.width,this.upperLeft.getY() + this.height);
        arrLine[2] = l1;
        l1 = new Line(this.upperLeft.getX(),this.upperLeft.getY() + this.height,this.upperLeft.getX() + this.width,this.upperLeft.getY() + this.height);
        arrLine[3] = l1;
        return arrLine;
    }

    /**
     * method that get line and return list of all the interaction point of this rec and the line
     * @param line
     * @return list of point
     */
    public java.util.List<Point> intersectionPoints(Line line){
        List<Point> list = new LinkedList<>();
        Line[] arr =new Line[4];
        Point p = new Point(0,0);
        Rectangle thisRec = new Rectangle(this.upperLeft,this.width,this.height);
        arr = thisRec.getLineOfRectangle();
        for(int i =0;i<arr.length;i++){
            p = arr[i].intersectionWith(line);
            if(p!=null){
                list.add(p);
            }
        }
        return list;
    }
}
