# Introduction to Hibernate

**Lesson Duration: 45 minutes**

### Learning Objectives

- Understand what an ORM is
- Understand how hibernate works
- Add Hibernate as a dependancy.
- Create a new session


## Introduction

`ORM` stands for Object-Relational Mapping and is a programming technique for converting data between relational databases and object oriented programming languages such as Java, C#, etc.

An ORM system has the following advantages over plain JDBC −

- Let’s business code access objects rather than DB tables.
-	Hides details of SQL queries from OO logic.
-	No need to deal with the database implementation.
-	Entities based on business concepts rather than database structure.
-	Fast development of application.

An ORM solution consists of the following four entities −

- An API to perform basic CRUD operations on objects of persistent classes.
-	A language or API to specify queries that refer to classes and properties of classes.
-	A configurable facility for specifying mapping metadata.


### Java ORM Frameworks

There are a number of ORM options in Java.

- Enterprise JavaBeans Entity Beans
- Java Data Objects
- Castor
- TopLink
- Spring DAO
- Hibernate
- And many more

## Hibernate

Hibernate is the most popular ORM used for Java applications.

How Hibernate works is it maps Java classes to database tables and from Java data types to SQL data types.
This is very useful as it takes a lot of work away from the developer in terms of having to create tables in SQL and relate the different data types depending on what version of SQL is used. Hibernate will map the correct types based on which database you are using.

Hibernate lies between our Java objects and database.

![](hibernate_position.jpg)

### Hibernate advantages

- Hibernate maps Java classes to database tables using XML files or annotations without the developer having to write any SQL code.

- Provides a collection of useful methods for saving and retrieving Java objects directly to and from the database.

- If you want to change the database structure by amending or adding classes, then you need to change the mapping file properties only.

- Upon retrieving data from the database, Hibernate will map the rows back to the relevant java objects.

## First Hibernate app.

> Hand out starter code or use end point from Intro to Maven and XML.

Here we have our basic pirate tracker.

We will start by using hibernate and map the Pirate class to our database.  

We are going to be using Hibernate and Postgresql. We also need to add a dependancy called `Javassist`. Hibernate currently uses `Javassist` to implement classes at runtime. Basically speaking this allows Java to turn the data that hibernate returns from the database into the Java objects.

So lets start by adding these as dependencies in our pom.xml

```xml
<!-- pom.xml -->

<dependencies>
   <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>4.3.5.Final</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>9.4.1212</version>
    </dependency>

    <dependency>
           <groupId>javassist</groupId>
           <artifactId>javassist</artifactId>
           <version>3.12.1.GA</version>
       </dependency>
</dependencies>
```

Ok so let's start hibernating!

## Hibernate Configuration

To allow Hibernate to work we need to create a configuration object. This defines the properties that hibernate will use to control and connect to the database.

We need to define at least 2 common properties:

- Database Connection − Details of the database connection including the url of the database, username, password etc.

- Class Mapping Setup − This provides the details to create the link between the Java classes and database tables.

This configuration is always stored in a file called hibernate.cfg.xml located in the resources folder.

Lets create this file.

- Right click resources folder
- Select new > file
- Name the file hibernate.cfg.xml

```xml
<!-- hibernate.cfg.xml -->

<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

</hibernate-configuration>
```

## Session Factory

We need to set up a session-factory to allow Hibernate to connect to our database.

You need one SessionFactory object per database. If using multiple databases then we would use separate configuration files to create multiple SessionFactory objects.

Here we specify:

**hibernate.dialect**

- This tells Hibernate which version of SQL to use depending on the database.

**hibernate.connection.driver_class**

- This specifies what hibnertae will use to connect. In our case we will use the JDBC (Java DataBase Connection) class.

**hibernate.connection.url**

- The database URL.

#### Optional

**hbm2ddl.auto**

- Tells hibernate what do do with the tables each time a SessionFactory is created.
- Examples of this could be `create`, `create-drop`, `update`, `validate`.

> We will use create to recreate the schema each time as we will be making a lot of changes to tables through the week.

**hibernate.connection.username**

- The database username.

**hibernate.connection.password**

- The database password.

**hibernate.connection.pool_size**

- Limits the number of connections Hibernate allows to the database.

**hibernate.show_sql**

- Allows us to see the SQL happening under the hood at runtime and keep a log of all transactions.

Lets set up the configuration we will need.

```xml
<!-- hibernate.cfg.xml -->

<hibernate-configuration>
    <session-factory>
        <property name="hbm2ddl.auto">create</property>
        <property name="connection.driver_class">org.postgresql.Driver</property>
        <property name="connection.url">jdbc:postgresql://localhost/piratedb</property>
        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQL9Dialect</property>
        <property name="connection.pool_size">1</property>
        <property name="show_sql">true</property>
    </session-factory>
</hibernate-configuration>
```


## Creating a new session.

Well we need to set up a class to handle connecting to the database and to manage the session.

Lets create a new `SessionFactory` and use this to handle our hibernate transactions.

We will do this in the `HibernateUtil` class in a new package called `db`

- Right click `main > java`
- Select new > package
- Call the package db

- Right click on `db` package
- Select new > Java class
- Call the class `HibernateUtil`

There are number of methods provided by the Session interface, but we're just going to use .buildSessionFactory().

>You can check Hibernate documentation for a complete list of methods associated with Session and SessionFactory.

- Connection.configure().buildSessionFactory()

Creates the SessionFactory based on our hibernate.cfg.xml.
If an error occurs this method will throw it back up to parent rather than catching it. We don't really need to worry about  exactly what this is doing at the moment.

```java
// HibernateUtil.java
package db;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```

Now we will be able to create a new session to perform the actions on our database.

in the next lesson we will map our classes to allow us to create our tables.

## Summary

We've seen:
 - What an ORM is.
 - Advantages of Hibernate.
 - Setting up a Hibernate project
 - Creating a new Session


Hibernate is a really powerful tool when it comes to persisting Object Orientated applications.

Hibernate will also deal with relationships like one-many and many-many, inheritance and mapping collections.

Now let's have a look at how we map the classes to database tables,

Next Lesson: [Intro to Annotations](../intro_to_annotations/intro_to_annotations.md)
