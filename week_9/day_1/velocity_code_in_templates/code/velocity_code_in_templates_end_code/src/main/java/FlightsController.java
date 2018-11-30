import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class FlightsController {
    public static void main(String[] args) {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
        staticFileLocation("/public");

        ArrayList<Flight> flights = new ArrayList<>();
        Flight flight = new Flight("SC666", "Scottish Airways","Inverness", "New York");
        Flight flight2 = new Flight("US123", "US Airlines", "New York", "Sydney");
        Flight flight3 = new Flight("AU555", "Aussie Airways", "Sydney", "London");
        flight2.setSeatsAvailable(false);

        flights.add(flight);
        flights.add(flight2);
        flights.add(flight3);

        get("/flights", (req, res) -> {

            HashMap<String, Object> model = new HashMap<>();
            model.put("flights", flights);
            model.put("template", "flights.vtl");
            return new ModelAndView(model, "layout.vtl");
        }, velocityTemplateEngine);
    }
}
