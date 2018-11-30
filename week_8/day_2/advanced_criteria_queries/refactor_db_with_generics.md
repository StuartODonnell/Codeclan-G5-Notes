# Refactoring DB files with generics

## Learning Objectives

- Understand how to refactor crud methods
- Know how to use generics when returning database queries.
- Know how to perform an update, find and delete one entry.

**Lesson Duration: 45 minutes**

## Refactoring the DB files.

When we are creating a new class that we want persisted we have to write a `save()`, `update()`, etc twice for each class in separate DB files.

Wouldn't it be better if we could just have one file with methods that could be used for any class?

Well with hibernate we can!

You will notice that save takes in a type of either `Pirate` or `Ship`. We can just declare that this method takes in a parameter of type `Object`. All classes we write by default inherit from an `Object` class. So using `Polymorphism` we can save whichever class is passed in.

## Save

> Continue with code from previous lesson.

Create a new file called `DBHelper`. This is where methods common to all classes will be written.

Let's write the save function and test it out.

This will look the same as the other save methods we have written but this time will take in an object and save that.

```java
// DBHelper.java

public class DBHelper {
  private static Transaction transaction;
  private static Session session;

  public static void save(Object object){
    session = HibernateUtil.getSessionFactory().openSession();
    try {
      transaction = session.beginTransaction();
      session.save(object);
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

Now regardless of whether we pass in an `Pirate` or a `Ship`, or any other hibernate mapped class for that matter, it will save it to the appropriate table.

Try it out by changing the Runner file.

```java
// Runner.jave

public class Runner {

