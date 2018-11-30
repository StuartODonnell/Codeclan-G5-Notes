import Building.Building;
import Kaiju.Godzilla;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodzillaTest {

    Godzilla godzilla;
    Building building;

    @Before
    public void setUp() {
        godzilla = new Godzilla("Zilla", 40);
        building = new Building();
    }

    @Test
    public void hasHealthValue() {
        assertEquals(100, godzilla.getHealthValue());
    }

    @Test
    public void canRoar() {
        assertEquals("ROAAAR", godzilla.roar());
    }

    @Test
    public void canAttackBuilding() {
        godzilla.attack(building);
        assertEquals(20, building.getHealthValue());
    }
}
