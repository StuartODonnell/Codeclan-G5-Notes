# Spark With Hibernate and Velocity

## Objective

* Consolidate what we've learned over the past week

## Intro

So over the last week we've been introduced to using Hibernate to interact with a PSQL database and to Spark and Velocity to create simple web apps in Java. Today we're going to put everything together to create a web app in Java which interacts with a PSQL database.

> Give out start code and get the students to look at it for about 5 minutes to familiarise themselves with the Hibernate stuff.

You'll see from the start code that this is the same as the end point from the inheritance lesson last week.

We are going to use this code to build a simple web app, where we can perform all the CRUD operations using a web page.

## Adding the dependencies

Before we do anything, we need to add the relevant dependencies to our `pom.xml` file. The Hibernate dependencies are already there so we just need to add the dependencies for Spark and the Velocity Template Engine.

### Spark

Let's add the Spark dependency first:

```xml
<!-- pom.xml -->
        <dependency>
            <groupId>com.sparkjava</groupId>
            <artifactId>spark-core</artifactId>
            <version>2.2</version>
        </dependency>
```

### Velocity

Now let's add the dependencies to allow us to use the Velocity Template Engine with Spark:

```xml
<!-- pom.xml -->

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
```

## The Plan

So now that we're all set up let's think about what we want to show when our web app starts up. What about showing a list of all the employees, with their name and department.

So what are we going to need?

> Perhaps give the students a chance to think about it.

Well we're going to need to create a route in Spark. We'll do this inside a controller class, called `EmployeesController`.

We'll also need to create a template so that we can generate the html to be rendered in out browser.

## Creating the Employees controller

So let's create our Employees Controller. First of all let's create a new package in our main `java` package. Right click on the `java` directory in the 'Project' window and select `New -> Package`. Enter 'controllers' in the text box and click `OK`.

Now we can create our class. Right click on the `controllers` directory in the 'Project' window and select `New -> Java Class`. Enter `EmployeesController` for the name and click `OK`.

We're going to use this class as the entry point for our app, so what do we need to add? Yes, a `main` method.

```java
public class EmployeesController {

    public static void main(String[] args) {

    }

}
```

We can now create our route inside our `main` method. Let's create a `GET` route `/employees` which outputs to a template called `templates/employees/index.vtl` and uses a layout file `templates/layout.vtl`

> Perhaps get students to do this for themselves

```java
public class EmployeesController {

    public static void main(String[] args) {
      get("/employees", (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("template","templates/employees/index.vtl");

        return new ModelAndView(model, "templates/layout.vtl");
      }, new VelocityTemplateEngine());
    }

}
```

> You might need to import the relevant classes


At moment, all our route would do is simply render a page, but we want to show all the employees. Therefore, we need to get all the entries from the `employee` table. So how do we do this?

> Ask the class if they remember how to do this.

We can use the `getAll()` method in our `DBHelper` class, passing it  the class of the model we are looking for, in this case, "Employee". This should return a `List` of `Employee` objects which we can store in a variable e.g.:

```java
  List <Employee> employees  = DBHelper.getAll(Employee.class);
```

We can then need to pass this list of `Employee` objects to our template, so that we can render it. Can you remember from yesterday how to do this? Yes, we can add it to the `model` HashMap, giving it an appropriate key e.g.:

```java
  model.put("employees", employees);
```

So now our controller should look something like:

```java
//EmployeesController.java

public class EmployeesController {

    public static void main(String[] args) {

        get("/employees", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Employee> employees = DBHelper.getAll(Employee.class); //NEW
            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees); //NEW

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }

}

```

## Creating the layout file

We're now going to create the layout file which will be used by all our templates. First of all, we need to create a new directory `templates` in `resources` directory.

Right click on the `resources` directory in the 'Project' window and select `New -> Directory`. Enter 'templates' in the text box and click `OK`.

We then need to create our layout file in this directory. Right click on the `resources/templates` directory in the 'Project' window and select `New -> File`. Enter 'layout.vtl' in the text box and click `OK`.

We now need to add the template code for our layout file.

> Maybe send this out via Slack to save time

```html
<!-- layout.vtl -->

<!DOCTYPE html>
<html>
  <head>
    <title>Employee Records</title>
  </head>
  <body>
    <!-- begin inner template-->
    <div class="container">
      #parse( $template )
    </div>
    <!-- end inner template-->
  </body>
</html>
```

## Creating the template file

We're now need to create the template file which will be used to render our list of employees. Following the RESTful routes we're going to call this `index.vtl` and place it within a directory called `employees` which will be located within our `templates` directory.

First we need to create the directory. Right click on the `templates` directory in the 'Project' window and select `New -> Directory`. Enter 'employees' in the text box and click `OK`.

We then need to create our template file in this directory. Right click on the `resources/templates/employees` directory in the 'Project' window and select `New -> File`. Enter 'index.vtl' in the text box and click `OK`.