  public static void main(String[] args) {

    Pirate pirate1 = new Pirate("Bruce", "Wayne", 33, 1234);
    DBHelper.save(pirate1);  // MODIFIED
    Pirate pirate2 = new Pirate("Clark", "Kent", 27, 2345);
    DBHelper.save(pirate2); // MODIFIED
    Pirate pirate3 = new Pirate("Diane", "Prince", 23, 3456);
    DBHelper.save(pirate3); // MODIFIED
    Pirate pirate4 = new Pirate("Arthur", "Curry", 24, 4567);
    DBHelper.save(pirate4); // MODIFIED
    Pirate pirate5 = new Pirate("Barry", "Allan", 24, 5678);
    DBHelper.save(pirate5); // MODIFIED


    // AS BEFORE
  }
}
```

Now when we run this and check the database in psql we should see our entries saved as usual.


### Update

Lets say we wanted to update a pirate.

Well we have setters in the class so we can change details of the object.

To update we will write another method in the `DBHelper` to update the details. This will be done in the same way as the `save` method.

For just now we will update by passing in the `Object` we want to update. This will come in useful when we build the web application next week.

```java
  //DBHelper.java

  public static void update(Object object){
    session = HibernateUtil.getSessionFactory().openSession();
    try {
      transaction = session.beginTransaction();
      session.update(object);
      transaction.commit();
    } catch (HibernateException e) {
      transaction.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
```

So let's say that Hector Barbossa has had a birthday and is now 66.

We will update his age in the `Runner`. Put this line of code just below where we created the pirates.

```java
//Runner.java

//AS BEFORE

pirate2.setAge(66);
DBHelper.update(pirate2); //ADDED

//AS BEFORE
```

Now when we run the runner and check the pirate details in debugger, Hector should now be 66.

## Task

Update the title of the ship to `The Flying Dutchman`.

## Delete

So let's delete an pirate. Again Hibernate has a `delete()` method we can call on and, like `save` and `update`, it takes in an object.

```java
  //DBHelper.java

  public static void delete(Object object){
       session = HibernateUtil.getSessionFactory().openSession();
       try {
           transaction = session.beginTransaction();
           session.delete(object);
           transaction.commit();
       } catch (HibernateException e) {
           transaction.rollback();
           e.printStackTrace();
       } finally {
           session.close();
       }
   }
```

And let's try deleting `pirate2` in the runner

```java
// Runner.java

public static void main(String[] args) {
  //AS BEFORE
  Pirate pirate5 = new Pirate("Davey", "Jones", 154);
  DBHelper.save(pirate5);

  DBHelper.delete(pirate2); // ADDED
}

```

Check this in psql terminal and we should only have 1 pirate in the table now.

## List all

Ok so now this is a little but trickier as we are returning specific lists of the class type.

Enter generics...

Generics are used in Java when you want to work on multiple types either using one method or class.

We have actually already seen generics when we use List and HashMap. Have you realised that we can create a List or a HashMap of any type? So `List<Pirate>` and `List<Ship>` work. This is because the class that List implements uses generics so it can create a list of any type.

You can write a single generic method declaration that can be called with arguments of different types. Based on the types of the arguments passed to the generic method, the compiler handles each method call appropriately.

The standard declaration for generic methods is
`public [static] <T> return_type methodName([arguments]){}`

We declare that our method is a generic method by using the notation `<T>` which stands for generic Type.

Then specify a return type which in our case will be a `List<T>`.

We want to return a generic List of either type `List<Pirate>` or `List<Ship>` depending on which table we are querying.

Let's give it a go!

```java
  // DBHelper.java

  public static <T> List<T> getAll(Class classType) {
       session = HibernateUtil.getSessionFactory().openSession();
       List<T> results = null;
       try {
           Criteria cr = session.createCriteria(classType);
           results = cr.list();
       } catch (HibernateException e) {
           e.printStackTrace();
       } finally {
           session.close();
       }
       return results;
   }
```
Now let's get a list of pirates and ships back in the runner and check in debugger.

```java
// Runner.java

public static void main(String[] args) {
  // AS BEFORE

  List<Pirate> pirates = DBHelper.getAll(Pirate.class); // MODIFIED

  List<Ship> ships = DBHelper.getAll(Ship.class); // NEW

} // DEBUGGER
```

Cool, when we check in the debugger we should have 2 lists. One with both pirates and one with just the ship.


## Find by ID

Lastly lets find an object by its ID. Like the `getAll()` method we will use generics to help us out here. We will pass in the id of the object and the classType.

In this method we want to return either a ship or pirate so we will set the return type to `T` to signify that it is a generic type returned and then assign to the appropriate class in the runner.

```java
  //DBHelper.java
  public static <T> T find(Class classType, int id) {
          session = HibernateUtil.getSessionFactory().openSession();
          T result = null;
          try {
              Criteria cr = session.createCriteria(classType);
              cr.add(Restrictions.eq("id", id));
              result = (T) cr.uniqueResult();
          } catch (HibernateException e) {
              e.printStackTrace();
          } finally {
              session.close();
          }
          return result;
      }
```

```java
 //Runner.java

 Pirate foundPirate = DBHelper.find(Pirate.class, pirate1.getId()); // MODIFIED
```

## Task

Find a ship by ID.

Solution:

```java
  // Runner.java

   Ship foundShip = DBHelper.find(Ship.class, ship2.getId());
```


Nice so we have now been able to run all our basic CRUD functions for any class in one `DBHelper` file.

This makes our code very DRY.

We can actually now delete the `save`, `update`, `delete` and `getAll` and `find` methods in `DBPirate` and `DBShip` files as we will no longer need them.

This leaves nothing in the DBShip so we can actually now just delete this file. (If we were doing ship specific queries like getting all pirates for a ship, we would keep it, but we aren't, so we won't)

- Right click DBShip file
- Select Refactor > safe delete

If the class is still being used anywhere in your application IntelliJ will warn you.

We will keep `DBPirate` as we are still using Pirate specific queries like `order by age`, etc.


## Summary

We've seen:
 - How save, update and delete work with objects of any type.
 - How to return a list or object of any type using generics.

Lab: [Criteria Queries Lab](../criteria_queries_lab/criteria_queries_lab.md)

Next Lesson: [Hibernate and Enums](../hibernate_and_enums/hibernate_and_enums.md)
