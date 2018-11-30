# Velocity - Intro

## Objectives

* Know what Velocity Template engine is
* Be able to use Velocity in a Java Spark Project
* Know how to create a template
* Passing a variable to our template
* Be able to render html from a template

# Introduction

So far we've been able to create a simple web app with routes we can pass via a URL. We're now going to start creating a simple MVC app, for a simple calculator.

> If this is being taught as a standalone module, go over [MVC](mvc.md)

> Give out start code and let students read through it for a few minutes (this is the completed code from the previous lession - with all the routes)
> 
> If the students get an error like ```Unable to import maven project: See logs for details``` then here are the steps to get around this:
> - Close the project
> - In the IntelliJ welcome screen select `Import Project`
> - Navigate to the directory containing the project and click on `Open`
> - Select `Import project from external model and select `Maven` and click  `Next`
> - Check the `Import Maven Projects automatically box` and click `Next`
> - In the next two screens click `Next` without making any changes
> - Enter your chosen Project Names and Directory and click `Next`
> 
> If they try to run the tests then they MIGHT get and error about the working directory being missing.  This can be fixed by going to  `Run > Edit Configurations` and then clicking on the `...`` and selecting the current working directory/project folder.

## Serving content

We currently have a functional app which works as a calculator. But what are we actually sending to the view?

We are actually sending a string down just now. If we `cmd-alt-u` and view the source of our page you will notice there is no HTML. As we are in control of what we send we dictate what we send to the client.  As mentioned, servers can send down loads of types of data. On the web, one particular type of data is more common than any other.

### HTML

Everyday, we will all be requesting this file type without probably even knowing we are. HTML is sent back from servers to users to display and structure content. So, it's important we know how to send HTML.

> DRAW -  request comes in - processes request and gets data and then generates HTML and sends down:

1. Request comes in
2. Grab resource (data from database/process information)
3. Inject to html
4. Page sent down as HTML.

There are many ways to create HTML. We could create a `.html` file and send this but because we are building dynamic apps we probably want to add content to our pages dynamically. One way to do this would be to write Java code which creates strings for the html we want to generate. This is time consuming (and messy). A better way is to use a template engine. A template engine is a library which is used to generate text output (HTML web pages, e-mails, configuration files, source code, etc.) based on templates and changing data. ... 

