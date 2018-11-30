package com.codeclan.example.rockpaperscissors;


public enum Selection {
    ROCK("Rock"),
    PAPER("Paper"),
    SCISSORS("Scissors");

    private String value;

    private Selection(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
