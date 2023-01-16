/**
 * this class is p[art ofpackage game
 */
package Game;

//import all class that we use in this class
import SpritePack.Sprite;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * this class making the GUI and responde of the flow of the game level by level and put effects between them
 */
public class GameFlow {
    //KeyboardSensor for all Keyboard press in the game
    private KeyboardSensor keyboardSensor;
    //going to run for us the curtten level
    private AnimationRunner animationRunner;
    //gui fo all the graphic
    private GUI gui;
    //list of level that will be the levels by order to play
    private List<LevelInformation> levels;
    //h and w the size of the screen of the gui.
    private static int h = 600, w = 800;
    //score will be the xp that the player get in the game
    private int score;
    //flaf that true if the player win ane else false.
    private boolean isWin;
    //backeground is will be sprite that we draw ass a bacjkgeaounf for the gam.
    private Sprite background;
    //cs will be animaithin between tow difrent screen in the game
    private ChangeScreenEffect cs;

    /**
     * constractor
     * @param ar is the AnimationRunner for he curten level
     * @param ks is the KeyboardSensor of the game
     * @param gui is the graphic system of the game.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        levels = new LinkedList<>();
        this.score = 0;
        this.isWin = true;
        this.animationRunner = new AnimationRunner(this.gui);
        this.keyboardSensor = this.gui.getKeyboardSensor();

    }

    /**
     * getter that return kist of all the levels includ in the game
     * @return list of levels
     */
    public List<LevelInformation> getLevels(){
        return this.levels;
    }

    /**
     * method that intilize level one and return it
     */
    public void inintilaizeLevelOne(){
        this.levels.add(new LevelOne());
    }

    /**
     * method that intilize level tow and return it
     */
    public void inintilaizeLevelTwo(){
        this.levels.add(new LevelTwo());
    }

    /**
     * method that intilize level three and return it
     */
    public void inintilaizeLevelThree(){
        this.levels.add(new LevelThree());
    }

    /**
     * method that intilize level four and return it
     */
    public void inintilaizeLevelFour(){
        this.levels.add(new LevelFour());
    }

    /**
     * method that intilaize all levels and put them in a list
     */
    public void intilaizeLevels(){
        this.levels.add(new LevelOne());
        this.levels.add(new LevelTwo());
        this.levels.add(new LevelThree());
        this.levels.add(new LevelFour());
    }

    /**
     * method that with given list of levels start the game and change level if win or go to loser screen if lose
     * if win all the level make to the winner screen
     * @param levels list of all the level in the game
     */
    public void runLevels(List<LevelInformation> levels) {
        //making game level that have the first level in the list of levels
        GameLevel lastLevel= new GameLevel(levels.get(0), this.keyboardSensor, this.animationRunner,this.gui);
        for (LevelInformation levelInfo : levels) {
            //initilaize new level to the gameLevel
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,this.gui);
            lastLevel = level;
            //remember the score couter between tow levels
            level.getScoreCounter().increase(this.score);
            //initilaze level and backgraound
            level.initialize();
            this.background = level.getBackground();
            //while the player isn't losw or win
            while (level.getBlocksNum() > 0 && level.getBallNum() != 0) {
                //run the game
                level.run();
                //after running the game make effect of changing screen and run it
                this.cs = new ChangeScreenEffect(2,2,level.getBackground());
                this.animationRunner.run(this.cs);
            }
            //if the player loss
            if (level.getBallNum() == 0) {
                //update the score and the flag of isWin
                score += level.getScoreCounter().getValue();
                this.isWin = false;
                //go to the losser screen
                this.endScreen(lastLevel);
                break;
            }
            //else the player won
            else{
                //update the flag
                this.isWin = true;
            }
            //update the score
            score += level.getScoreCounter().getValue();
        }
        //go to end screen
        this.endScreen(lastLevel);
    }

    /**
     * method that draw the end screen
     * @param level the level that the player played
     */
    public void endScreen(GameLevel level){
        //making surface to draw on
        DrawSurface d = this.gui.getDrawSurface();
        //draw the backgrauond of the level again
        this.background.drawOn(d);
        //paint the end screen
        d.setColor(Color.BLUE.darker());
        d.fillCircle(400,300,250);
        d.setColor(Color.black);
        d.drawCircle(400,300,250);
        d.setColor(Color.blue.brighter().brighter().brighter());
        d.fillCircle(400,300,245);
        d.setColor(Color.black);
        //write the text acocrding to win/loss
        if(isWin){
            d.drawText(160,320,"You Win! Your score is "+ this.score, 35);
        }else{
            d.drawText(160,320,"Game Over. Your score is "+ this.score, 35);
        }
        gui.show(d);

        //when pess space exit from the progrm.
        KeyPressStoppableAnimation k = new KeyPressStoppableAnimation(this.keyboardSensor," ",level);
        k.doOneFrame(d);
        gui.close();
    }
}
