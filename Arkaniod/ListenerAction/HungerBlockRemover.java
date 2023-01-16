package ListenerAction;
import Game.*;
import SpritePack.*;
import SpritePack.Ball;

/**
 * this class is listener for the ball that when the event happend remove the hitten ball
 */
public class HungerBlockRemover  implements HitListener{
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constractor for the listener
     * @param game the game that the listener is serv
     * @param removedBlocks counter that need to update on every event
     */
    public HungerBlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * method that happend when the event happend and remove ball for the game.
     * @param beingHit the block that shuld be remove
     * @param hitter the ball that hit the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        hitter.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);
    }
}
