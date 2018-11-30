import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    Calculator calculator;

    @Before
    public void before(){
        calculator = new Calculator(2, 4);
    }

    @Test
    public void canAdd() {
        assertEquals(6, calculator.add());
    }

    @Test
    public void canSubtract() {
        assertEquals(-2, calculator.subtract());
    }

    @Test
    public void canMultiply() {
        assertEquals(8, calculator.multiply());
    }

    @Test
    public void canDivide() {
        calculator = new Calculator(25, 5);
        assertEquals(5, calculator.divide());
    }
}
