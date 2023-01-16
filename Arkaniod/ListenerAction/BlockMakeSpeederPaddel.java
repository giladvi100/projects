package ListenerAction;

import Game.GameLevel;
import SpritePack.Ball;
import SpritePack.Block;


public class BlockMakeSpeederPaddel implements HitListener{

    private GameLevel game;

    /**
     * constractor for the kistener
     * @param game the game that the listener is serv
     */
    public BlockMakeSpeederPaddel(GameLevel game) {
        this.game = game;
    }

    /**
     * method that happend when the event happend and add ball for the game.
     * @param beingHit the block that shuld be remove
     * @param hitter the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //remove the block from all the platform
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        //making new ball and the licition of the new ball will start in the center of the block
        this.game.incrisPaddelSpeed(5);

    }
}
