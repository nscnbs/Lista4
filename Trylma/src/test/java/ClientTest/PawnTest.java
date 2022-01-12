package ClientTest;

import Client.Pawn;
import org.junit.Assert;
import org.junit.Test;


/**
 * Check Pawn Class
 */
public class PawnTest {
    Pawn pawn = new Pawn(40);

    @Test
    public void getXTest() {
        pawn.setPosition(1,1);
        int x = pawn.getX();
        Assert.assertEquals(1,x);
    }

    @Test
    public void getYTest() {
        pawn.setPosition(1,1);
        int y = pawn.getX();
        Assert.assertEquals(1,y);
    }

    @Test
    public void getSize() {
        int check = pawn.getSize();
        Assert.assertEquals(40, check);
    }

    @Test
    public void getPlayerNick() {
        pawn.setPlayerNick("New");
        String check = pawn.getPlayerNick();
        Assert.assertEquals("New", check);
    }
}
