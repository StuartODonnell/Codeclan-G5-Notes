# Introduction to Maven and XML

**Lesson Duration: 45 minutes**

### Learning Objectives
 - Understand how Maven works
 - Create a new maven project
 - Understand how to write XML code
 - Add dependencies to Maven project.

## Introduction

So far we have been creating simple Java projects using Gradle as the build tool to manage our applications dependancies (mainly JUnit).

Build tools are used to compile projects into executable code.

There are 3 major build tools used in Java. `Ant`, `Gradle` and `Maven`

### What does this mean?

Which build tool you use will depend on the needs of your application. If build speed is important to you, Maven may be best. If documentation and support is important, then Gradle could be the way to go. If full control is what you want, then you should lean towards Ant. All three of these are good options, so it really comes down to what is best for you and the application..

### Maven

We are going to use Maven as it is a very useful tool for what we are going to be doing this week and next.

## Creating a Maven Project

Open IntelliJ

To keep things simple we are going to setup Maven to import automatically so it doesn't have to keep asking us.

To avoid having to do this every time lets go to preferences.

- Click `Configure` > `preferences`.
- Expand `Build, Execution, Deployment` and select `Build Tools > Maven`.
- Check the `Import Maven projects automatically` option.
- Click `Ok`.

Now lets create a project


- Select `Create New project`.

- From the left hand menu select `Maven`

Maven provide us with a number of templates, called `archetype`'s depending on what type of application we want to create.

We are not going to use an archetype just yet so leave the checkbox unticked.

- Click `Next`.

We need to assign a `GroupId`. This will identify your project uniquely across all projects, so we need to enforce a naming schema.

- Enter `com.codeclan.example` as GroupId

- Enter `pirate_tracker` as ArtifactId.

- Click `Next`.

- Change location of your project to `codeclan_work/week_8/day_1/pirate_tracker`

- Click `Finish`

Once project opens it may say 'Maven projects need to be imported.' Click `Turn on Auto Import`.

The project will open up an XML file called pom.xml. (Tab will show name of project but pom.xml in project pane on left, don't know why!).

You are now looking at XML.... looks familiar doesn't it?

## XML

### What is XML?

- XML stands for `EXtensible Markup Language`
- XML is a markup language much like `HTML`.
- XML was designed to describe data.
- XML tags are not predefined in XML. You must define your own tags.

### The main difference between XML and HTML

XML and HTML are similar but were designed with different aims:

XML was designed to describe data and to focus on what data is. HTML was designed to display data and to focus on how data looks.

HTML is about displaying information, XML is about describing information.

### Adding dependencies to XML.

Dependencies are tools that we can use in our Java application. For example JUnit, Postgres, Hibernate, etc.

To be able to use any of these we need to add a tag for them in our pom.xml so that Maven can fetch the files from it's repository that will allow us to use them.

Lets add JUnit for just now.

```xml
#pom.xml
...
 <version>1.0-SNAPSHOT</version>

<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.8.1</version>
      <scope>test</scope>
    </dependency>
</dependencies>

```

So we can now use JUnit. Lets create something to test.

## MVC

We will be using the MVC pattern moving forward so let's create a package called `models` under the main package.

- Right Click `main > java`
- Select `new` > `package`
- Call it `models`

Right click the `models` package and create a new class called `Pirate`.

## Pirate System

Lets create a test file for an Pirate class under `src/test/java` called `PirateTest`

We will give our pirate a first name, last name and age.

```java
// PirateTest.java
import models.Pirate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PirateTest {

  Pirate pirate;

  @Before
  public void before(){
    pirate = new Pirate("Jack", "Sparrow", 32);
  }

  @Test
  public void hasFirstName(){
    assertEquals("Jack", pirate.getFirstName());
  }

  @Test
  public void hasLastName(){
    assertEquals("Sparrow", pirate.getLastName());
  }

  @Test
  public void hasAge(){
    assertEquals(32, pirate.getAge());
  }


}
```

Go ahead and code the `Pirate` class and get the tests passing.

Run the tests and make sure they pass.

#### Solution


```java
// Pirate.java
package models;

public class Pirate {

  private String firstName;
  private String lastName;
  private int age;

  public Pirate(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

}
```

Excellent, we have just created our first maven project.

## Summary

We've seen:
 - What a build tool is.
 - How to setup a Maven project.
 - What XML is and how it differs from HTML.
 - Adding dependencies to project using XML.
 - How to write, compile and run a Maven program.

As we go through the next two weeks you will get to see more and more features of Maven and XML and understand how these can be very powerful tools for developers.


Next Lesson: [Intro to Hibernate](../intro_to_hibernate/intro_to_hibernate.md)
