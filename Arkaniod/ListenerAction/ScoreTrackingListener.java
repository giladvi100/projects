package ListenerAction;

import SpritePack.Ball;
import SpritePack.Block;

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * constractor
     * @param scoreCounter the counter of score that we want to tracking
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * method that when the event happend add to the score 5 points.
     * @param beingHit the block
     * @param hitter the ball that hit.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
       this.currentScore.increase(5);
    }
}