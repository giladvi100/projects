package Game;

import AbstractGraphics.Point;
import AbstractGraphics.Velocity;
import Backgrounds.BackgraoundThree;
import ListenerAction.Counter;
import ListenerAction.ScoreTrackingListener;
import SpritePack.Block;
import SpritePack.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LevelThree implements LevelInformation{

    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private List<Velocity> vecs;
    private ScoreTrackingListener scorTrackLintener;
    private Counter score;
    private Counter countBlock;

    public LevelThree(){
        //intitilaize the background od the screen
        this.initilaizeBackground();
        //make list of all the bloicks in the game
        this.blocks = new LinkedList<>();
        this.vecs = new ArrayList<>();
        //intilaize all the colidable in tne level and all the vec for the game
        this.initielaizeCol();
        this.intielaizeVecForBalls();

        //the spped of the paddel in this level and his scale
        this.paddleSpeed = 15;
        this.paddleWidth = 210;

        //the name level
        this.levelName = "level Three";

        //make the scoer and the listener for the score.
        this.score = new Counter(0);
        this.scorTrackLintener = new ScoreTrackingListener(this.score);
    }

    /**
     * method that initilaize the background
     */
    public void initilaizeBackground(){
        Sprite bg= new BackgraoundThree();
        this.background = bg;
    }

    /**
     * method that intielaize the vec for the balls
     */
    private void intielaizeVecForBalls(){
        Random rnd = new Random();
        for(int i =0;i<3;i++) {
            double dx = rnd.nextDouble(4, 5);
            double dy = Math.sqrt(60 - dx * dx);
            Velocity v = new Velocity(-dx, -dy);
            this.vecs.add(v);
        }
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
     * method that intielaize all the collidaible in the level and add to the list block
     */
    public void initielaizeCol(){
        this.initializeRowOfBlock(new Block(new Point(10,30),30,60),5,Color.YELLOW.darker().darker(),"regular");
        this.blocks.add(new Block(new Point(310,30),30,60,Color.YELLOW.darker(),"bonus"));
        this.blocks.add(new Block(new Point(370,30),30,60,Color.YELLOW.darker(),"rmoveBall"));
        this.blocks.add(new Block(new Point(430,30),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(490,30),30,60),5,Color.YELLOW.darker().darker(),"regular");

        this.initializeRowOfBlock(new Block(new Point(10,60),20,39),20,Color.cyan.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,80),30,60),13,Color.pink.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,110),20,39),20,Color.cyan.darker().darker(),"regular");

        this.blocks.add(new Block(new Point(230,130),30,80,Color.PINK,"forMorSpeed"));
        this.blocks.add(new Block(new Point(310,130),30,80,Color.PINK,"forLessSpeed"));
        this.blocks.add(new Block(new Point(390,130),30,80,Color.PINK,"forMorSpeed"));
        this.blocks.add(new Block(new Point(470,130),30,80,Color.PINK,"forLessSpeed"));

        this.initializeRowOfBlock(new Block(new Point(10,200),20,39),20,Color.cyan.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,220),30,60),13,Color.pink.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,250),20,39),20,Color.cyan.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,270),30,60),5,Color.YELLOW.darker().darker(),"regular");
        this.blocks.add(new Block(new Point(310,270),30,60,Color.YELLOW.darker(),"bonus"));
        this.blocks.add(new Block(new Point(370,270),30,60,Color.YELLOW.darker(),"rmoveBall"));
        this.blocks.add(new Block(new Point(430,270),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(490,270),30,60),5,Color.YELLOW.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,330),20,39),20,Color.cyan.darker().darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,350),30,60),5,Color.YELLOW.darker().darker(),"regular");
        this.blocks.add(new Block(new Point(310,350),30,60,Color.YELLOW.darker(),"bonus"));
        this.blocks.add(new Block(new Point(370,350),30,60,Color.YELLOW.darker(),"rmoveBall"));
        this.blocks.add(new Block(new Point(430,350),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(490,350),30,60),5,Color.YELLOW.darker().darker(),"regular");
        //this.initializeRowOfBlock(new Block(new Point(10,300),30,60),13,Color.cyan.darker().darker(),"regular");


    }

    /**
     * method that return the number of balls in the begining of the level
     * @return int of the nubme of balls
     */
    public int numberOfBalls(){
        return this.vecs.size();
    }

    /**
     * methos that return the list of all the vecs for the balls.
     * @return
     */
    public List<Velocity> initialBallVelocities(){
        return this.vecs;
    }

    /**
     * method that return the curtnent speed of the paddel
     * @return int of the speed of the paddel
     */
    public  int paddleSpeed(){
        return this.paddleSpeed;
    }

    /**
     * method that return the curtnent width of the paddel
     * @return int of the width of the paddel
     */
    public int paddleWidth(){
        return this.paddleWidth;
    }

    /**
     * method that return name of the level
     * @return string of the name level
     */
    public String levelName(){
        return this.levelName;
    }

    /**
     * method that return the background f this level
     * @return sprite of the backgrounf of the level
     */
    public Sprite getBackground(){
        return this.background;
    }

    /**
     * methos that return the list of all the blocks in this level
     * @return the list of all the blocks in this level
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

