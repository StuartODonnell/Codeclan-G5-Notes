package Building;

import Behaviours.iDamagable;

//This class has an attribute healthValue that is initialized as 100 whenever
//a new instance of this class is created.
//It implements the iDamageable interface so it has to have the method takeDamage.
//Take damage is being implemented here by its health value going down by
//2 times the amount its being attacked by.

public class Building implements iDamagable {

    private int healthValue;

    public Building() {
        this.healthValue = 100;
    }

    public int getHealthValue() {
        return healthValue;
    }

    public void takeDamage(int value) {
        this.healthValue -= (value * 2);
    }
}
