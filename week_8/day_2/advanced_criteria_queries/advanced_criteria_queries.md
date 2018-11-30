# Criteria Queries and Native SQL

## Learning Objectives

- Understand Hibernate Criteria Queries.
- Know how to implement clauses in Criteria Queries.
- Run queries using Criteria Queries.

**Lesson Duration: 30 minutes**

## Introduction

We have seen how Criteria Queries help us to perform our `Read` functions on the database. We have also seen how to filter the results using `Restrictions`

Criteria Queries also give us more options to manipulate our results.

## Sorting the Results

> Hand out start point

The Criteria API provides the `org.hibernate.criterion.Order` class to sort your results in either ascending or descending order, according to one of your object's properties.

Lets find the pirates ordered by age, oldest to youngest.

```java
// DBPirate.java

public static List<Pirate> orderByAge(){
  session = HibernateUtil.getSessionFactory().openSession();
  List<Pirate> pirates = null;
  try {
    Criteria cr = session.createCriteria(Pirate.class);
    cr.addOrder(Order.desc("age"));
    pirates = cr.list();
  } catch (HibernateException e) {
    e.printStackTrace();
  } finally {
    session.close();
  }
  return pirates;
}
```

And let's check this in `Runner`

```java
// Runner.java

public static void main(String[] args) {
  // AS BEFORE

  List<Pirate> piratesByAge = DBPirate.orderByAge();
} // DEBUGGER line

```

Now when we check this in debugger the list should be in order. Check this by clicking on each pirate from the list in turn and checking the age.

## Projections & Aggregations

The Criteria API provides the `org.hibernate.criterion.Projections` class, which can be used to get `average`, `maximum`, or `minimum` of the property values. The `Projections` class is similar to the `Restrictions` class, in that it provides several methods for obtaining Projection instances.

Let's find the `average` age.

> The average projection returns a double value from the database.

```java
// DBPirate
public static Double getAverageAge(){
  session = HibernateUtil.getSessionFactory().openSession();
  Double average = null;
  try {
    transaction = session.beginTransaction();
    Criteria cr = session.createCriteria(Pirate.class);
    cr.setProjection(Projections.avg("age"));
    average = (Double)cr.uniqueResult();
  } catch (HibernateException e) {
    transaction.rollback();
    e.printStackTrace();
  } finally {
    session.close();
  }
  return average;
}
```

> Note that the `Average` projection returns a double.

And now we will check this in the `Runner`.

```java
// Runner.java

public static void main(String[] args) {

  double averageAge = DBPirate.getAverageAge();

} // DEBUGGER LINE

```

And we get the average age back!

`Projections` have a number of handy methods:

- To get total row count.
```java
cr.setProjection(Projections.rowCount());
```

- To get average of a property.
```java
cr.setProjection(Projections.avg("age"));
```

- To get distinct count of a property.
```java
cr.setProjection(Projections.countDistinct("firstName"));
```

- To get maximum of a property.
```java
cr.setProjection(Projections.max("age"));
```

- To get minimum of a property.
```java
cr.setProjection(Projections.min("age"));
```

- To get sum of a property.
```java
cr.setProjection(Projections.sum("age"));
```


## Summary

We've seen:
 - How to sort results using Criteria Queries.
 - How to use `Projections`

 Next Lesson: [Refactoring DB with generics](refactor_db_with_generics.md)
