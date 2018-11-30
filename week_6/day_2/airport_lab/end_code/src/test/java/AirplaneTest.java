import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirplaneTest {

    Airplane airplane;
    Person passenger1;
    Person passenger2;
    Person passenger3;

    @Before
    public void setup(){
        airplane = new Airplane("Honolulu", 2);
        passenger1 = new Person("Colin");
        passenger2 = new Person("Louise");
        passenger3 = new Person("John");
    }

    @Test
    public void airplaneHasCapacity(){
        assertEquals(2, airplane.getCapacity());
    };

    @Test
    public void airplaneHasDestination() {
        assertEquals("Honolulu", airplane.getDestination());
    };

    @Test
    public void airplaneStartsEmpty() {
        assertEquals(0, airplane.passengerCount());
    }

    @Test
    public void airplaneCanBoard() {
        airplane.board(passenger1);
        assertEquals(1, airplane.passengerCount());
    }

    @Test
    public void airplaneCannotBoardBeyondCapacity(){
        airplane.board(passenger1);
        airplane.board(passenger2);
        airplane.board(passenger3);
        assertEquals(2, airplane.passengerCount());
    }

    @Test
    public void airplaneCanDeplanePassengers() {
        airplane.board(passenger1);
        airplane.deplane(passenger1);
        assertEquals(0, airplane.passengerCount());
    }
}
