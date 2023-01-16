package ListenerAction;
import Game.*;
import SpritePack.*;

/**
 * this class is listener for the ball that when the event happend add one more the ball
 */
public class BonusBlockRemover implements HitListener{
    private GameLevel game;
    private Counter remineBlocks;
    private Counter remineBall;

    /**
     * constractor for the kistener
     * @param game the game that the listener is serv
     * @param remineBlocks counter that need to update on every event
     */
    public BonusBlockRemover(GameLevel game, Counter remineBlocks, Counter remineBall) {
        this.game = game;
        this.remineBlocks = remineBlocks;
        this.remineBall = remineBall;
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
        double xForNewBall = beingHit.getCollisionRectangle().getUpperLeftPoint().getX() + (beingHit.getCollisionRectangle().getWidth()/2);
        double yForNewBall = beingHit.getCollisionRectangle().getUpperLeftPoint().getY() + (beingHit.getCollisionRectangle().getHeight()/2);
        this.game.addBall(xForNewBall,yForNewBall);
        this.game.addBall(xForNewBall,yForNewBall);
        //update the copunter
        this.remineBall.increase(2);
        this.remineBlocks.decrease(1);

    }
}
