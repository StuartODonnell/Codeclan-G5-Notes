package com.example.allymcgilloway.robot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by allymcgilloway on 13/12/2017.
 */

public class RobotTest {

    Robot robot;

    @Before
    public void before(){
        robot = new Robot("C3PO", "Gold");
    }

    @Test
    public void canCheckBattery(){
        assertEquals(100.0, robot.checkBattery(), 0.1);
    }

    @Test
    public void canMakeDrink(){
        assertEquals("I am making tea", robot.makeDrink("tea"));
    }

    @Test
    public void canWashDishes(){
        assertEquals("I am washing the dishes", robot.washDishes());
    }

    @Test
    public void canDoDusting(){
        assertEquals("I am dusting", robot.doDusting());
    }

    @Test
    public void canRecharge(){
        robot.washDishes();
        robot.doDusting();
        robot.rechargeBattery();
        assertEquals(100.0, robot.checkBattery(), 0.1);
    }

    @Test
    public void cantDoDishesNotEnoughBattery(){
        robot.washDishes();
        robot.washDishes();
        robot.washDishes();
        assertEquals(10, robot.checkBattery(), 0.1);
        assertEquals("Not enough battery", robot.washDishes());
    }


}
