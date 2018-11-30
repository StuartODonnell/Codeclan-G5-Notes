# One-To-Many Relationships using annotations

## Learning Objectives

- Understand the one-to-many relationship.
- Know how to map one-to-many relationships using hibernate annotations.

## The one-to-many relationship

With our Pirate system we have created database tables for Pirate and Ship but there is nothing that links the two of them at the moment.

For example, our Ship might want information about its crew.

> DRAW THE FOLLOWING

```
Pirate
id
first_name
last_name
age
weapon
```

```
Ship
id
name
```

In SQL, when we start to have multiple tables, we relate the data row to one or more data rows in another table. ( this is why we call them relational databases )
We can do this a few different ways.

- One to one
- One to many
- Many to many

We relate data by individual table row

## One-to-many

Lets focus on the one-to-many for our application just now.

What if we wanted to associate the pirate to the ship? How could we do this?

We can setup a one to many association. We can say one ship row is associated to many pirate rows.  With this setup, we use a ship_id as the FOREIGN KEY. We use this foreign key to reference the ID of the associated row in the other table.

So, our pirate table should now look like this:

```
Pirate
id
first_name
last_name
age
enrolment_number
ship_id - Linked to id of ship.
```  

There are two sides to this relationship. `One` ship has `many` pirates. But conversely `many` pirates are on `one` ship. We have to map both sides of the relationship in hibernate.
> Hand out start point.


## Many-to-one

So we can say that pirates `have a` ship. So we will add the ship as a property of the `Pirate` class.

Open up the `Pirate` class and add the following code:

```java
// Pirate.java

@Entity //ADDED
@Table(name="pirates")
public class Pirate {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private Weapon weapon;
    private Ship ship; // ADDED

    //AS Before

    public Ship getShip() {
        return ship;
    } // ADDED

    public void setShip(Ship ship) {
        this.ship = ship;
    } // ADDED
  }

```

And now we need to add the annotations to map the pirate to a ship. Again we will add the annotations to the getter so that hibernate uses this to access the property.

If we look at this logically we will say there are Many pirates to One Ship. So in the `Pirate` class the annotation we use to define this relationship is `@ManyToOne`.

```java
// Pirate.java

    @ManyToOne // ADDED
    @JoinColumn(name="ship_id", nullable=false) // ADDED
    public Ship getShip() {
        return ship;
    }
```

The `@ManyToOne` annotation is used to set the relationship between `Pirate` and `Ship` classes.

The `@JoinColumn` annotation will generate a column `ship_id` (a foreign key) in the pirates table which will point to the id of the `ships` table (primary key).

> Hibernate will take care of this for us.. We only need to worry about the Java side which is why we don't give the `Pirate` class a `ship_id` property.

To ensure that each pirate needs to have a ship we will set the foreign key as `nullable = false`. So there must be an entry in that column.

If we tried to save a pirate to the database without assigning them a ship then we would get a null pointer exception. along the lines of
`not-null property references a null or transient value : models.Pirate.ship`.

> We would also get an error if we save a pirate with a ship before we save the ship to the database as there would be no matching ship id.

So to ensure Pirate must have a ship we will also change the constructor of Pirate to take in the ship when we create a new Pirate object.


```java
// Pirate.java

public Pirate(String firstName, String lastName, int age, Weapon weapon, Ship ship) { // MODIFIED
  this.firstName = firstName;
  this.lastName = lastName;
  this.age = age;
  this.weapon = weapon;
  this.ship = ship; // ADDED
}

```

And lastly we will change the Runner to pass a ship into each pirate.

```Java
// Runner.Java

Ship pearl = new Ship("The Black Pearl");
        DBHelper.save(ship);

        Ship dutchman = new Ship("The Flying Dutchman");
        DBHelper.save(ship);

Pirate pirate1 = new Pirate("Jack", "Sparrow", 32, Weapon.CUTLASS, pearl); // MODIFIED
       DBHelper.save(pirate1);
       Pirate pirate2 = new Pirate("Hector", "Barbossa", 65, Weapon.PISTOL, pearl); // MODIFIED
       DBHelper.save(pirate2);
       Pirate pirate3 = new Pirate("Will", "Turner", 23, Weapon.DAGGER, dutchman); // MODIFIED
       DBHelper.save(pirate3);
       Pirate pirate4 = new Pirate("Elizabeth", "Swanson", 24, Weapon.PISTOL, dutchman); // MODIFIED
       DBHelper.save(pirate4);
       Pirate pirate5 = new Pirate("Davey", "Jones", 154, Weapon.CUTLASS, dutchman); // MODIFIED
       DBHelper.save(pirate5);

```

