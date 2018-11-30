import Weapons.Weapon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeaponTest {

    Weapon machineGun;
    Weapon rocketLauncher;

    @Before
    public void setUp() {
        machineGun = new Weapon("Machine Gun", 20);
        rocketLauncher = new Weapon("Rocket Launcher", 50);
    }

    @Test
    public void hasType() {
        assertEquals("Machine Gun", machineGun.getType());
        assertEquals("Rocket Launcher", rocketLauncher.getType());
    }

    @Test
    public void hasDamageValue() {
        assertEquals(20, machineGun.getDamageValue());
        assertEquals(50, rocketLauncher.getDamageValue());
    }
}
