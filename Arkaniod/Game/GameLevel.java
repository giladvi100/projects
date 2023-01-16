package Game;

import AbstractGraphics.Line;
import AbstractGraphics.Point;
import ListenerAction.*;
import SpritePack.*;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.*;
import java.security.KeyException;
import java.util.Random;

public class GameLevel implements Animation{
    //all the sprite that in the game
    SpriteCollection sprites;
    //all the object that in the GameEnvironment
    private GameEnvironment environment;
    //countBlock- to count the remine blocks. countBall-to count the remine balls. score- count the score
    private Counter countBlock;
    private Counter countBall;
    private Counter score;
    private ScoreTrackingListener scorTrackLintener;
    private BlockRemover blockRemover;
    private BonusBlockRemover bonusBlockRemover;
    private Sprite scoreIn;
    private AnimationRunner runner;
    private boolean running;
    private biuoop.KeyboardSensor keyboard;
    private CountdownAnimation countDown;
    private LevelInformation level;
    private BallRemover ballRemover;
    private Paddle paddel;
    private BlockMakeSpeederPaddel bmsop;
    private BlockMakeLessSpeedPaddel bmls;
    private HungerBlockRemover hngerBlock;
    private LevelNameDetector levelName;
    private static int h = 600, w = 800;
    private GUI gui;
    public GameLevel(LevelInformation level, KeyboardSensor keyboardSensor, AnimationRunner animationRunner,GUI gui){
        this.gui = gui;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.score = new Counter(0);
        this.countBlock = new Counter(0);
        this.countBall = new Counter(level.numberOfBalls());
        this.scorTrackLintener = new ScoreTrackingListener(score);
        this.blockRemover = new BlockRemover(this,countBlock);
        this.scoreIn = new ScoreIndicator(this.score);
        this.sprites.addSprite(scoreIn);
        this.runner = new AnimationRunner(this.gui);
        this.keyboard = gui.getKeyboardSensor();
        this.countDown = new CountdownAnimation(2,3,this.sprites);
        this.level = level;
        this.ballRemover = new BallRemover(this, this.countBall);
        this.bmsop = new BlockMakeSpeederPaddel(this);
        this.bonusBlockRemover = new BonusBlockRemover(this,countBlock,countBall);
        this.bmls = new BlockMakeLessSpeedPaddel(this);
        this.hngerBlock = new HungerBlockRemover(this,countBall);
        this.levelName = new LevelNameDetector(this.level.levelName(),4);


    }
    public Counter getScoreCounter(){
        return this.score;
    }
    public int getBlocksNum(){
        return this.countBlock.getValue();
    }
    public int getBallNum(){
        return this.countBall.getValue();
    }
    public boolean shouldStop(){
        return !this.running;
    }

    //h the hight of the game. w thw withd of the game


    /**
     * add Collidable to the game
     * @param l the level that we thake the collidaible from to add.
     */
    public void initializeCollidable(LevelInformation l) {
        for(int i = 0;i<l.blocks().size();i++ ) {
            l.blocks().get(i).addHitListener(blockRemover);
            l.blocks().get(i).addHitListener(scorTrackLintener);
            this.sprites.addSprite(l.blocks().get(i));
            this.environment.addCollidable(l.blocks().get(i));
        }
    }


    /**
     * func that initialize al the game.
     */
    public void initialize(){
        //THE ORDER IS IMPORTANT

        this.sprites.addSprite(this.level.getBackground());

        this.addListenetToAll();
        this.initializeScreenAndWalls(600,800,this.gui);
        this.sprites.addSprite(this.levelName);
        this.initializeBall(this.level);
        this.initializePaddle(this.level);
        //this.initializeCollidable(this.levelOne);
        this.sprites.addSprite(scoreIn);
        // this.initializeBonusBlock(new Point(400,30),20,20,Color.WHITE);
    }

    /**
     * method that initialize bonus block that add to the game more balls.
     * @param bonus the block that add the balls.
     */
    public void initializeBonusBlock(Block bonus){
        this.environment.addCollidable(bonus);
        this.sprites.addSprite(bonus);
        bonus.addHitListener(this.bonusBlockRemover);
        //this.countBlock.increase(1);
        //bonus.addHitListener(this.blockRemover);
        bonus.addHitListener(this.scorTrackLintener);

    }


    /**
     * func that initialize the paddel of the game.
     */
    public void initializePaddle(LevelInformation l){
        paddel = new Paddle(gui,l.paddleSpeed(),l.paddleWidth());
        this.environment.addCollidable(paddel);
        Sprite s = paddel.castToSprite(paddel);
        this.sprites.addSprite(paddel);
    }

    /**
     * func that initialize the balls
     */
    public void initializeBall(LevelInformation l){

        for(int i = 0;i<l.initialBallVelocities().size();i++){
            Ball b = new Ball(400,560,5,Color.red.brighter().brighter());
            b.setVelocity(l.initialBallVelocities().get(i));
            b.setGameEnvironment(this.environment);
            this.sprites.addSprite(b);
        }
    }

