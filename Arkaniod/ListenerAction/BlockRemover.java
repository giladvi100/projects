package ListenerAction;
import Game.*;
import SpritePack.*;

/**
 * this class is listener for the block that when the event happend remove the block
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constractor for the kistener
     * @param game the game that the listener is serv
     * @param removedBlocks counter that need to update on every event
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * method that happend when the event happend and remove the block from the game and end this listener for this block
     * @param beingHit the block that shuld be remove
     * @param hitter the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {

        //remove the listener from the hitten block and remove it from the game
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        //update the counter
        this.remainingBlocks.decrease(1);
    }
}