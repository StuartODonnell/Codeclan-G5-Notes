import People.Person;
import Vehicles.Jet;
import Weapons.Weapon;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JetTest {

    Jet jet;
    Weapon machineGun;
    Person person;

    @Before
    public void setUp() {
        machineGun = new Weapon("Machine Gun", 10);
        jet = new Jet(machineGun, 2);
    }

    @Test
    public void canAddPersonToJet() {
        jet.addPassengers(person);
        assertEquals(1, jet.passengerCount());
    }

    @Test
    public void canGetDamageValueFromWeapon(){
        assertEquals(10, jet.damageValue());
    }
}