OK so our pirates have their ship. Next we need give the ship access to it's collection of pirates.

## One-to-many

And now we will map the collection of pirates in the Ship class.

For this we will use a `List`. A List in Java is the interface that all concrete lists in Java inherit from. (So ArrayList, SortedList, etc). When we get this back it behaves exactly like an ArrayList will.

We don't need to worry about passing this in to constructor or initialising it anywhere as we don't want to have to have a list of pirates before we are able to create the ship.

Hibernate will use the setter for this to assign a collection of pirates to the ship when queried from the database.

Open the Ship file and add the following.

```java
// Ship.java

@Entity
@Table(name = "ships")
public class Ship {

    private int id;
    private String title;
    private LevelType level;
    private List<Pirate> crew; // ADDED

    // AS BEFORE

    public List<Pirate> getCrew() {
        return crew;
    }

    public void setCrew(List<Pirate> crew) {
        this.crew = crew;
    }
}
```

So in this case we are mapping the one-to-many relationship so we need to use `@OneToMany` annotation.

Again we will add this above the getter for pirates.

Like before we will make it lazy loaded and write a method to get pirates for a specific ship.

```java
// Ship.java

    @OneToMany(mappedBy="ship", fetch = FetchType.LAZY) // ADDED
    public List<Pirate> getCrew() {
        return crew;
    }
```

The first thing that we should discuss here is what `lazy` loading and `eager` loading are:

- Eager Loading is a design pattern in which data initialisation occurs on the spot
- Lazy Loading is a design pattern which is used to defer initialisation of an object as long as it’s possible

One Ship can have multiple Pirates. In eager loading strategy, if we load the Ship data, it will also load up all pirates associated with it and will store it in memory.

But, when lazy loading is enabled, if we pull up a Ship, Pirate data won’t be initialised and loaded into a memory until an explicit call is made to it.

This is the preferred option as it will be more efficient. Also loading this list constantly can lead to problems later on.

Note that the `@OneToMany` annotation is `mapped by` the ship property in the Pirate class. This tells hibernate on which property to link the two tables together.


Run the runner and it should now save the Ship and Pirate entries.
>Note if configuration window pops up select the module to run from drop down (there should only be one option)

We can check this in the terminal by connecting into our database. Our pirates table should now include `ship_id` column with the appropriate ship id as a value.

## Getting a list of pirates per ship.

Let's check that we can get a list of pirates by ship back from the entries.

This operation should be owned by the ship so we will create a `DBShip` file to hold the method to get all the pirates.

- Create `DBShip` class in the `db` package

To get the list of pirates we will need to add a function into the DBShip file. This function will take in the instance of `Ship` that we want to get the pirates for.

```java
// DBShip.java

private Static Session session;

public static  List<Pirate> getCrewForShip(Ship ship) {
  session = HibernateUtil.getSessionFactory().openSession();
  List<Pirate> results = null;
  try {
    Criteria cr = session.createCriteria(Pirate.class);
    cr.add(Restrictions.eq("ship", ship));
    results =  cr.list();
  } catch (HibernateException e) {
    e.printStackTrace();
  } finally {
    session.close();
  }
  return results;
}
```

Note that in the restrictions we match the ship and not the ship id?

That is because hibernate is using the class which has a ship property. Not the database which has the ship_id property.

And lets check results in debugger.

```java
//Runner.java

List<Pirate> piratesOnPearl = DBShip.getCrewForShip(pearl);  // ADDED
```

We should now see our 2 pirates on that ship.

## Summary

We've seen:
 - What a one-to-many relationship is
 - How to map one-to-many-relationships in hibernate using annotations
 - How to return a collection from database.

 Next: [One to Many Lab](../one_to_many_lab/one_to_many_lab.md)
