import device_management.Mouse;
import device_management.MouseType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MouseTest {
    Mouse mouse;

    @Before
    public void before() {
        mouse = new Mouse(3, MouseType.WIRELESS);
    }

    @Test
    public void hasNumberOfButtons() {
        assertEquals(3, mouse.getNumberOfButtons());
    }

    @Test
    public void hasType() {
        assertEquals(MouseType.WIRELESS, mouse.getType());
    }

    @Test
    public void canMove() {
        mouse.move("up");
        assertEquals("mouse move: up", mouse.getData());
    }

    @Test
    public void canClickButton() {
        mouse.clickButton(2);
        assertEquals("button 2 clicked!", mouse.getData());
    }

    @Test
    public void canSendData() {
        mouse.move("down");
        assertEquals("data: [mouse move: down]", mouse.sendData());
    }
}
