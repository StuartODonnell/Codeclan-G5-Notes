package Behaviours;

//this is an interface which has the method attack, so any class that
//implements this interface has to have this function.
//This function has to take in a class which is an iDamageable.

public interface iAttack {

    void attack(iDamagable victim);
}
