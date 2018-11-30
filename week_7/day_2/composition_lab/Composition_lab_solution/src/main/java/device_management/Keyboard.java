package device_management;

import behaviours.IInput;

public class Keyboard extends InputDevice implements  IInput {
    private int numberOfKeys;

    public Keyboard(int numberOfKeys) {
        this.numberOfKeys = numberOfKeys;
    }

    public int getNumberOfKeys() {
        return this.numberOfKeys;
    }

    public void keyPress(char key) {
        this.data =  "keypress: '" + key + "'";
    }

    public String sendData() {
        return "data: " + this.data;
    }
}
