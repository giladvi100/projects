package ListenerAction;
import Game.*;
import SpritePack.*;

/**
 * this class is listener for the ball that when the event happend remove the ball
 */
public class BallRemover implements HitListener{
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constractor for the kistener
     * @param game the game that the listener is serv
     * @param removedBlocks counter that need to update on every event
     */
    public BallRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * method that happend when the event happend and remove the ball from the game and end this listener for this ball
     * @param beingHit the block that shuld be remove
     * @param hitter the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //remove the ball from the game
        hitter.removeFromGame(this.game);
        //update the count
        this.remainingBlocks.decrease(1);
    }
}
