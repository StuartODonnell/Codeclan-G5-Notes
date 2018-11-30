import db.DBHelper;
import db.DBPirate;
import models.Ship;
import models.Pirate;

import java.util.List;

public class Runner {

    public static void main(String[] args) {

        Ship ship = new Ship("The Black Pearl");
        DBHelper.save(ship);

        Pirate pirate1 = new Pirate("Jack", "Sparrow", 32);
        DBHelper.save(pirate1);
        Pirate pirate2 = new Pirate("Hector", "Barbossa", 65);
        DBHelper.save(pirate2);
        Pirate pirate3 = new Pirate("Will", "Turner", 23);
        DBHelper.save(pirate3);
        Pirate pirate4 = new Pirate("Elizabeth", "Swanson", 24);
        DBHelper.save(pirate4);
        Pirate pirate5 = new Pirate("Davey", "Jones", 154);
        DBHelper.save(pirate5);


        pirate2.setAge(66);
        DBHelper.update(pirate2);

        ship.setName("Flying Dutchman");
        DBHelper.update(ship);

        DBHelper.delete(pirate2);

        List<Pirate> pirates = DBHelper.getAll(Pirate.class);

        Pirate foundPirate = DBHelper.find(Pirate.class, pirate1.getId());

        Ship foundShip = DBHelper.find(Ship.class, ship.getId());

        List<Pirate> studentsByAge = DBPirate.orderByAge();

        double averageAge = DBPirate.getAverageAge();


    }
}
