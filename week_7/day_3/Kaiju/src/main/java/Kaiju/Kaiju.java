package Kaiju;

import Behaviours.iAttack;
import Behaviours.iDamagable;

//This is an abstract class so an instance of this class can't be made.  Think of this class as a foundation
//upon which its subclasses can build from.  This class has attributes and methods which would be common to
//many other classes which is why its has been constructed as an abstract class.

public abstract class Kaiju implements iAttack, iDamagable {

   private String name;
   private int healthValue;
   private int attackValue;

   //as the health value is always going to start at 100 we initialize it in this way instead of passing it
    //in as a parameter.

   public Kaiju(String name, int attackValue) {
       this.name = name;
       this.healthValue = 100;
       this.attackValue = attackValue;
   }

   public String getName(){
       return this.name;
   }

   public int getHealthValue(){
       return this.healthValue;
   }

   public int getAttackValue() {
       return this.attackValue;
   }

   public void setHealthValue(int damage) {
       this.healthValue -= damage;
   }


   //This abstract method means every subclass has to implement a meathod called roar which returns a string,
    //how they implement it is up to them.
   public abstract String roar();

   //All subclasses of this abstract class have to implement the methods from the iDamagable and iAttack interfaces.
    //As they are going to implement them in the exact same way we have created the methods in the abstract class so
    //that we aren't repeating ourselves in the subclasses.

    //The iDamagable interface has a method called takeDamage which has to take in an int when its executed.
    //In this instance we're removing the int that is passed in from the class's healthValue attribute
   public void takeDamage(int value){
       this.healthValue -= value;
   }
    //The iAttack interface has a method called attack which has to take in an iDamageable.  We've called the
    //iDamageabe 'victim' and we're accessing the takeDamage method of the victim which is there as the iDamagable
    //interface forces it to be there.
    //The takeDamage method has to take in an int and so we're passing in the attack value attribute of this class.

    //If we look at both of these methods together, the iDamagable's health will decrease by the iAttacks attackValue.
   public void attack(iDamagable victim){
       victim.takeDamage(this.attackValue);
   }
}