So, thinking about what we want this template file to render, we want to show all our employees, with their first name, last name and department. So how could we do this? We could use a table, where each row corresponds to an individual employee.

So let's start by creating the outline of our table

```html
<!-- templates/employees/index.vtl -->
<p class="pageHeader">All Employees</p>

<div class="container">
    <table>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
        </tr>
    </table>
</div>
```

We now need to think about the rows in our table?  What do we want to show? We might want to display
  * First Name
  * Last Name
  * Department Name

So if each row applies to an `Employee` object how to we get to this information in our template file? Well, we could have a variable called `$employee` which is an `Employee` and call the `getFirstName()` and `getLastName()` methods to get the name() e.g.:

```html
  <tr>
    <td>$employee.getFirstName()</td>
    <td>$employee.getLastName()</td>
  <tr>
```
But how do we get the department name? If we call `getDepartment()` on an `Employee` object we get a `Department` object back. But then to get the name of the department, we can call the `getTitle()` method on the `Department` object returned e.g.:

```html
  <tr>
    <td>$employee.getFirstName()</td>
    <td>$employee.getLastName()</td>
    <td>$employee.getDepartment().getTitle()</td>
  <tr>
```

So that's us got the details for a single `Employee`. But we are passing in a list of employees. So how do we create a row in the table for every employee? We can use the `#foreach` loop that Velocity gives use to loop through the list of employees we pass in and create a new table row for each one e.g.:

```html
#foreach($employee in $employees )
  <tr>
  </tr>
#end
```

So our template should now look something like:

```html
<!-- templates/employees/index.vtl -->
<p class="pageHeader">All Employees</p>

<div class="container">
    <table>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
        </tr>
            #foreach($employee in $employees )
                <tr>
                    <td>$employee.getFirstName()</td>
                    <td>$employee.getLastName()</td>
                    <td>$employee.getDepartment().getTitle()</td>
                <tr>
            #end
    </table>
</div>
```

Try running the app, by right-clicking on `EmployeesController` in the 'Project' window and selecting `Run 'EmployeesController.main()'` and then open your browser at `localhost:4567/employees`. What do you see? An empty table. WHAT! That's because we have no data in our database. So let's create some seed data so that the Database is always seeded when the app is run.

## Adding Some Seed Data

So let's create a way so that when the app starts, we have some data in the database. We'll start by creating a class in the `db` package called `Seeds`

This class is going to have one method, called `seedData`. It will be a class method so will be declared as `static` i.e.:

```java
//Seeds.Java
public class Seeds {
    public static void seedData() {

    }
}
```
Due to the fact that we will have to restart our server we don't want to have to drop our table each tie as we would have to then run seeds file constantly.
Let's change this.


We'll start by deleting all the entries in all our tables, using the `deleteAll()` method in our `DBHelper` class. We will need to call it for each of the tables in our database i.e.:

```java
//Seeds.Java
public class Seeds {
    public static void seedData() {
      DBHelper.deleteAll(Engineer.class); //NEW
      DBHelper.deleteAll(Employee.class); //NEW
      DBHelper.deleteAll(Manager.class);  //NEW
      DBHelper.deleteAll(Department.class); //NEW
    }
}
```

So now we can add some data to database. We'll just create some `Department`s and employees, just like we did in our `Runner` class:

```java
//Seeds.Java
public class Seeds {
    public static void seedData() {
        DBHelper.deleteAll(Engineer.class);
        DBHelper.deleteAll(Employee.class);
        DBHelper.deleteAll(Manager.class);
        DBHelper.deleteAll(Department.class);

        //ADDED SEED DATA
        Department department1 = new Department("HR");
        DBHelper.save(department1);
        Department department2 = new Department("IT");
        DBHelper.save(department2);

        Manager manager = new Manager("Peter", "Griffin", 40000, department1, 100000 );
        DBHelper.save(manager);
        Engineer engineer1 = new Engineer("Lois", "Griffin", 29000, department1);
        DBHelper.save(engineer1);
        Engineer engineer2 = new Engineer("Stewie", "Griffin", 27000, department1);
        DBHelper.save(engineer2);
    }
}
```

Finally, we need to call this method the `main` method in our `EmployeesController` class. We'll add the call right at the start of the `main` method:

```java
//EmployeesController.java

public class EmployeesController {

    public static void main(String[] args) {
        Seeds.seedData(); //NEW

        // AS BEFORE
    }
}

```

Finally to change the way that hibernate handles restarts we will change the `create-drop` option in `hibernate.cfg.xml` to `update`. This won't drop our tables each time.

```xml
<!-- hibernate.cfg.xml-->

<property name="hbm2ddl.auto">update</property> 

```

Try running the app and going to `localhost:4567/employees` now. You should see the seeded data appearing in the table.
