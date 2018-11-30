import device_management.Keyboard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyboardTest {
    Keyboard keyboard;

    @Before
    public void before() {
        keyboard = new Keyboard(80);
    }

    @Test
    public void hasNumberOfKeys() {
        assertEquals(80, keyboard.getNumberOfKeys());
    }

    @Test
    public void canPressKey() {
        keyboard.keyPress('q');
        assertEquals("keypress: 'q'", keyboard.getData());
    }

    @Test
    public void canSendData() {
        keyboard.keyPress('A');
        assertEquals("data: keypress: 'A'", keyboard.sendData());
    }

}
