package Game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation{
    private boolean stop;
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;

    /**
     * constractor method
     * @param sensor the KeyboardSensor of the game
     * @param key the String of the key to pess
     * @param animation the animation to run
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        //stop tell ass to stop the animation when its true.
        this.stop = false;
        //for the case ot was paus all redy.
        this.isAlreadyPressed = true;
    }

    /**
     * method that draw one frame of the animation.
     * @param d the surface to print on
     */
    public void doOneFrame(DrawSurface d) {
        //while the stop flag isnt true
        while (!this.shouldStop()) {
            //if pres space we want to end this animation.
            if (this.key == " ") {
                //check that the edge
                if (!this.isAlreadyPressed) {
                    if ((this.sensor.isPressed(KeyboardSensor.SPACE_KEY))) {
                        this.stop = true;
                    }
                } else if (!((this.sensor.isPressed(KeyboardSensor.SPACE_KEY)) || (this.key == " "))) {
                    this.isAlreadyPressed = false;
                }
            }
            //if isAlreadyPresed is fkase so if we want to close we do it.
            if (!this.isAlreadyPressed) {
                if ((this.sensor.isPressed(key)))
                    this.stop = true;
            } else if (!((this.sensor.isPressed(key)))) {
                this.isAlreadyPressed = false;
            }
        }
    }

    /**
     * method that tell ass to stop
     * @return boolean true if we want to stop and false otherwise.
     */
    public boolean shouldStop(){
        return this.stop;
    }
}
