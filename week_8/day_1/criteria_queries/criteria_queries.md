# Criteria Queries and Native SQL

**Lesson duration 1 hour**

### Learning Objectives

- Understand Hibernate Criteria Queries.
- Run queries using Criteria Queries.

## Introduction

Criteria Queries provide a flexible way to query databases and return results.

There are a lot of methods available from the Criteria Query class which allow you to filter and add more options to your queries.

We will use a `createCriteria()` method, to create a Criteria object. Which in turn will be used to query the database.

## Creating a simple Criteria Query

With `Critera Queries` we can actually specify inside these queries which class to use.

Let's have a look at this.

> Hand out start point

We will start by getting all pirates back.

```java
// Runner.java

// AS BEFORE

public static void main(String[] args) {
  // AS BEFORE
  List<Pirate> pirates = DBPirate.getAll(); // ADDED

} // DEBUGGER line
```

We use List here as this is what the hibernate query will return. List is just the interface that is used for any type of Java list (ArrayList, SortedList, etc). It behaves exactly the same way as an ArrayList and has all the usual methods we are used to.)

So we have this method started in `DBPirate` let's go ahead and uncomment this and complete it.

> Note that we don't need a transaction in this method as we are not modifying the table in any way.

First of all we will create a list to hold our results of query and return it. We will set it to `null` otherwise Java will complain that it may not have been initialised.

```java
//DBPirate.java

public static List<Pirate> getAll()
{
  List<Pirate> results = null; // ADDED
  session = HibernateUtil.getSessionFactory().openSession();
  try{

  } catch (HibernateException e){
    e.printStackTrace();
  } finally {
    session.close();
  }
  return results; // ADDED
}
```

To create the Criteria Query we use the session and call the `createCriteria()` method and pass in the class. We pass in the class to tell the Criteria which table to query. So now the query will run on the `pirates` table only.

```java
// DBPirate.java

public static List<Pirate> getAll()
{
  List<Pirate> results = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try{
    Criteria cr = session.createCriteria(Pirate.class); // ADDED
  } catch (HibernateException e){
    e.printStackTrace();
  } finally {
    session.close();
  }
  return results;
}
```

Hibernate queries have a `.list()` method that returns a list of Pirate objects. So no need for us to map over result sets ourselves. Neat huh?

```java
// DBPirate.java

public static List<Pirate> getAll()
{
  List<Pirate> results = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try{
    Criteria cr = session.createCriteria(Pirate.class);
    results = cr.list(); // ADDED
  } catch (HibernateException e){
    e.printStackTrace();
  } finally {
    session.close();
  }
  return results;
}
```

Run this and check it out in debugger.

Awesome, we can now get our pirates back as a list without having to write any SQL either.

## Finding one pirate by ID.

We can also set `restrictions` on criteria queries. Similar to the clauses we used in SQL. (`WHERE`, `LIKE`, etc).

We will use this to get an pirate by their `ID`.

We will start off by creating the method and a Criteria as we did above. We also only want one pirate to be returned so we will create a `null` pirate initially.

```java
// DBPirate.java

public static Pirate find(int id) {
  Pirate result = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try {
    Criteria cr = session.createCriteria(Pirate.class);
  } catch (HibernateException e) {
    e.printStackTrace();
  } finally {
    session.close();
  }
  return result;
}
```

So now we will add a restriction to the Criteria using the `add()` method and `Restrictions.eq()` (which is the same as saying `WHERE id = id` in SQL)

We want to match the `id` property value to the `id` passed into the method.



```java
// DBPirate.java

public static Pirate find(int id) {
  Pirate result = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try {
    Criteria cr = session.createCriteria(Pirate.class);
    cr.add(Restrictions.eq("id", id));
  } catch (HibernateException e) {
    e.printStackTrace();
  } finally {
    session.close();
  }
  return result;
}
```

> Note that the restriction should be on the java property not the database column. So if we were trying to find by name it would be eq("firstName") not eq("first_name")

And lastly we want to get back the one result that matches that ID. We could use `.list()` and then return the first value but hibernate gives us a `uniqueResult()` method that will just return one pirate object.

`uniqueResult()` however isn't that clever and just returns a type `Object`. In order to tell Java that it is a pirate we will have to cast the object that is returned to a pirate.

```java
// DBPirate.java

public static Pirate find(int id) {
  Pirate result = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try {
    Criteria cr = session.createCriteria(Pirate.class);
    cr.add(Restrictions.eq("id", id));
    result = (Pirate)cr.uniqueResult();
  } catch (HibernateException e) {
    e.printStackTrace();
  } finally {
    session.close();
  }
  return result;
}
```

Let's check that we can find Davey Jones.

```java
// Runner.java
public static void main(String[] args) {

// AS BEFORE

Pirate davey = DBPirate.find(pirate5.getId());

}// DEBUGGER line
```

Now if we debug we should see that we get `Davey Jones` back. Sweet!

There are a number of restrictions we can apply including:

- To get records having age more than 25
```java
cr.add(Restrictions.gt("age", 25));
```

- To get records having age less than 25
```java
cr.add(Restrictions.lt("age", 25));
```

- To get records having fistName starting with jac
```java
cr.add(Restrictions.like("firstName", "jac%"));
```

- Case sensitive form of the above restriction.
```java
cr.add(Restrictions.ilike("firstName", "Jac%"));
```

- To get records having age in between 25 and 35
```java
cr.add(Restrictions.between("age", 25, 35));
```

and many more.


## Adding more functionality

Note that we can still have other methods in these classes if we wanted to. The only condition is that these methods cannot start with `get` or `set`. otherwise hibernate will think there is a property that hasn't been mapped.

Let's add a talk method to the pirate.

```java
// Pirate.java

public class Pirate {

  // AS Before

  public String talk(){
    return "Where has all the Rum gone?"
  }
}
```

And try it in the runner.

```java
// Runner.java

public static void main(String[] args) {

  // As BEFORE

  String pirateTalk = davey.talk();
}
```

Check this out in debugger.

> Note that normally we would TDD any methods that are not DB related.

## Summary

We've seen:
 - What `Criteria Queries` are.
 - How to execute a simple query.
 - How to execute a more detailed query using `Restrictions`.
 - How to add more methods to classes.

 Lab : [Golfer Lab](../hibernate_lab/hibernate_lab.md)
