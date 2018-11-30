import db.DBShip;
import db.DBPirate;
import models.Ship;
import models.Pirate;

public class Runner {

    public static void main(String[] args) {
        Pirate pirate = new Pirate("Jack", "Sparrow", 32);
        DBPirate.save(pirate);

        Ship ship = new Ship("The Black Pearl");
        DBShip.save(ship);
    }
}
