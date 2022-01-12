package ServerTest;

import Server.SharedData;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Check SharedData Class
 */
public class SharedDataTest {
    SharedData sharedData = new SharedData();

    @Test
    public void getNumberOfPlayersTest() {
        sharedData.setNumberOfPlayers(2);
        int check = sharedData.getNumberOfPlayers();
        Assert.assertEquals(2,check);
    }

    @Test
    public void getNamesTest() {
        sharedData.addName("New1");
        sharedData.addName("New2");
        ArrayList<String> list = new ArrayList<String>();
        list.add("New1");
        list.add("New2");
        Collection<String> check = sharedData.getNames();
        Assert.assertEquals(list,check);
    }

}
