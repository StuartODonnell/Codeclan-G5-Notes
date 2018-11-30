import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WaterBottleTest {

    Waterbottle waterbottle;

    @Before
    public void before(){
        waterbottle = new Waterbottle();
    }

    @Test
    public void volumeIs100() {
        assertEquals(100, waterbottle.getVolume());
    }

    @Test
    public void canDrink() {
        waterbottle.drink();
        assertEquals(90, waterbottle.getVolume());
    }

    @Test
    public void canEmpty() {
        waterbottle.empty();
        assertEquals(0, waterbottle.getVolume());
    }

    @Test
    public void canFill() {
        waterbottle.drink();
        waterbottle.fill();
        assertEquals(100, waterbottle.getVolume());
    }
}
