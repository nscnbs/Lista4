package ClientTest;

import Client.Field;
import org.junit.Assert;
import org.junit.Test;

/**
 * Check Field class
 */
public class FieldTest {
    Field field = new Field(1,2, 40, "1");


    @Test
    public void getIdTest() {
        String check = field.getId();
        Assert.assertEquals("1",check);
    }

    @Test
    public void getSizeTest() {
        int check = field.getSize();
        Assert.assertEquals(40,check);
    }

    @Test
    public void getxPositionTest() {
        int check = field.getxPosition();
        Assert.assertEquals(1,check);
    }

    @Test
    public void getyPositionTest() {
        int check = field.getyPosition();
        Assert.assertEquals(2,check);
    }
}