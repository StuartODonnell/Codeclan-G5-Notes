package Vehicles;

import Behaviours.iAttack;
import Behaviours.iDamagable;
import People.Person;
import Weapons.Weapon;

import java.util.ArrayList;

//This is an abstract class so an instance of this class can't be made.  Think of this class as a foundation
//upon which its subclasses can build from.  This class has attributes and methods which would be common to
//many other classes which is why its has been constructed as an abstract class.

public abstract class Vehicle implements iAttack, iDamagable {

    private int healthValue;
    private Weapon weapon;
    private ArrayList<Person> passengers;
    private int capacity;

    //Passing the weapon in here is an example of composition.  The Vehicle class is composed of the weapon object.
    //This class now has access to all of the methods and attributes in the weapon class.

    //as the health value is always going to start at 100 we initialize it in this way instead of passing it
    //in as a parameter.
    //The passengers arraylist is initialized as a new Arraylist so that it's always created empty.

    public Vehicle(Weapon weapon, int capacity) {
        this.healthValue = 100;
        this.weapon = weapon;
        this.capacity = capacity;
        this.passengers = new ArrayList<Person>();
    }

    public int getHealthValue() {
        return healthValue;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public ArrayList<Person> getPassengers() {
        ArrayList<Person> copyOfPassengers = new ArrayList<Person>(this.passengers);
        return copyOfPassengers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addPassengers(Person person){
        if(this.passengers.size() < this.capacity){
            this.passengers.add(person);
        }
    }

    public int passengerCount(){
        return this.passengers.size();
    }

    //This damageValue method is a method which can we called on any subclass of Vehicle.  Its returning
    //the damageValue of the weapon which is being passed into the constructor.
    public int damageValue(){
        return this.weapon.getDamageValue();
    }

    //All subclasses of this abstract class have to implement the methods from the iDamagable and iAttack interfaces.
    //As they are going to implement them in the exact same way we have created the methods in the abstract class so
    //that we aren't repeating ourselves in the subclasses.

    //The iAttack interface has a method called attack which has to take in an iDamageable.  We've called the
    //iDamageabe 'victim' and we're accessing the takeDamage method of the victim which is there as the iDamagable
    //interface forces it to be there.
    //The takeDamage method has to take in an int and so we're passing in the damageValue method  of this class.
    public void attack(iDamagable victim){
        victim.takeDamage(this.damageValue());
    }

    //The iDamagable interface has a method called takeDamage which has to take in an int when its executed.
    //In this instance we're removing the int that is passed in from the class's healthValue attribute
    public void takeDamage(int value){
        this.healthValue -= value;
    }


}
