# Many-To-Many Relationships using annotations

## Learning Objectives

- Understand the many-to-many relationship.
- Know how to map many-to-many relationships using hibernate annotations.

## The many-to-many relationship

We are now going to introduce a many-to-many relationship into our pirate system.

Let's say that our pirates can go on raids. One pirate can go on many raids and each raid can have many pirates on them at a time.

A `many-to-many` mapping can be implemented using a `List` java collection, just like the `one-to-many` mapping.

We already have seen how to map `List` collection in hibernate, so we are all set to go with `many-to-many` mapping.

Remember we want to use a List as there should be no duplicate element required in the collection.

When we create the many-to-many relationship we will need a join table to relate the two classes.

>Instructors draw this relationship on the board.

> Hand out start point.

## Many-to-Many

Let's add in a new class for `Raid`s.

```java
// Raid.java

package models;

import javax.persistence.*;

@Entity
@Table(name = "raids")
public class Raid {

  private int id;
  private String location;
  private int loot;

  public Raid() {
  }

  public Raid(String location, int loot) {
    this.location = location;
    this.loot = loot;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name = "location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Column(name = "loot")
  public int getLoot() {
    return loot;
  }

  public void setLoot(int loot) {
    this.loot = loot;
  }
}

```

And amend the hibernate.cfg.xml file to include this class in the mappings.

```xml
<!-- hibernate.cfg.xml -->

<mapping class = "models.Pirate"/>
<mapping class = "models.Ship"/>
<mapping class = "models.Captain"/>
<mapping class = "models.Raid"/> <!-- NEW -->
```

So we want to map a `List` in both `Raid` and `Pirate` class relating to each other.

Let's start by adding the List of raids to the `Pirate` class.

We will initialise this as an empty set in the constructor so that we can add to it later.

```java
// Pirate.java

@Entity
@Table(name="pirates")
public class Pirate {

  private int id;
  private String title;
  private List<Raid> raids; // NEW

  public Pirate() {
  }

  public Pirate(String firstName, String lastName, int salary, Ship ship) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.ship = ship;
    this.raids = new ArrayList<Raid>();
  }

  // AS BEFORE

  public List<Raid> getRaids() {
    return raids;
  }

  public void setRaids(List<Raid> raids) {
    this.raids = raids;
  }
}
```

Ok so now we need to add the annotations to map the raid to pirates. Again we will add the annotations to the getter so that hibernate uses this to access the property.

```java
// Pirate.java

@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
@ManyToMany
@JoinTable(name = "pirate_raid") // NEW
public List<Raid> getRaids() {
  return raids;
}
```

We will use a `@Cascade` annotation so that when it comes to adding pirates to raids we only need to update the pirate and it will cascade the update to the raid as well.

We don't want to mark this as CascadeType.ALL as this would mean that when we delete an pirate this would then delete all raids attached to this pirate and in turn all other pirates attached to that raid. PERSIST will only affect when we update.

Next we declare the name of the join table we will need.

Next we will have to give information about the join columns to be included in the join table. So these will be `pirate_id` and `raid_id`. The join columns are detailed within a set of `{}`
We declare the join column for this class using `@JoinColumn` annotation and the column relating to the Raid class as `inverseJoinColumns`

```java
// Pirate.java

@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
@ManyToMany
@JoinTable(name = "pirate_raid",
joinColumns = {@JoinColumn(name = "pirate_id", nullable = false, updatable = false)},
inverseJoinColumns = {@JoinColumn(name = "raid_id", nullable = false, updatable = false)})// NEW
public List<Raid> getRaids() {
  return raids;
}
```

We don't want these columns to be null or to be updated.

Next we add the set of pirates to `Raid` and annotate it in the same way as the set of raids in `Pirate` but with the `joinColumns` and `inverseJoinColumns` reversed.

Again in the constructor we will initialise this to be empty.

```java
// Raid.java

@Entity
@Table(name="raids")
public class Raid {

  private int id;
  private String title;
  private List<Pirate> pirates; // NEW

  public Raid() {
  }

  public Raid(String title) {
    this.title = title;
    this.pirates = = new ArrayList<Pirate>(); //NEW
  }

  //AS BEFORE

  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  @ManyToMany
  @JoinTable(name = "pirate_raid",
  joinColumns = {@JoinColumn(name = "raid_id", nullable = false, updatable = false)},
  inverseJoinColumns = {@JoinColumn(name = "pirate_id", nullable = false, updatable = false)})
  public List<Pirate> getPirates() {
    return pirates;
  }

  public void setPirates(List<Pirate> pirates) {
    this.pirates = pirates;
  } // NEW
}
```

Ok so we should have these mapped OK so let's try this out.

First of all drop and recreate the database

```bash
dropdb piratedb
createdb piratedb
```

In the `Runner` create and save a new `Raid`.

> Uncomment the code in DBRaid.

```java
// Runner.java

public static void main(String[] args) {

  // AS BEFORE

  Raid raid1 = new Raid("Tortuga", 200);
  DBHelper.save(raid1); // NEW
} // DEBUGGER HERE
```

Debug the file and our raid should be saved and our join table should have been created.

In terminal run `psql`:

```bash

psql
user=# \c piratedb

piratedb=# SELECT * FROM raids;

piratedb=#SELECT * FROM pirate_raid;
```


Nice. So now all that remains is adding pirates to raids.

Let's create an `addRaid()` in `Pirate`

```java
// Pirate.java

public void addRaid(Raid raid){
  this.raids.add(raid);
} // NEW
```

And an `addPirate()` method in `Raid`

```java
// Raid.java

public void addPirate(Pirate pirate){
  this.pirates.add(pirate);
} //  NEW
```

Next we will create a method in our `DBPirate` to call these and pass in the appropriate objects.

> Note: Create this outside of the main method.

```java
//DBPirate.java

public static void addPirateToRaid(Pirate pirate, Raid raid){
  pirate.addRaid(raid);
  raid.addPirate(pirate);
  DBHelper.update(pirate); // REMEMBER THIS WILL CASCADE UPDATE TO PROJECT
} // NEW

```
And finally we will call this method from the `Runner` and pass in the pirate and raid.

Let's add both pirates to the same raid.

```java
// Runner.java

public class Runner {

  public static void main(String[] args) {
    // AS BEFORE

    DBPirate.addPirateToRaid(pirate1, raid1);
    DBPirate.addPirateToRaid(pirate2, raid1);
  }
}
```

So lets see if this has worked!

In psql terminal

```bash
# terminal

piratedb=# SELECT * FROM pirate_raid;
```

Bosh! our pirates and raid have been linked together.

## TASK

Try deleting pirate1 and make sure that the raid isn't deleted and that the join table still has an entry for pirate2 and raid1. (Check in psql terminal)

## Summary

We've seen:
 - What a many-to-many relationship is
 - How to map many-to-many-relationships in hibernate using annotations

Next Lesson: [Querying a Many To Many](./querying_a_many_to_many.md)
