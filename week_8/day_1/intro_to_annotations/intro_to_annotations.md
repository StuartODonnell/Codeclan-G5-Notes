# Introduction to Annotations

**Lesson duration: 1 hour**

### Learning Objectives

- Understand how annotations work
- Add annotations as a dependancy.
- Map Java classes with annotations.
- Create one-to-many relationship with annotations.

## Mapping our Pirate class.

So we are going to take the values from our `Pirate` class attributes and store them in our database in a table.

Java classes whose objects or instances will be stored in database tables are called `persistent` classes in Hibernate.

For Hibernate to work classes follow the POJO (Plain Old Java Object) rules.

These are:

- All attributes that you want to save to the database should be private and have getter and setter methods.

- All Java classes that will be mapped need a default constructor.

- All classes need an integer `id` in order to allow Hibernate to map the property to the primary key column of a database table.

So we will first of all change our `Pirate` class to match these rules.

> Hand out starter code

```java
// Pirate.java

package models;

public class Pirate {

  private int id;
  private String firstName;
  private String lastName;
  private int age;

  public Pirate() {
  }

  public Pirate(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age){
    this.age = age;
  }
}
```

Ok so now our class is ready to be mapped to a database table.

## Hibernate Annotations

Hibernate annotations are a way to define how the classes are mapped.

We have already seen the use of annotations in our test files. `@Before`
, `@Test`, etc that allows JUnit to identify specific actions we want to perform.

Annotations for Hibernate are added to the Java classes in the same way.

This way we can quickly understand the database table structure related to the class as it will be visible in the class itself.

Hibernate Annotations come from a set of standards known as EJB 3. (Enterprise Java Beans)

They follow this standard to allow you to transfer your application from one database type to another as long as they use these standards. (PSQL to mySQL, for example)


## Pirate tracker using annotations

The `hibernate.cfg.xml` file and `hibernate`, `postgres` and `Javassist` dependencies have already been added to the `pom.xml` file.


All that remains is to map the `Pirate` class to the database using annotations.

### Javax Persistance API

Maven projects come with an in-built Java Persistence API (JPA).

The JPA is a library of classes and methods that control the mapping of java classes to database tables through annotations.

When we mark a class as a a persistent entity hibernate will create a table in the database matching the details we provide in the mapped class.

Let's start by simply mapping the Pirate class.

We will first let hibernate know that we want to map over the pirate class to a database table.

### Adding table details

```java
// Pirate.java

package models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity //ADDED
@Table(name="pirates") // ADDED
public class Pirate {
// AS BEFORE
}
```

##### `@Entity` Annotation

The EJB 3 standard annotations are located in a package called `javax.persistence`, so we need to  import what we need from this package.

The `@Entity` annotation lets Hibernate know that we want to map this class to a database table

The `@Table` annotation tells Hibernate the name of the table we want to map to.

We can also include the properties of the table inside the `@Table` annotation. For now, we are just using the `name` of the table, which is `pirates`.

> Note  If we left the name blank Hibernate would create a table called `pirate`

### Adding an ID.

Next step is to add the primary key identifier to our `id` attribute.

We can either annotate a `property` directly or annotate the `getter`.

The difference being which one hibernate then uses to access the details. We should never access properties directly so we will add all other annotations to the getters.

```java
// Pirate.java

    @Id // ADDED
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ADDED
    @Column(name = "id") // ADDED
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

```

Each table will have a primary key, which you annotate on the class with the `@Id` annotation.

By default, the `@Id` annotation will automatically decide which way the Primary Key is managed.  However, you can override this by applying the `@GeneratedValue `annotation. This lets Hibernate determine which generator type to use.

We want to use the standard `Auto Increment` key so we will use `GenerationType.IDENTITY` as the strategy Hibernate uses. (Yes, this is an ENUM)

The `@Column` annotation is used to specify the details of the column we want to map the property to.

Again we can add set values associated with this column.

`name` tells Hibernate  the name of the column. (Left black it will match the property name)

`length` specifies the size of the column.

`nullable` marks the column as `NOT NULL` when set to true. This allows `null` values in the column.

`unique` specifies that the column should contain only unique values.

As a minimum we will assign the column name.

### Mapping our properties.

Now let's map over the rest of the properties that we want persisted to the database using the `@Column` annotation.

```java

// Pirate.java

    @Column(name = "first_name") // ADDED
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name") // ADDED
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age") // ADDED
    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
```

Lastly we need to update `hibernate.cfg.xml` to point to the mapping resource.

As the annotations are declared in the class this is will be our new mapping resource.

We add this to hibernate.cfg.xml so that when we run the application Hibernate will know which classes should be mapped and that the mapping properties are located within the class.

The `Pirate` class is in the `models` package so we will point to it using `models.Pirate`