There are various template engines we can use with our Java code (e.g. Apache Velocity, Apache FreeMarker, Jade, JetBrick, Thymeleaf, Pebble). The one we are going to use is [Apache Velocity](http://velocity.apache.org/)

## What is Velocity?

Velocity is a Java-based templating engine. It is an open source web framework and is designed to be used as a view component in the MVC architecture.

Velocity can be used to generate XML files, SQL, PostScript and most other text-based formats, but we are going to use it to generate HTML pages dynamically.

## Adding the dependencies

We add Velocity to our project just like any other dependency i.e. in the pom.xml file. When using Velocity we add two dependencies, `velocity`,  and `spark-template-velocity` (which allows us to use Velocity templates with Spark):

```xml
<!-- pom.xml -->
<dependencies>
    <!-- AS BEFORE -->
    <dependency>
        <groupId>org.apache.velocity</groupId>
        <artifactId>velocity</artifactId>
        <version>1.7</version>
    </dependency>
    
    <dependency>
        <groupId>com.sparkjava</groupId> 
        <artifactId>spark-template-velocity</artifactId> 
        <version>2.7.1</version> 
    </dependency>
</dependencies>

```

> We can also add the `velocity-tools` (which allow us to use the Velocity templating engine) dependency (see [here](http://www.baeldung.com/apache-velocity)), though in some cases this can cause errors
>
```xml
<!-- pom.xml -->
<dependencies>
    <!-- AS BEFORE -->
    <dependency>
         <groupId>org.apache.velocity</groupId>
         <artifactId>velocity-tools</artifactId>
         <version>2.0</version>
    </dependency>
</dependencies>

```


The latest version of velocity and velocity-tools can be found here: [velocity](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.apache.velocity%22%20AND%20a%3A%22velocity%22) and [velocity-tools](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.apache.velocity%22%20AND%20a%3A%22velocity-tools%22).

## Creating a Template

Since we want to generate HTML to send back to the client, we need to create a template file, from which the template engine will generate the html. Template files go in the `resources` directory, as this is where the template engine expects them to be. Since we are using the Velocity Template engine, then rather than a `.html` file extension, our template files will have a `.vtl` extension. 

> Perhaps it is worth stressing that our template files are NOT html files, they are files used to generate html

Our template files are kept in a `resources` directory so we need to create one:

* Right-click on `src/main/` and select New->Directory
* In the 'Enter new directory name:' dialog box type `resources` and click 'OK' 

So let's create our first template file, one to show us the result from a calculation:

* Right-click on `src/main/resources` and select New->File
* In the 'New File' dialog box type `result.vtl` and click 'OK' 

Our template file can contain html code so let's add some:

```html
<!-- result.vtl -->

<h3> The result is </h3>
```

As well as html code, we can also reference Java variables in our template file. We need to use a special syntax for this. Whenever we reference a Java variable we need to put a dollar sign ('$') in front of the variable i.e. if we wanted to reference a variable called `result` we would add `$result` in our file:

```html
<!-- result.vtl -->

<h3> The result is $result </h3>
```

We can also include Java code in our templates (more about that later)

The velocity template file syntax can be found [here](http://velocity.apache.org/engine/1.7/vtl-reference.html)

## Generating html using our template 

We generate html from our template using the template engine. In our example, whenever we do a calculation we want to get the result of the calculation, pass it to the template engine, along with our template, and generate html which is then rendered in our browser. 

So how do we pass variables to our template files? Spark has a tool called `ModelAndView` which allows us to pass dynamic information, like variables, from our java code (in thi case `Controller.java`) to our template files. To use this tool we need to import it into our java code:

```java
//Controller.java

import spark.ModelAndView; //ADDED
import models.Calculator;

import static spark.Spark.get;

public class Controller {
  //AS BEFORE
}
```

Spark also has a template engine adapter for use specifically with Velocity. We also need to import this into our code

```java
//Controller.java

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine; //ADDED
import models.Calculator;

import static spark.Spark.get;

public class Controller {
  //AS BEFORE
}
```

> You might need to add the Maven dependency

So now we're ready to modify our code to generate the html. 

As we are using the Velocity Template Engine, we need to pass an instance of this to our routes. We can do this at the start of our `main` method, before all our routes:

```java
//Controller.java

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine; //ADDED
import models.Calculator;

import static spark.Spark.get;

public class Controller {
  public static void main(String[] args) {
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine(); //ADDED
  }
}
```

This new `velocityTemplateEngine` is now used as a third argument passed to our `GET` route:

```java
//Controller.java

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine; //ADDED
import models.Calculator;

import static spark.Spark.get;

public class Controller {
  public static void main(String[] args) {
    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    get("/add/:num1/:num2", (req, res) -> {
                Integer number1 = Integer.parseInt(req.params(":num1"));
                Integer number2 = Integer.parseInt(req.params(":num2"));
                Calculator calculator = new Calculator(number1, number2);
                int result = calculator.add();
                return result;
            }, velocityTemplateEngine);  //MODIFIED
  }
}
```

### Passing a variable to our template

In order to pass variables from our Java code to our template we need to create a model which is then passed to the template. This model takes the form of a Java `HashMap`, where the key is the name of the Java variable you access in the template, and the value is the variable you wish to pass through. The key of the model is of type `String` and the value is of type `Object` i.e. we can pass an object of any class as value

```java
  Map<String, Object>
```

So let's add a hashmap for the model to our route:

```java
//Controller.java

      get("/add/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.add();
            HashMap<String, Object> model = new HashMap<>(); //ADDED
            return result;
        }, velocityTemplateEngine);
```

We now need to add an entry to our hashmap for the result we want to pass to the template. So what is the key going to be. Going back to our template we see that we access a variable called `$result`, therefore our key is going to be "result".

```html
<!-- result.vtl -->

<h3> The result is $result </h3>
```

The value we are going to use is the value of our `result` variable i.e.:

> Ask the class if they remember how to add an item to a hashMap

```java
  model.put("result", result);
```

> We can add as many items to our hash as we like, depending on the number of Java objects we want to access in our template

```java
//Controller.java

      get("/add/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.add();
            Map<String, Object> model = new HashMap<>();
            model.put("result", result);  //ADDED
            return result;
        }, velocityTemplateEngine);
```

### Rendering the proper template

At the moment we are still returning the value in our `result` variable. We still need to tell our route that we want to render our result template with the model we have created. Spark has a class called `ModelAndView` which is used to set the name of the view(template file) and the model object we want to be rendered. 

```java

return new ModelAndView(<model>, <templatefile>);
```

In our case the model is our `model` hashmap, and the view is our `result.vtl` template file. So we need to create a new instance of `ModelAndView` which takes these two arguments, our model i.e. `model` and the name of the template file i.e. `result.vtl` which is passed as a string i.e.:

```java
//Controller.java

      get("/add/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.add();
            Map<String, Object> model = new HashMap<>();
            model.put("result", result);
            return new ModelAndView(model, "result.vtl");
        }, velocityTemplateEngine);
```

Run the app and enter call the add route using something like `localhost:4567/add/30/50`. What do you see?

#### Task

Modify the other routes (subtract, multiply, divide) to display the result 

SOLUTION:
```java
//Controller.java

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import models.Calculator;

import java.util.HashMap;

import static spark.Spark.get;

public class Controller {
    public static void main(String[] args) {

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/add/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.add();
            HashMap<String, Object> model = new HashMap<>();
            model.put("result", result);
            return new ModelAndView(model, "result.vtl");
        }, velocityTemplateEngine);

        get("/subtract/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.subtract();
            HashMap<String, Object> model = new HashMap<>();
            model.put("result", result);
            return new ModelAndView(model, "result.vtl");
        }, velocityTemplateEngine);

        get("/multiply/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            int result = calculator.multiply();
            HashMap<String, Object> model = new HashMap<>();
            model.put("result", result);
            return new ModelAndView(model, "result.vtl");
        }, velocityTemplateEngine);

        get("/divide/:num1/:num2", (req, res) -> {
            Integer number1 = Integer.parseInt(req.params(":num1"));
            Integer number2 = Integer.parseInt(req.params(":num2"));
            Calculator calculator = new Calculator(number1, number2);
            double result = calculator.divide();
            HashMap<String, Object> model = new HashMap<>();
            model.put("result", result);
            return new ModelAndView(model, "result.vtl");
        }, velocityTemplateEngine);
    }
}
```

## Summary

What we've covered:

* What Velocity Template engine is
* How to add Velocity to a Java Spark Project
* How to create a template(.vtl file)
* How to pass a variable from Java to a template
* How to render html generated using a template file.
