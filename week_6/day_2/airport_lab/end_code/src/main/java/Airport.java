import java.lang.reflect.Array;
import java.util.ArrayList;

public class Airport {

    private String location;
    private ArrayList<Person> departureLounge;

    public Airport(String name) {
        this.location = name;
        this.departureLounge = new ArrayList<Person>();
    }

    public String getLocation() {
        return this.location;
    }

    public ArrayList<Person> getDepartureLounge() {
        return this.departureLounge;
    }

    public int departuresCount(){
        return getDepartureLounge().size();
    }

    public void addToDepartures(Person passenger){
        this.departureLounge.add(passenger);
    }

    public void removeFromDepartures(Person passenger){
        this.departureLounge.remove(passenger);
    }

    public void planeDeparts(Airplane airplane){
        ArrayList<Person> departingPassengers = new ArrayList(this.departureLounge);
        for(Person passenger : departingPassengers){
            airplane.board(passenger);
            removeFromDepartures(passenger);
        }
    }

    public void planeArrives(Airplane airplane){
        if(airplane.getDestination() == getLocation()){
        ArrayList<Person> arrivingPassengers = new ArrayList(airplane.getPassengers());
        for(Person passenger : arrivingPassengers){
            airplane.deplane(passenger);
            addToDepartures(passenger);
            }
        }
    }
}
