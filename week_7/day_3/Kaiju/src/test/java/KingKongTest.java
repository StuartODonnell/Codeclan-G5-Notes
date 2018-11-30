import Kaiju.KingKong;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KingKongTest {

    KingKong kingKong;

    @Before
    public void before(){
        kingKong = new KingKong("Kong", 20, "black");
    }

    @Test
    public void hasName() {
        assertEquals("Kong", kingKong.getName());
    }

    @Test
    public void hasHealthValue() {
        assertEquals(100, kingKong.getHealthValue());
    }

    @Test
    public void hasAttackValue() {
        assertEquals(20, kingKong.getAttackValue());
    }

    @Test
    public void hasHairColour() {
        assertEquals("black", kingKong.getHairColour());
    }

    @Test
    public void canTakeDamage() {
        kingKong.setHealthValue(10);
        assertEquals(90, kingKong.getHealthValue());
    }

    @Test
    public void canRoar() {
        assertEquals("OOOHH OOOH AHH", kingKong.roar());
    }
}
