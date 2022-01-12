package Server;


/**
 * Game rules
 */

public class GameRules {

    public enum BlockRules {
        Lose,
        Swap,
        CountTowardsWin
    }

    public BlockRules blockRule;

    public boolean loseIfUnableToMove;
    public boolean turnSkippable;
    public boolean gameStopsAtFirstWin;

    /**
     * Set default rules
     */
    public GameRules() {
        blockRule = BlockRules.Lose;
        loseIfUnableToMove = true;
        turnSkippable = false;
        gameStopsAtFirstWin = true;
    }
    public GameRules(BlockRules br, boolean loseIfUnableToMove, boolean turnSkippable, boolean gameStopsAtFirstWin) {
        this.blockRule = br;
        this.loseIfUnableToMove = loseIfUnableToMove;
        this.turnSkippable = turnSkippable;
        this.gameStopsAtFirstWin = gameStopsAtFirstWin;
    }
}
