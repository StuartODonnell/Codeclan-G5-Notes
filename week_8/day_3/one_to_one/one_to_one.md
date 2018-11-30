# One-To-One Relationships using annotations

## Learning Objectives

- Understand the one-to_one relationship.
- Know how to map one-to-one relationships using hibernate annotations.

**Lesson Duration: 45 minutes**

## The one-to-one relationship

We are now going to introduce a one-to-one relationship into our pirate system.

One-To-One relationships are fairly common place and it is important to understand how to deal with them in Hibernate.

For this we are going to introduce a `Captain` class for our ship.
We will say that One captain belongs to one ship and vice versa.

A `one-to-one` mapping can be implemented with annotations just like the `one-to-many` mapping.

> Hand out start point.

## One-To_One

So we have a new class for `Captain`. We have given the captain the same properties as a pirate but also a popularity rating as a property.

We won't worry about inheritance in this case... we will cover that tomorrow.

### Annotation for one-to-one

We have passed a Ship into the captains constructor but we will not need to pass a captain into the ships constructor. This makes sense to do this as we can logically say that we can create a ship without the need for a captain initially but to create a captain we want to have a ship for them to manage.


 As we are passing the ship into the captains constructor this is the side that will have the foreign key. So we will need to create the join column to hold this.


```java
// Captain.java

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "ship_id", nullable = false)
public Ship getShip() {
  return ship;
}
```

Ok so now we need to add the Captain as a property of `Ship` and annotate that with `@OneToOne`.

Again we will add the property but we don't need to initialise it or pass it to the ships constructor as we should be able to have a ship without a captain.

```java
// Ship.java

public class Ship {

  private int id;
  private String name;
  private List<Pirate> crew;
  private Captain captain; // ADDED

  // AS BEFORE

  @OneToOne(mappedBy = "ship", fetch = FetchType.LAZY)
  public Captain getCaptain() {
    return captain;
  } // ADDED

  public void setCaptain(Captain captain) {
    this.captain = captain; // ADDED
  }
}
```

So now we have added the relationship to both classes we will save a couple of captains to the database.

In `Runner` add the following lines.

```java
// Runner.javax

// AS Before
DBHelper.save(pirate3);

Captain captain1 = new Captain("Jack", "Sparrow", 32, Weapon.CUTLASS, 100, pearl); // ADDED
DBHelper.save(captain1); // ADDED
Captain captain2 = new Captain("Davey", "Jones", 154, Weapon.CUTLASS, 39, dutchman); // ADDED
DBHelper.save(captain2); // ADDED

```

Run this and have a look at the tables in psql via terminal.

We should now see the 2 captains saved each with a ship_id matching the primary key of relevant ship.

Cool.

## Querying the One to One

As both of the OneToOne annotations will set the fetch type to lazy by default (a good thing!) we will need to write queries to retrieve them back fro the database.

Let's start by writing the query to return the captain of a ship.

This is exactly the same kind of query we have used for one to many relationships.  

We will do this in `DBShip`.


```java
// DBShip.java

public static Captain getCaptain(Ship ship){
  session = HibernateUtil.getSessionFactory().openSession();
  Captain captain = null;

  try {
    Criteria cr = session.createCriteria(Captain.class);
    cr.add(Restrictions.eq("ship", ship));
    captain = (Captain)cr.uniqueResult();
  } catch (HibernateException e) {
    e.printStackTrace();
  } finally {
    session.close();
  }

  return captain;
}

```

Now lets add a line in to the runner to get the captain back for the pearl.

```java
// Runner.java
public static void main(String[] args) {
  // AS BEFORE

  Captain pearlCaptain = DBShip.getCaptain(pearl);

}



```

Now if we debug this we should get Jack Sparrow back from the database.

Simples!

## Digging deeper into the chain..


What if we wanted to find the captain of a particular pirate...

> Give students time to suggest solutions.

So we could write a similar method to the one above but match the ship to `pirate.getShip()`.


```java
// DBPirate

public static Captain getPiratesCaptain(Pirate pirate){
  session = HibernateUtil.getSessionFactory().openSession();
  Captain captain = null;
  try {
    transaction = session.beginTransaction();
    Criteria cr = session.createCriteria(Captain.class);
    cr.add(Restrictions.eq("ship", pirate.getShip()));
    captain = (Captain)cr.uniqueResult();
  } catch (HibernateException e) {
    transaction.rollback();
    e.printStackTrace();
  } finally {
    session.close();
  }
  return captain;
}
```

And in the runner.

```java
// Runner.java
Captain willsCaptain = DBPirate.getPiratesCaptain(pirate2);
```
Now if we debug this we should get back Davey Jones.

## Summary

We've seen:
 - What a one-to-one relationship is
 - How to map one-to-one-relationships in hibernate using annotations
 - How to query a one to one.


Next Lesson: [Many To Many](../many_to_many/many_to_many.md)
