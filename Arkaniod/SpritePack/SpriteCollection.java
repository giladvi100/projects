package SpritePack;
import biuoop.*;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SpriteCollection {
    //list of the collidablesSpirit
    private List<Sprite> collidablesSpirit = new LinkedList<>();

    /**
     * func that add sprite
     * @param s the spriite to add.
     */
    public void addSprite(Sprite s){
        this.collidablesSpirit.add(s);
    }

    public void removeSprite(Sprite s){
        this.collidablesSpirit.remove(s);
    }

    /**
     * func that call all the object that muy be chnging.
     */
    public void notifyAllTimePassed(){
        for(int i=0;i<this.collidablesSpirit.size();i++){

            this.collidablesSpirit.get(i).timePassed();
        }
    }


    /**
     * funf that drae akk the object on given surface.
     * @param d the given surface,
     */
    public void drawAllOn(DrawSurface d){
        for(int i=0;i<this.collidablesSpirit.size();i++){
            d.setColor(this.collidablesSpirit.get(i).getColor());
            this.collidablesSpirit.get(i).drawOn(d);

        }
    }
}