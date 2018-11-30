import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirportTest {

    Airport airport;
    Person passenger1, passenger2, passenger3, passenger4;
    Airplane departingPlane, arrivingPlane, connectingPlane;

    @Before
    public void setup(){
        airport = new Airport("Prestwick");

        passenger1 = new Person("Colin");
        passenger2 = new Person("Louise");
        passenger3 = new Person("John");
        passenger4 = new Person("Steve");

        departingPlane = new Airplane("Blackpool", 2);
        arrivingPlane = new Airplane("Prestwick", 2);
        connectingPlane = new Airplane("Honolulu", 2);

        arrivingPlane.board(passenger1);
        connectingPlane.board(passenger2);
    }

    @Test
    public void airportHasName(){
        assertEquals(airport.getLocation(), "Prestwick");
    }


    @Test
    public void departuresStartsEmpty() {
        assertEquals(airport.departuresCount(), 0);
    }

    @Test
    public void airportCanAddToDepartures() {
        airport.addToDepartures(passenger4);
        assertEquals(airport.departuresCount(), 1);
    }

    @Test
    public void airportCanRemoveFromDepartures() {
        airport.addToDepartures(passenger3);
        airport.removeFromDepartures(passenger3);
        assertEquals(airport.departuresCount(), 0);
    }

    @Test
    public void planeCanDepartAirport() {
        airport.addToDepartures(passenger3);
        airport.addToDepartures(passenger4);
        airport.planeDeparts(departingPlane);
        assertEquals(airport.departuresCount(), 0);
        assertEquals(departingPlane.passengerCount(), 2);
    }

    @Test
    public void planeCanArriveAtAirport() {
        airport.planeArrives(arrivingPlane);
        assertEquals(arrivingPlane.passengerCount(), 0);
        assertEquals(airport.departuresCount(), 1);
    }

    @Test
    public void connectingPlaneCanArriveAtAirport(){
        airport.planeArrives(connectingPlane);
        assertEquals(connectingPlane.passengerCount(), 1);
        assertEquals(airport.departuresCount(), 0);
    }
}
