import Game.GameFlow;
import Game.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.util.concurrent.ExecutionException;


public class Ass6Game {
    public static void main(String[] args) {
        GUI gui = new GUI("", 800, 600);
        KeyboardSensor ks = gui.getKeyboardSensor();
        AnimationRunner ar = new AnimationRunner(gui);
        GameFlow gf = new GameFlow(ar, ks, gui);
        try {
            if (args.length == 0) {
                addFourFirstLevels(gf);
            }
        }catch (IndexOutOfBoundsException e) {
            addFourFirstLevels(gf);
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("1")) {
                gf.inintilaizeLevelOne();
            }
            if (args[i].equals("2")) {
                gf.inintilaizeLevelTwo();
            }
            if (args[i].equals("3")) {
                gf.inintilaizeLevelThree();
            }
            if (args[i].equals("4")) {
                gf.inintilaizeLevelFour();
            }
        }
        if(gf.getLevels().size() == 0){
            addFourFirstLevels(gf);
        }
        gf.runLevels(gf.getLevels());
    }
    static public void addFourFirstLevels(GameFlow gf){
        gf.inintilaizeLevelOne();
        gf.inintilaizeLevelTwo();
        gf.inintilaizeLevelThree();
        gf.inintilaizeLevelFour();
    }
}
