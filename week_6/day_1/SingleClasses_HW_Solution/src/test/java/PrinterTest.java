import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrinterTest {

    Printer printer;

    @Before
    public void before(){
        printer = new Printer(100, 100);
    }

    @Test
    public void printerHasEnoughPaper() {
        printer.print(5, 2);
        assertEquals(90, printer.getSheetsLeft());
    }

    @Test
    public void canRefillPaper() {
        printer.print(5, 2);
        printer.refill();
        assertEquals(100, printer.getSheetsLeft());
    }

    @Test
    public void tonerGoesDown(){
        printer.print(5, 2);
        assertEquals(90, printer.getTonerVol());
    }
}
