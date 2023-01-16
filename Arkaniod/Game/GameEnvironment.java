package Game;

import AbstractGraphics.*;
import AbstractGraphics.Line;
import SpritePack.Collidable;
import SpritePack.CollisionInfo;
import java.util.LinkedList;
import java.util.List;

public class GameEnvironment {
    //all the collidables in the GameEnvironment
    private List<Collidable> collidables = new LinkedList<>();

    public List<Collidable> getCollidables(){
        return this.collidables;
    }
    /**
     * method that add collidable
     * @param c the collidable
     */
    public void addCollidable(Collidable c){
        this.collidables.add(c);
    }

    /**
     * method that remove a givven colidable
     * @param c the givven colidable
     */
    public void RemoveCollidable(Collidable c){
        this.collidables.remove(c);
    }

    /**
     * func that give the info of the clossest Collision
     * @param trajectory the vec line of the ball.
     * @return CollisionInfo
     */
    public CollisionInfo getClosestCollision(Line trajectory){
        Collidable c = this.getClosestObsicle(trajectory, trajectory.start());
        CollisionInfo ci = new CollisionInfo();
        ci.setCollidable(c);
        ci.setCollisionPoint(this.getClosestInterctionPoint(trajectory, trajectory.start()));
        return ci;
    }

    /**
     * func thet return the closest interaction point.
     * @param trajectory the line of the vector of the ball.
     * @param p1 the point of the center oof the ball
     * @return  the point of the closest interaction
     */
    public Point getClosestInterctionPoint(Line trajectory, Point p1){
        Point interctionPoiont = null;
        Collidable clossestOb = this.getClosestObsicle(trajectory, p1);
        Line[] arr = new Line[4];
        //if clossestOb so there is no interaction.
        if(clossestOb == null){
            return null;
        }
        arr = clossestOb.getCollisionRectangle().getLineOfRectangle();
        double min = trajectory.length();
        //find the closest clossestOb by using distance between p1 to the interaction point.
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
        return interctionPoiont;
    }

    /**
     * get the closest clossest that the ball ganna hit
     * @param trajectory the line of the vec of the ball
     * @param p1 the center of the ball.
     * @return the closest clossest that the ball ganna hit
     */
    public Collidable getClosestObsicle(Line trajectory, Point p1) {
        //get list of akk obsticle that the line ganna hit
        int index = 0;
        List <Collidable> obsicles = getIntarctonObsocile(trajectory);
        if(obsicles == null){
            return null;
        }
        //with distance between p1 to the interacion point we finf the closest obsicle.
        Collidable closestC = obsicles.get(0);
        Line[] arr = new Line[4];
        arr = closestC.getCollisionRectangle().getLineOfRectangle();

        double min = trajectory.length();
        for(int i =0;i<obsicles.size();i++){
            arr = obsicles.get(i).getCollisionRectangle().getLineOfRectangle();
            for(int j = 0;j< arr.length;j++){
                Point p = trajectory.intersectionWith(arr[j]);
                if (p!= null) {
                    double temp = p.distance(p1);
                    if (temp < min && temp != 0) {
                        min = temp;
                        index = i;
                        closestC = obsicles.get(i);
                    }
                }
            }
        }
        return obsicles.get(index);
    }

    /**
     * func the return list of all the obsicols that the line of the vec of the ball ganna hit.
     * @param trajectory the line of the vec of the ball.
     * @return list of all the obsicols that the line of the vec of the ball ganna hit.
     */
    public List <Collidable> getIntarctonObsocile(Line trajectory) {
        List<Collidable> obsicle = new LinkedList<>();
        Line[] arr = new Line[4];
        //run on all the obsticle in the envirment and id there is a hit with th vec line of the ball, we add itr to anothe list.
        for(int i =0;i<this.collidables.size();i++){
            arr = this.collidables.get(i).getCollisionRectangle().getLineOfRectangle();
            if(arr[0].intersectionWith(trajectory)!=null||arr[1].intersectionWith(trajectory)!=null||arr[2].intersectionWith(trajectory)!=null||arr[3].intersectionWith(trajectory)!=null){
                obsicle.add(this.collidables.get(i));
            }
        }
        //return the list.
        if(obsicle.size() == 0) {
            return null;
        }
        return obsicle;
    }



}