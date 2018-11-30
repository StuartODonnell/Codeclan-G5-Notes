# Querying Many To Many Tables

## Learning Objectives

- Know how to create an Alias
- Know how to use Alias to query multiple tables.

## Getting a list of pirates.

Let's check that we can get a list of pirates back from the raid.

For this we will create a method in the `DBRaid` file to query the database for all pirates attached to a raid.

With a Many-To_Many relationship this will need to be done a little bit differently.

If we were doing this in SQL we would create a JOIN query like:

```SQL
SELECT * FROM pirates INNER JOIN pirate_raid ON pirate.id = pirate_raid.pirate_id WHERE pirate_raid.raid_id = 1;
  ```

Quite the mouthful and if we were using the old HQL or Native SQL queries we would have to type all this out.

## Criteria Query Alias method.

Criteria queries on the other hand have something called an `createAlias()` method that allows us to link our `Pirates` and `Raids` via the `pirate_raid` table based on the raid id.

Here's how.....

Start off our normal query method to return a list of pirates and pass it a raid.

We will create a DBRaid file to handle this.

- db > new > Java Class > `DBRaid`

```java
// DBRaid.Java

private static Session session;

public static List<Pirate> getRaidPirates(Raid raid){
  List<Pirate> results = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try {
    Criteria cr = session.createCriteria(Pirate.class);

    results = cr.list();
  } catch (HibernateException ex){
    ex.printStackTrace();
  } finally {
    session.close();
  }
  return results;

}

```

Now we need to add our Alias code to the Criteria.

We want to match pirates on the raid so we will create an Alias to use when we are checking each of the pirates raids.

This is a bit like adding the placeholder in our for loop. We want to go through each pirates `raids` set and for each raid check if it matches the one we have passed into the method.

We will then add a restriction that says add this pirate to the results if they have a raid where the id matches the raid we are querying.


```java
// DBRaid.java

public static List<Pirate> getRaidPirates(Raid raid){
  List<Pirate> results = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try {
    Criteria cr = session.createCriteria(Pirate.class);
    cr.createAlias("raids", "raid"); // ADDED
    cr.add(Restrictions.eq("raid.id", raid.getId())); // ADDED
    results = cr.list();
  } catch (HibernateException ex){
    ex.printStackTrace();
  } finally {
    session.close();
  }
  return results;

}

```

Now in the `Runner` file we will run this query.


```java
// Runner.java

List<Pirate> pirates = DBRaid.getRaidPirates(raid1);

```

Now debug this and there they are.

## Task

In the `DBPirate` file write a method to get a list of all `raids` for an pirate.


## Solution

```java
// DBPirate.java

public static List<Raid> getPirateRaids(Pirate pirate){
  List<Raid> results = null;
  session = HibernateUtil.getSessionFactory().openSession();
  try {
    Criteria cr = session.createCriteria(Raid.class);
    cr.createAlias("pirates", "pirate"); // ADDED
    cr.add(Restrictions.eq("pirate.id", pirate.getId())); // ADDED
    results = cr.list();
  } catch (HibernateException ex){
    ex.printStackTrace();
  } finally {
    session.close();
  }
  return results;

}
```

```java
// Runner.Java

 List<Raid> raids = DBPirate.getPirateRaids(pirate1);
```

## Summary

We can add multiple aliases to Criteria Queries so if we needed to query 3 or 4 tables we just add an alias for each table we want to use.

We've seen:
 - How to create an Alias
 - How to use Alias to query multiple tables.
