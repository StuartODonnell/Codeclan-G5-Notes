import java.util.ArrayList;
import java.util.Collections;

import static spark.Spark.get;

public class SparkIntro {
    public static void main(String[] args) {
        get("/hello", (req, res)-> { return "Hello world";});

        get("/hello/:name", (req, res) -> { return "Hello " + req.params(":name");});

        get ("/random_name", (req, res) -> {
            ArrayList<String> names = new ArrayList<>();
            names.add("Jack");
            names.add("Victor");
            names.add("Isa");
            names.add("Winston");
            names.add("Tam");
            names.add("Naveed");

            Collections.shuffle(names);
            return names.get(0);
        });

        get ("/friends/:number", (req, res) -> {
            String [] friends = { "Jack", "Victor", "Winston", "Tam", "Isa", "Naveed" };
            int index = Integer.parseInt(req.params(":number")) - 1;
            return friends[index];
        });

        get ("/add/:num1/:num2", (req, res) -> {
            int num1 = Integer.parseInt(req.params(":num1"));
            int num2 = Integer.parseInt(req.params(":num2"));
            return num1 + num2;
        });

    }
}
