package device_management;

import behaviours.IInput;

public class Mouse extends InputDevice implements IInput {
    private int numberOfButtons;
    private MouseType type;

    public Mouse(int numberOfButtons, MouseType type) {
        this.numberOfButtons = numberOfButtons;
        this.type = type;
    }

    public int getNumberOfButtons() {
        return this.numberOfButtons;
    }

    public MouseType getType() {
        return this.type;
    }

    public void move(String direction) {
        this.data =  "mouse move: " + direction;
    }

    public void clickButton(int buttonNumber) {
        this.data = "button " + buttonNumber + " clicked!";
    }

    public String sendData() {
        return "data: [" + this.data + "]";
    }
}
