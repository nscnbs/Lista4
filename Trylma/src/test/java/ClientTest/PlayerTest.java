package ClientTest;

import Client.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * Check Player Class
 */
public class PlayerTest {
    Player player = new Player();


    @Test
    public void getNickTest() {
        player.setNick("New");
        String check = player.getNick();
        Assert.assertEquals("New",check);
    }

    @Test
    public void getPlayerPawnsPositionTest() {
        player.setPlayerPawnsPositions(new int [][] {{1},{0,1}});
        Assert.assertEquals(new int [][] {{1},{0,1}}, player.getPlayerPawnsPositions());
    }
}
