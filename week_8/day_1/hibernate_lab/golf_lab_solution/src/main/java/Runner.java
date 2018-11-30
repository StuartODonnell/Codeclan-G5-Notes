import db.DBGolfer;
import models.Golfer;

import java.util.List;

public class Runner {

    public static void main(String[] args) {

        Golfer golfer1 = new Golfer("Nick Faldo", 45);
        DBGolfer.save(golfer1);

        Golfer golfer2 = new Golfer("Rory McIlroy", 28);
        DBGolfer.save(golfer2);

        golfer1.setAge(46);
        DBGolfer.update(golfer1);

        DBGolfer.delete(golfer2);


        List<Golfer> golfers = DBGolfer.getAll();

        Golfer foundGolfer = DBGolfer.find(golfer1.getId());

        golfer1.setAge(10);
        DBGolfer.save(golfer1);


        DBGolfer.deleteAll();

        List<Golfer> emptyGolfers = DBGolfer.getAll();

    }
}
