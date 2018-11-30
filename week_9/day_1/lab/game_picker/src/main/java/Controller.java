import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Controller {
    public static void main(String[] args) {

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        ArrayList<String> games = new ArrayList<String>();
        games.add("Fantasy World Dizzy");
        games.add("Operation Wolf");
        games.add("Bad Dudes Vs Dragon-ninja");

        get("/game", (req, res) -> {
            Collections.shuffle(games);
            String randomGame = games.get(0);
            HashMap<String, Object> model = new HashMap<String, Object>();
            model.put("game", randomGame);
            return new ModelAndView(model, "result.vtl");
        }, velocityTemplateEngine);

    enableDebugScreen();
    }
}
