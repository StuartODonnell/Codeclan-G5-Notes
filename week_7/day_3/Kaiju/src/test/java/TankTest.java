import Kaiju.Godzilla;
import People.Person;
import Vehicles.Tank;
import Weapons.Weapon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TankTest {

    Tank tank;
    Weapon cannon;
    Person person;
    Godzilla godzilla;

    @Before
    public void setUp() {
        cannon = new Weapon("Cannon", 25);
        tank = new Tank(cannon, 5);
        person = new Person();
        godzilla = new Godzilla("Zilla", 55);
    }

    @Test
    public void tankStartsEmpty() {
        assertEquals(0, tank.passengerCount());
    }

    @Test
    public void canAddPassenger() {
        tank.addPassengers(person);
        assertEquals(1, tank.passengerCount());
    }

    @Test
    public void hasAccessToWeaponsDamageValue() {
        assertEquals(25, tank.damageValue());
    }

    @Test
    public void canAttackGodzill() {
        tank.attack(godzilla);
        assertEquals(75, godzilla.getHealthValue());
    }

    @Test
    public void canBeAttackedByGodzilla() {
        godzilla.attack(tank);
        assertEquals(45, tank.getHealthValue());
    }
}
