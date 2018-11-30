import models.Calculator;

import static spark.Spark.get;

public class Controller {
    public static void main(String[] args) {
        get("/add/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.add();
            return result;
        });

        get("/subtract/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.subtract();
            return result;
        });

        get("/multiply/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.multiply();
            return result;
        });

        get("/divide/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            double result = calculator.divide();
            return result;
        });
    }
}
