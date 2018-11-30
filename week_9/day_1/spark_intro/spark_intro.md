# Spark - Introduction

## Objectives

* Know what Spark is
* Set up Spark using Maven
* Create a very simple Spark App using IntelliJ

> If this unit is being taught as a standalone unit, and the students haven't covered Ruby/Sinatra, then it might be worth going through the [HTTP recap](http_recap.md)

## What is Spark?

[Spark](http://sparkjava.com/) is a simple, lightweight web framework which lets us quickly and easily(??) build web applications in Java. It was inspired by the Sinatra framework, which uses Ruby.

## Creating our first Spark app

To create a Spark app we create an IntelliJ project as we have done before. To use the framework in our app we add it to our project as a Maven dependency (like what we did for JUnit). So let's start by creating a Maven project in IntelliJ.

### Creating a Maven Project

> Perhaps get the students to take you through this.

Open IntelliJ

- Select `Create New project`.

- From the left hand menu select `Maven`

Maven provide us with a number of templates, called `archetype`'s depending on what type of application we want to create.

We are not going to use an archetype just yet so leave the checkbox unticked.

- Click `Next`.

We need to assign a `GroupId`. This will identify your project uniquely across all projects, so we need to enforce a naming schema.

- Enter `com.codeclan.example` as GroupId

- Enter `intro_to_spark` as ArtifactId.

- Click `Next`.

- Change location of your project to `<working_directory>/intro_to_spark`

- Click `Finish`

Once project opens it may say 'Maven projects need to be imported.' Click `Import Changes`.

### Adding Spark as a dependency

To use the Spark framework we need to add it as a dependency to our project.

> Perhaps ask the class if they remember which file we are going to need to edit.

So what file are we going to need to edit? That's right, the pom.xml file.
So let's add our new dependency:

* groupId - com.sparkjava
* artifactId - spark-core
* version - 2.7.1

So our pom.xml file should now contain the following:

```xml
<!-- pom.xml -->
<dependencies>
    <dependency>
        <groupId>com.sparkjava</groupId>
        <artifactId>spark-core</artifactId>
        <version>2.7.1</version>
    </dependency>
</dependencies>
```

> You can find the latest version of Spark at [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.sparkjava%22%20AND%20a%3A%22spark-core%22)

### Creating our first Spark application

Applications in Spark Java are built around routes and their handlers. Routes are essential elements in Spark. Every route is made up of three simple pieces - a verb, a path, and a handler.

#### Verb

The verb is a method corresponding to an HTTP method. Verb methods include (among others): `get`, `post`, `put`, `delete`

#### Path

The path (also called a route pattern) determines which URI(s) the route should listen to and provide a response for

#### Handler

The hander is a function that is called for a particular verb and path combination. It is called to generate and return a response to the corresponding HTTP request. The handler function takes two arguments, one for a request object, and another for a response object.

Here is the basic structure for a route that uses the `get` verb:

```java

get("/your-route-path/", (request, response) -> {
    // your handler code
});
```

> Write this on the board.

Explanation:
First of all we use the `get` keyword(or whatever our verb is). This is followed by a set of round brackets i.e. `get()`

Inside the brackets we first add a string for the path we want to match e.g. `get("/hello")`.

Next, inside the brackets after the string we have (in another set of brackets) the arguments for our handler function (i.e. the request and response objects) e.g. `get("/hello", (req, res));`

Finally, we're going to use some syntax you haven't seen before. We are going to use an arrow `->` to point to the code we want our handler function to run. So if we wanted to return the string "Hello World" we would have:

```java

  get("/hello", (req, res)-> { return "Hello world";});
```

> This is called a lambda expression

> The simplest way to describe a lambda expression is as a method that does not have a
> declaration i.e. it does ___not___ have an access modifier (public, private etc), a
> return value declaration, or a name.
>
> The convenience of lambda expressions is that it is a shorthand which lets you write
> a method in the same place you are going to use it.
>
> This is especially useful in places where a method is being used only once, and the
> method definition is short. It saves you the effort of declaring and writing a
> separate method in the containing class..

We can put any valid Java code inside the braces.

## Creating our first route

Create java class

Project Window -> Right-click on `src/main/java` directory and go to `New -> Java Class`

Enter `SparkIntro` in the box

This should create a file called `SparkIntro.java`. We want to run our app from this class so let's add a `main` method to our class:

```java
//SparkIntro.java
public class SparkIntro {
    public static void main(String[] args) {

    }
}
```

We are now ready to add our first route. We are going to add a simple route for a `get` verb and where the path is `/hello`. This will call a handler which simply returns the string "Hello World" which we will be able to see in our browser. We add our route inside our `main` method.

```java
//SparkIntro.java
import static spark.Spark.get;

public class SparkIntro {
    public static void main(String[] args) {
        get("/hello", (req, res)-> { return "Hello world";});
    }
}
```

> NOTE you might have to set the Java version to Java 8 in order to be able to use lambda expressions. Clicking on the red bulb will give you the option to do this automatically.

We can now try running the app. Right-click on the `SparkIntro` file in the `Project` window and choose `Run SparkIntro.main()`. This will start our App running.

> You might get something like the following which you can ignore for now:
> SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
> SLF4J: Defaulting to no-operation (NOP) logger implementation
> SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.

The server runs on port 4567, so got to `http://localhost:4567/hello` in your browser. What do you see?

> in case they ask - Note that localhost is a special url we can use to run applications on our local machine. It's not visible to the outside world. The ip address resolves to 127.0.0.1.

> You'll see that http://127.0.0.1:4567/hello works as well.

> The port number allows us to run multiple applications on our machine if we assign them different ports. It's a specific id which your application uses to listen for inbound requests. Port 443 and Port 80 are generally reserved so that your machine can talk to the Internet for example.

[TASK:] create a route for `randomName` which returns a random string from an array list of strings.

> SOLUTION:

```java
//SparkIntro.java

get ("/random_name", (req, res) -> {
           ArrayList<String> names = new ArrayList<>();
           names.add("Jack");
           names.add("Victor");
           names.add("Isa");
           names.add("Winston");
           names.add("Tam");
           names.add("Naveed");

           Collections.shuffle(names);
           return names.get(0);
       });
```


## Dynamic Input

While interacting with a web site, users need to be able to provide information. One of the ways we can get this is to capture parameters from the path in the URL.

We do this by defining the path with placeholder strings with colons in front of them.  

We get the value associated with the placeholder string in the path by calling the `params()` method on the `request`, passing it our placeholder string

```java

  req.params(placeholder_text);
```

To show this, let's add a new route which is similar to the one we already have, except that instead of just printing "Hello World", we will pass the name of a person as a parameter and the browser will say hello to that person i.e. if we pass the name "Isa", it will now say "Hello Isa".

The placeholder text we will use is `:name`, so our path will look something like
```java

"/hello/:name"
```

The verb we are using is `get`.

```java
  get("/hello/:name", (req, res) -> { });
```

We need to access the text we enter for this parameter so we make a call to `req.params()`, passing it the placeholder text i.e.

```java
  req.params(":name");
```

So our route looks like:

```java
  get("/hello/:name", (req, res) -> { return "Hello " + req.params(":name");});
```

So our class should now look like:

```java
//SparkIntro.java
import static spark.Spark.get;

public class SparkIntro {
    public static void main(String[] args) {
        get("/hello", (req, res)-> { return "Hello world";});

        get("/hello/:name", (req, res) -> { return "Hello " + req.params(":name");});
    }
}
```

NOTE. If you make changes you need to stop and restart your server for the changes to take effect. If you simply click 'Run' in IntelliJ then it will try to start a new instance of the server which will give an error as the port will already by in use.

## Using params to request data

One common use of these URL parameters is to specify a certain piece of data we want to get back. For example, I could have an array of people's names, and want to get a certain name back each time - putting 1 for the first name, 2 for the second, etc.

One thing we have to note is that the parameters are always treated as strings. So if we had something like

`http://localhost:4567/friends/3` then the parameter passed to the `/friends` route would be the string "3", rather than being the number 3. To get the value as a number we'd have to convert it to a number

> Ask the class if they remember how to do this:
> ```java
> Integer.parseInt(req.params(":number"))
> ```

```java
//SparkIntro.java

  get ("/friends/:number", (req, res) -> {
    String [] friends = { "Jack", "Victor", "Winston", "Tam", "Isa", "Naveed" };
    int index = Integer.parseInt(req.params(":number")) - 1;
    return friends[index];
  });
```

## Multiple Parameters

We can also pass multiple parameters via the URL - we just need to separate them with `/` e.g.

```java
//SparkIntro.java

  get ("/add/:num1/:num2", (req, res) -> {
    int num1 = Integer.parseInt(req.params(":num1"));
    int num2 = Integer.parseInt(req.params(":num2"));
    return num1 + num2;
  });
```

> If time allows, get the students to create routes for subtract, multiply, divide.

## Beware of identical routes

If we have two routes that have the same pattern e.g.

```java
  get("/hello/:name", (req, res) -> { return "Hello " + req.params(":name");});

  get("/hello/:mate", (req, res) -> { return "Hello Mate"; });
```

The first one will always be triggered. The second one will never be triggered.

## RECAP
> Instructor note: Ask the class...

* What is Spark?
<details>
<summary>Answer:</summary>
- A lightweight web framework which lets us build web applications in Java.
</details>
<br>

* Which Ruby framework inspired it?
<details>
<summary>Answer:</summary>
- Sinatra
</details>
<br>

* What are the three parts which make up a route in Spark?
<details>
<summary>Answer:</summary>
-  Verb, Path, Handler
</details>
<br>

* How do you pass data to a route in the browser?
<details>
<summary>Answer:</summary>
-  Using the path in the URL
</details>
<br>

* How do you get that data back?
<details>
<summary>Answer:</summary>
- by calling the `params()` method on the request object
</details>
<br>

## Summary

We've seen:

* What Spark is
* How to set up a Java project in IntelliJ to use Spark using Maven
* How to set up a `GET` route in Spark
* That we can pass parameters to routes in the url
* How to access parameters passed to the route in the Java code handling the route
* That we can use parameters to request data.

Spark is really helpful when you want to create web applications using Java quickly.

It can be used to create RESTful apps, with all the RESTful routes.
