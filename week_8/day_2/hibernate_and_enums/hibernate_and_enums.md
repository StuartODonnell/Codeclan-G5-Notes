# Persisting Enums using Hibernate

### Learning Objectives

- Understand how to persist Enums to a database

**Lesson Duration: 30 minutes**


## Enums and HibernateException

What would happen if we now introduce Enums into our code and wanted to persist them?

We could always convert the Enum to a string and save that. Then convert back to an Enum when we retrieve the data back from the database.

This is a lot of work though....

Luckily for us Hibernate and JPA have specific annotations to deal with Enums.

## Storing ENUM using Ordinal.

So we actually have 2 ways to store an Enum in the database. We an store them by ordinal (index) number or by String.

We will look at the ordinal first.

Let's amend our Pirate so that they have a weapon as well. These weapons will be simple Enums with a `damage property`.

> Hand out start point

Start by creating the Weapon Enum.

- In `models` package create a new Java class of type Enum called `Weapon`

```Java
// Weapon.java

package models;

public enum Weapon {

  CUTLASS(20),
  PISTOL(30),
  DAGGER(5);

  private int damageValue;

  Weapon(int damageValue) {
    this.damageValue = damageValue;
  }

  public int getDamageValue() {
    return damageValue;
  }
}
```

Next we will amend our pirates so that they take in a weapon into the constructor. Also create a getter and setter for the weapon.

```Java
// Pirate.Java
public class Pirate {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private Weapon weapon; // ADDED

    public Pirate() {
    }

    public Pirate(String firstName, String lastName, int age, Weapon weapon) { // MODIFIED
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weapon = weapon; // ADDED
    }

    // AS BEFORE


    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
  }

```

And finally we will change the pirates in the runner to take in a weapon.

```Java
// Runner.java

public static void main(String[] args) {

  //AS BEFORE

  Pirate pirate1 = new Pirate("Jack", "Sparrow", 32, Weapon.CUTLASS); // MODIFIED
  DBHelper.save(pirate1);
  Pirate pirate2 = new Pirate("Hector", "Barbossa", 65, Weapon.PISTOL); // MODIFIED
  DBHelper.save(pirate2);
  Pirate pirate3 = new Pirate("Will", "Turner", 23, Weapon.DAGGER); // MODIFIED
  DBHelper.save(pirate3);
  Pirate pirate4 = new Pirate("Elizabeth", "Swanson", 24, Weapon.PISTOL); // MODIFIED
  DBHelper.save(pirate4);
  Pirate pirate5 = new Pirate("Davey", "Jones", 154, Weapon.CUTLASS); // MODIFIED
  DBHelper.save(pirate5);

}
```

Ok so we are all set up now. Let's see how we persist these to the database.

## `@Enumerated` annotation

JPA gives us access to an `@Enumerated` annotation that will allow us to persist the Enum to the database.

How this is persisted will depend on the strategey that we use.

There are 2 options available to us.

### Ordinal

We can store the ordinal value of the Enum to the database.

```Java
// Pirate.Java

@Enumerated(value = EnumType.ORDINAL) // ADDED
public Weapon getWeapon() {
  return weapon;
}
```

If we now debug the program and go to the terminal and check the pirates table using psql you will see that each pirate has a numeric value under a `weapon` column.

This works well. When we retrieve the pirate object form the database hibernate will check the ordinal value and assign the Weapon with that ordinal to the pirate.

Check out the `foundPirate` in the debugger. They should have a `CUTLESS` as their weapon.

We can also get the value attached to that weapon.

```Java
// Runner.Java

 int damagevalue = foundPirate.getWeapon().getDamageValue();
```

Cool so this works well.....

One issue with this though.

What happens if we add another Weapon to the middle of the list of values in the Enum class?


```Java
// Weapon.Java

CUTLASS(20),
PISTOL(30),
CANNON(100),
DAGGER(5);
```

Ok at the minute every time we run the data is refreshed and re-saved so no big deal it will save the correct ordinal.

But what if we had data already in the database with ordinal values? When we add a new weapon the ordinals shift position. So when we pulled back the data from the database they would all have a different weapon than when we had originally saved.

> Instructor: You can demonstrate this if you want by chaninging the value of `hbm2ddl.auto` to update in hibenrate.cfg.xml and commenting out the creation of pirates in Runner. Remember to change them back if you do.

So this is only really useful if our Enum list isn't subject to change.

The preferred option is to save the Enum as a string to the database.


### String values

Let's change the value associated with our `@Enumerated` annotation.

```Java
// Pirate.Java

@Enumerated(value = EnumType.STRING) // MODIFIED
public Weapon getWeapon() {
  return weapon;
}
```

Now let's run the application and check the database again in psql.

Ok.. so now the value of the weapon column is a string of the Enum name.

Again hibernate will use this string value to assign the correct Weapon to the pirate upon retrieval from the database.

So now it won't matter which order the enums are in or if one is added as the name of the weapon will stay the same.

## Summary

We've seen:
 - How to persist Enums using ordinal and String values.

Yes it really is that simple to persist Enums to the database using the annotations. there isn't really much more to it than this.

The trick comes when we try to create pirates via a form on the web but we will cover that next week.

Next Lesson: [One To Many Annotations](../one_to_many/one_to_many.md)
