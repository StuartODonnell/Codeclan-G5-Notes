import db.DBConsole;
import db.DBGame;
import db.DBHelper;
import models.Console;
import models.Game;
import models.Genre;
import models.Owner;

import java.util.List;

public class Runner {

    public static void main(String[] args) {
        Game mario = new Game("Super Mario", Genre.ARCADE);
        DBHelper.save(mario);

        Game spyro = new Game("Spyro the Dragon", Genre.ARCADE);
        DBHelper.save(spyro);

        Game driver = new Game("Driver", Genre.RPG);
        DBHelper.save(driver);

        Game cod = new Game("Call of Duty", Genre.FPS);
        DBHelper.save(cod);

        Console ps2 = new Console("Sony", "PS2", "Europe", driver);
        DBHelper.save(ps2);

        Console nintendo = new Console("Fusajiro Yamauchi", "Mega Drive", "Europe", mario);
        DBHelper.save(nintendo);

        Owner owner = new Owner(mario);
        DBHelper.save(owner);

        Owner owner2 = new Owner(mario);
        DBHelper.save(owner2);

        DBHelper.addGameToConsole(ps2, spyro);
        DBHelper.addGameToConsole(ps2, driver);
        DBHelper.addGameToConsole(ps2, cod);
        DBHelper.addGameToConsole(nintendo, mario);

        List<Game> nintendoGames = DBConsole.availableGames(nintendo);
        List<Game> ps2Games = DBConsole.availableGames(ps2);

        List<Owner> ownersWhoLikeMario = DBGame.ownersWhoFavGame(mario);

        Console consoleMarioIsIn = DBGame.consoleBeingPlayedOn(mario);

        Game gameBeingPlayedInPs2 = DBConsole.gameBeingPlayed(ps2);

    }

}
