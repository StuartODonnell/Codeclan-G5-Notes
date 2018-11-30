import java.util.ArrayList;

public class Airplane {

    private String destination;
    private int capacity;
    private ArrayList<Person> passengers;

    public Airplane(String destination, int capacity){
        this.destination = destination;
        this.capacity = capacity;
        this.passengers = new ArrayList<Person>();
    }

    public String getDestination() {
        return this.destination;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public ArrayList<Person> getPassengers() {
        return this.passengers;
    }

    public int passengerCount(){
        return this.passengers.size();
    }

    public void board(Person passenger){
        if(passengerCount() < getCapacity()){
            this.passengers.add(passenger);
        }
    }

    public void deplane(Person passenger){
//        deplane is a word, look it up
        this.passengers.remove(passenger);
    }




}
