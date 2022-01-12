package ServerTest;

import Server.GameRules;
import org.junit.Assert;
import org.junit.Test;

/**
 * Check GameRules Class
 */
public class GameRulesTest {
    GameRules gameRules = new GameRules();

    @Test
    public void RulesCheckTest() {
        Assert.assertTrue((gameRules.loseIfUnableToMove));
        Assert.assertFalse((gameRules.turnSkippable));
        Assert.assertTrue((gameRules.gameStopsAtFirstWin));
    }
}
