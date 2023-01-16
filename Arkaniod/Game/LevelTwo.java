package Game;


import AbstractGraphics.Point;
import AbstractGraphics.Rectangle;
import AbstractGraphics.Velocity;
import Backgrounds.BackgraounTwo;
import ListenerAction.*;
import SpritePack.Block;
import SpritePack.Collidable;
import SpritePack.Sprite;
import biuoop.GUI;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LevelTwo implements LevelInformation{

    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private List<Velocity> vecs;
    private ScoreTrackingListener scorTrackLintener;
    private Counter score;
    private Counter countBlock;

    public LevelTwo(){
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
        this.paddleWidth = 220;

        //the name level
        this.levelName = "level two";

        //make the scoer and the listener for the score.
        this.score = new Counter(0);
        this.scorTrackLintener = new ScoreTrackingListener(this.score);
    }

    /**
     * method that initilaize the background
     */
    public void initilaizeBackground(){
        Sprite b2= new BackgraounTwo();
        this.background =b2;
    }

    /**
     * method that intielaize the vec for the balls
     */
    private void intielaizeVecForBalls(){
        Random rnd = new Random();
        for(int i =0;i<1;i++) {
            double dx = rnd.nextDouble(5, 6);
            double dy = Math.sqrt(50.5 - dx * dx);
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
        this.blocks.add(new Block(new Point(430,28),30,60,Color.YELLOW.darker(),"bonus"));
        this.blocks.add(new Block(new Point(370,28),30,60,Color.YELLOW.darker(),"rmoveBall"));
        this.blocks.add(new Block(new Point(310,28),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(10,60),30,60),13,Color.cyan.darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,90),30,60),13,Color.ORANGE,"regular");
        this.initializeRowOfBlock(new Block(new Point(10,120),30,60),6,Color.pink.darker(),"regular");
        this.blocks.add(new Block(new Point(370,120),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(430,120),30,60),6,Color.pink.darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,150),30,60),6,Color.pink.darker(),"regular");
        this.blocks.add(new Block(new Point(370,150),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(430,150),30,60),6,Color.pink.darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,180),30,60),6,Color.pink.darker(),"regular");
        this.blocks.add(new Block(new Point(370,180),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(430,180),30,60),6,Color.pink.darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,210),30,60),6,Color.pink.darker(),"regular");
        this.blocks.add(new Block(new Point(370,210),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(430,210),30,60),6,Color.pink.darker(),"regular");
        this.initializeRowOfBlock(new Block(new Point(10,240),30,60),5,Color.pink.darker(),"regular");
        this.blocks.add(new Block(new Point(430,240),30,60,Color.YELLOW.darker(),"bonus"));
        this.blocks.add(new Block(new Point(370,240),30,60,Color.YELLOW.darker(),"bonus"));
        this.blocks.add(new Block(new Point(310,240),30,60,Color.YELLOW.darker(),"bonus"));
        this.initializeRowOfBlock(new Block(new Point(490,240),30,60),5,Color.pink.darker(),"regular");
        this.blocks.add(new Block(new Point(360,270),30,80,Color.PINK,"forMorSpeed"));

        this.initializeRowOfBlock(new Block(new Point(10,300),30,60),13,Color.gray,"regular");
        this.initializeRowOfBlock(new Block(new Point(10,300),30,60),13,Color.gray,"regular");
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