```xml
<!-- hibernate.cfg.xml -->

    <mapping class = "models.Pirate"/> <!-- ADDED -->
  </session-factory>
</hibernate-configuration>
```


## Saving pirate to DataBase

So now we have our Hibernate configuration and our class mapping set up the last thing to do is to be able to save a pirate to the database.

To do this we will create a new file called `DBPirate`. This file will hold the database related methods for the pirate.

We will create this in the `db` package.

- Right click db
- Select new > Java class
- Call the class `DBPirate`

First of all we will need to add in a `Session` object to open a connection to the database and a `Transaction` object to commit our changes.

We don't want to have to new up an instance of DBPirate each time we want to use these so we will make them static.

```java
// DBPirate.javax

package db;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBPirate {

  private static Session session; // ADDED
  private static Transaction transaction; // ADDED

}
```

Next we will write a static method to save a pirate to the database. To do this we will use hibernates in built `save` method. This means that we won't have to write a single line of SQL in order to save an object. Sweet huh?

```java
// DBPirate.javax

package db;

import models.Pirate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBPirate {

  private static Session session;
  private static Transaction transaction;

  public static void save(Pirate pirate) { // ADDED
    session = HibernateUtil.getSessionFactory().openSession();
    try {
      transaction = session.beginTransaction();
      session.save(pirate);
      transaction.commit();
    } catch (HibernateException e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
}

```

As we are working with databases, which could be accessed over a network, we have to use try-catch blocks so that if there are any errors we can deal with them.

We will also use the finally block to make sure that the session is closed even if the save fails.

The SessionFactory is able to handle all of the transactions for us with minimal SQL needed.

Also have you noticed that we haven't created an SQL file for tables yet?

We don't have to!

All we need to do is create the database to work with and hibernate will map over the class and create the table for us because we have specified its details in  the pirate class

Now we will save a pirate in the runner file and use debugger to make sure it has been persisted.

```java
//Runner.javax

public class Runner {

    public static void main(String[] args) {
        Pirate pirate = new Pirate("Jack", "Sparrow", 32);
        DBPirate.save(pirate);
    } // DEBUGGER BREAKPOINT HERE
}

```

If you now debug we will see the pirate object. If you expand the pirate object you should see that the id property now has a value of 1 attached to it.

If you go to console we should see this output:

```
Hibernate: select nextval ('hibernate_sequence')
Hibernate: insert into pirates (first_name, last_name, enrolment_number, id) values (?, ?, ?, ?)
```

This means it has worked. We can see the SQL generated by hibernate and the values are encapsulated away so they are never exposed at this layer. If we go to terminal and connect in to `psql` we should see Jack Sparrow in our database


```bash
# terminal

psql
\c piratedb
\dt # Check tables created
SELECT * FROM pirates;
```


### Task: (20 minutes)

1. Create a new class called `Ship`
2. Ship will have a name.
3. Add annotations to `Ship` to map the class and properties to a table called `ships`
4. Create a `DBShip` file and method to save ship to database.
5. Create a new ship in the Runner and save.

> Remember to add the mapping property to hibernate.ccg.xml


### Solution

```java
// Ship.java
package models;

import javax.persistence.*;

@Entity
@Table(name = "ships")
public class Ship {

  private int id;
  private String name

  public Ship() {
  }


  public Ship(String name) {
    this.name = name;
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

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

```

```java
// DBShip.java

package db;

import models.Ship;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBShip {

  private static Session session;
  private static Transaction transaction;

  public static void save(Ship ship){
    session = HibernateUtil.getSessionFactory().openSession();
    try {
      transaction = session.beginTransaction();
      session.save(ship);
      transaction.commit();
    } catch (HibernateException e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
}

```

```java
// Runner.java
import db.DBShip;
import db.DBPirate;
import models.Ship;
import models.Pirate;

public class Runner {

  public static void main(String[] args) {
    Pirate pirate = new Pirate("Jack", "Sparrow", 67);
    DBPirate.save(pirate);

    Ship ship = new Ship("The Black Pearl");
    DBShip.save(ship);
  }
}

```


## Summary

We've seen:
 - What annotations are in hibernate.
 - Advantages of using annotations.
 - Setting up a Hibernate annotation project,
 - Mapping a class to hibernate using annotations


Annotations are a far more common way of mapping classes to database tables using hibernate so we will continue to use these for the remainder of this module.

There are a number of advantages to using annotations

- Compile-time checking : writing in Java (instead of Xml) is very user-friendly in the IDE nowadays. No more typos discovered when starting your application.

- annotations are essentially less wordy than writing out the mapping files in XML.

Next we will look at querying the database to get our pirates back.

Next Lesson: [Criteria Queries](../criteria_queries/criteria_queries.md)
