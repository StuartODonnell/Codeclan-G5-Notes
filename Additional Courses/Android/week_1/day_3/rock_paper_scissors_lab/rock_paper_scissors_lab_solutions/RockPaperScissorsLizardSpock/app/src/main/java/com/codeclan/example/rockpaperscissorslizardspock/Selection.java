package com.codeclan.example.rockpaperscissorslizardspock;


public enum Selection {
    ROCK("Rock"),
    PAPER("Paper"),
    SCISSORS("Scissors"),
    LIZARD("Lizard"),
    SPOCK("Spock");

    private String value;

    private Selection(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
