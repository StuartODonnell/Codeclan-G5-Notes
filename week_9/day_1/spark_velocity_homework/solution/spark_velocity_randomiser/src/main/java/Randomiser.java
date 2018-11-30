import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.get;

import java.util.ArrayList;
import java.util.HashMap;

public class Randomiser {

    public static void main(String[] args) {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        String[] allNames = {"Jack" , "Victor", "Tam", "Winston", "Isa" };
        People people = new People(allNames);

        get("/one", (req, res) -> {
            ArrayList<String> names = people.getRandomNames(1);
            return getModelAndView(names);
        }, velocityTemplateEngine);

        get("/two", (req, res) -> {
            ArrayList<String> names = people.getRandomNames(2);
            return getModelAndView(names);
        }, velocityTemplateEngine);

        get("/three", (req, res) -> {
            ArrayList<String> names = people.getRandomNames(3);
            return getModelAndView(names);
        }, velocityTemplateEngine);

        get("/four", (req, res) -> {
            ArrayList<String> names = people.getRandomNames(4);
            return getModelAndView(names);
        }, velocityTemplateEngine);
    }

    private static ModelAndView getModelAndView(ArrayList<String>names) {
        HashMap<String, Object> model = new HashMap<>();

        switch (names.size()) {
            case 0:
                return new ModelAndView(model, "empty.vtl");
            case 1:
                model.put("name", names.get(0));
                return new ModelAndView(model, "one.vtl");
            case 2:
                model.put("name1", names.get(0));
                model.put("name2", names.get(1));
                return new ModelAndView(model, "two.vtl");
            case 3:
                model.put("name1", names.get(0));
                model.put("name2", names.get(1));
                model.put("name3", names.get(2));
                return new ModelAndView(model, "three.vtl");
            case 4:
                model.put("name1", names.get(0));
                model.put("name2", names.get(1));
                model.put("name3", names.get(2));
                model.put("name4", names.get(3));
                return new ModelAndView(model, "four.vtl");
        }
        return null;
    }
}
