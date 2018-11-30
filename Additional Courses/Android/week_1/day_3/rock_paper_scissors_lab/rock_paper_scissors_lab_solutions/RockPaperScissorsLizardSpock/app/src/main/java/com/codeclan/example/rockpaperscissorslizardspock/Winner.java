package com.codeclan.example.rockpaperscissorslizardspock;

/**
 * Created by sandy on 20/10/2016.
 */
public enum Winner {
    USER("You win :-)"),
    ANDROID("Android wins :-("),
    DRAW("It's a Draw!");

    private String value;

    private Winner(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
