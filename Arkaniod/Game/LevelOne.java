package Game;


import AbstractGraphics.Point;
import AbstractGraphics.Rectangle;
import AbstractGraphics.Velocity;
import Backgrounds.BackgraoundOne;
import ListenerAction.*;
import SpritePack.Block;
import SpritePack.Collidable;
import SpritePack.Sprite;
import biuoop.GUI;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LevelOne implements LevelInformation{

    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private List<Velocity> vecs;
    private ScoreTrackingListener scorTrackLintener;
    private Counter score;
    private Counter countBlock;

    public LevelOne(){

        this.blocks = new LinkedList<>();
        this.vecs = new ArrayList<>();
        Random rnd = new Random();

        //add vec to the list of vect for the balls.
        Velocity v = new Velocity(0.1,8);
        this.vecs.add(v);
        this.initializeRowOfBlock(new Block(new Point(10,60),30,78),10,Color.PINK,"regular");

        //add to the blocks the blocks of the game.
        this.initializeRowOfBlock(new Block(new Point(10,200),30,78),10,Color.gray.darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,200),30,78),10,Color.gray.darker(),"regular");
        this.blocks.add(new Block(new Point(375,100),20,50,Color.YELLOW,"bonus"));
        this.blocks.add(new Block(new Point(100,400),20,50,Color.YELLOW,"bonus"));
        this.blocks.add(new Block(new Point(700,400),20,50,Color.YELLOW,"bonus"));
        this.blocks.add(new Block(new Point(375,35),20,50,Color.YELLOW,"rmoveBall"));

        //initilaize the paddel info
        this.paddleSpeed = 8;
        this.paddleWidth = 300;

        //the level name and starting the score
        this.levelName = "level one";
        this.score = new Counter(0);
        this.scorTrackLintener = new ScoreTrackingListener(this.score);
    }

    /**
     * method that get block and make row of block like it
     * @param b the bloc that we want to do in a row
     * @param rowSize the nubmer of block thar will be in the row
     */
    private void initializeRowOfBlock(Block b, int rowSize, Color color, String kind){


        //the point od the first block
        Point p = new Point(b.getCollisionRectangle().getUpperLeftPoint().getX(),b.getCollisionRectangle().getUpperLeftPoint().getY());
        //the loop make i blocks in a row srtwrt from the point p
        for (int i = 0;i<rowSize;i++){
            Block nexBlock = new Block(p,b.getCollisionRectangle().getHeight(),b.getCollisionRectangle().getWidth(),color,"regular");
            this.blocks.add(nexBlock);
            p = new Point(p.getX() + b.getCollisionRectangle().getWidth(), p.getY());
        }
    }

    /**
     * method that return the number of balls in the gamee
     * @return int of the balls
     */
    public int numberOfBalls(){
        return this.vecs.size();
    }
    public List<Velocity> initialBallVelocities(){
        return this.vecs;
    }

    /**
     * method that return the speed of the paddel.
     * @return ont of the speed of the paddel.
     */
    public  int paddleSpeed(){
        return this.paddleSpeed;
    }

    /**
     * method that return the width of the paddle
     * @return int of the width od the paddle.
     */
    public int paddleWidth(){
        return this.paddleWidth;
    }

    /**
     * method thar return the name of the level
     * @return string of the level name
     */
    public String levelName(){
        return this.levelName;
    }

    /**
     * method that return the backgraund
     * @return sprite of the backgraund
     */
    public Sprite getBackground(){
        Sprite backgraound = new BackgraoundOne();
        return backgraound;
    }

    /**
     * method that retuirn all the blocks in the game
     * @return list of all the blocks.
     */
    public List<Block> blocks(){
        return this.blocks;
    }

    /**
     * method that return the number pf blocks in the game.
     * @return the list of blocks size.
     */
    public int numberOfBlocksToRemove(){
        return this.blocks.size();
    }
}
