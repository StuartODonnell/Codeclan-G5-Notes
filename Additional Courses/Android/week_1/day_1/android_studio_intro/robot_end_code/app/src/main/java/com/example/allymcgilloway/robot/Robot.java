package com.example.allymcgilloway.robot;

/**
 * Created by allymcgilloway on 13/12/2017.
 */

public class Robot {

    private String name;
    private String colour;
    private double battery;


    public Robot(String name, String colour){
        this.name = name;
        this.colour = colour;
        this.battery = 100.0;
    }

    public double checkBattery(){
        return this.battery;
    }

    public String makeDrink(String drink){
        if (checkBattery() > 10.0) {
            this.battery -= 10.0;
            return "I am making " + drink;
        }
        return "Not enough Battery";
    }

    public String washDishes(){
        if (checkBattery() > 30.0) {
            this.battery -= 30.0;
            return "I am washing the dishes";
        }
        return "Not enough battery";
    }

    public String doDusting(){
        if (checkBattery() > 20.0) {
            this.battery -= 20.0;
            return "I am dusting";
        }
        return "Not enough battery";
    }

    public void rechargeBattery(){
        this.battery = 100.0;
    }
}
