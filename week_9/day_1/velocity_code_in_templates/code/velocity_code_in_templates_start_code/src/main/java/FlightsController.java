import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

import static spark.Spark.get;

public class FlightsController {
    public static void main(String[] args) {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        Flight flight = new Flight("SC666", "Scottish Airways","Inverness", "New York");

        get("/flights", (req, res) -> {

            HashMap<String, Object> model = new HashMap<>();
            model.put("flight", flight);
            return new ModelAndView(model, "flights.vtl");
        }, velocityTemplateEngine);
    }
}