    /**
     * func that initialize all the walls and the screen of the game.
     * @param h hight
     * @param w the width
     * @param gui the screen
     */
    private void initializeScreenAndWalls(int h,int w, GUI gui){
        int UP=  0, LEFT = 1, RIGHT = 2, BUTTEM = 3;
        Block[] arr = new Block[4];
        Point p = new Point(-25,0);
        arr[LEFT]= new Block(p,20,w+50);
        p = new Point(-0,-20);
        arr[UP] = new Block(p,w+50,10);
        p = new Point(w-10,-20);
        arr[RIGHT] = new Block(p,w+50,10);
        p = new Point(-10,h+20);
        arr[BUTTEM] = new Block(p,20,w+60);

        arr[BUTTEM].addHitListener(ballRemover);
        for(int i=0;i<arr.length;i++){
            this.environment.addCollidable(arr[i]);
            Sprite s = arr[i].castToSprite(arr[i]);
            this.sprites.addSprite(s);
        }
    }

    /**
     * method that add one more ball to the game.
     * @param x the x value that the ball will apeer in the game.
     * @param y the y value that the ball will apeer in the game.
     */
    public void addBall(double x, double y){
        Random rnd = new Random();
        //making new ball
        Ball b1 = new Ball(x,y,5,Color.red);
        double dx =rnd.nextDouble(4,7);
        double dy = Math.sqrt(60 - dx*dx);
        //make random direction for the ball movement
        b1.setVelocity(dx,-dy);
        //add the ball to the game.
        b1.setGameEnvironment(this.environment);
        this.sprites.addSprite(b1);
    }

    /**
     * method that print one frame on d
     * @param d the surface to print on.
     */
    public void doOneFrame(DrawSurface d) {
        //if win:
        if(this.countBlock.getValue() == 0){
            //update the score
            //and draw all the frame.
            this.score.increase(100);
            this.sprites.drawAllOn(d);
            this.running = false;
        }

        //if lose stop running
        if(countBall.getValue() == 0){
            this.running = false;
        }

        //draw all in d
        this.sprites.drawAllOn(d);
        //if any lister was activate do his event
        this.sprites.notifyAllTimePassed();
        //if press p pous the game.
        if (this.keyboard.isPressed("p")||this.keyboard.isPressed("P")){
            this.runner.run(new PauseScreen(this.keyboard,this.sprites));
        }
    }

    /**
     * method that increes the paddel of the game.
     * @param num the val to add to the paddel speed.
     */
    public void incrisPaddelSpeed(int num){
        this.paddel.incrisSpeed(num);
    }
    /**
     * func that run all the game
     */
    public void run() {

        this.running = true;
        //DrawSurface d = gui.getDrawSurface();

        this.runner.run(this.countDown);
        this.runner.run(this);
    }

    /**
     * method to remove givven sprite
     * @param s the givven sprite
     */
    public void removeSprite(Sprite s){
        this.sprites.removeSprite(s);
    }

    /**
     * method the remove from the game enviermnt givven colidable
     * @param c the givven collidable.
     */
    public void removeCollidable(Collidable c){
        this.environment.RemoveCollidable(c);
    }

    /**
     * method that run on all the blocks and add the listenr foe each one of them
     */
    public void addListenetToAll(){
        for(int i = 0; i< level.blocks().size(); i++){
            if(level.blocks().get(i).getKind() == "regular"){
                level.blocks().get(i).addHitListener(blockRemover);
                level.blocks().get(i).addHitListener(scorTrackLintener);
                this.sprites.addSprite(level.blocks().get(i));
                this.environment.addCollidable(level.blocks().get(i));
                this.countBlock.increase(1);
            }
            if(level.blocks().get(i).getKind() == "bonus"){
                this.countBlock.increase(1);
                this.initializeBonusBlock(level.blocks().get(i));
            }
            if(level.blocks().get(i).getKind() == "rmoveBall"){
                level.blocks().get(i).addHitListener(this.hngerBlock);
                this.environment.addCollidable(level.blocks().get(i));
                Sprite s = level.blocks().get(i).castToSprite(level.blocks().get(i));
                this.sprites.addSprite(s);
            }
            if(level.blocks().get(i).getKind() == "forMorSpeed"){
                level.blocks().get(i).addHitListener(bmsop);
                this.environment.addCollidable(level.blocks().get(i));
                Sprite s = level.blocks().get(i).castToSprite(level.blocks().get(i));
                this.sprites.addSprite(s);
            }
            if(level.blocks().get(i).getKind() == "forLessSpeed"){
                level.blocks().get(i).addHitListener(bmls);
                this.environment.addCollidable(level.blocks().get(i));
                Sprite s = level.blocks().get(i).castToSprite(level.blocks().get(i));
                this.sprites.addSprite(s);
            }

        }
    }

    /**
     * method that return the backgraund of the level.
     * @return
     */
    public Sprite getBackground(){
        return this.level.getBackground();
    }
}