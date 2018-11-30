# Employee App - Adding a Second Controller

## Objectives

* Be able to add a second controller
* Be able to add a form to add a new manager
* Be able to POST the data in that form to add a new manager to the database

## Intro

So we are now able to see all our employees. But what if we want to start dealing with `Manager`s. We'll need to add some more routes. We could create them inside our `EmployeesController` class but, to be cleaner, and following the MVC principle, we are going to create another controller, `ManagersController`.

## Creating the `ManagersController` Class

So let's start by creating a new class `ManagersController`

This class will not have a `main` method but will have a constructor:

```java
//ManagersController.java
  public class ManagersController() {
       public ManagersController() {  //CONSTRUCTOR
          //EMPTY FOR NOW
      }
   }
```

When we created the `EmployeesController` we created our routes within the `main` method. Since the `ManagersController` class does not have a `main` method it has to have a method inside which we can set up our routes. This method is `private` and is called from the constructor. We will call this method `setupEndpoints()`. So lets add it now:

```java
//ManagersController.java
  public class ManagersController() {
      public ManagersController() {
        this.setupEndpoints(); //NEW
      }

      private void setupEndpoints() {
        //ROUTES GO HERE
      }
   }
```

As this class has no `main` method we have to new up an instance of it so that the routes are set up. We do this inside our `main` method.

```java
//EmployeesController.java

  public static void main(String[] args) {
    ManagersController managersController = new ManagersController(); //NEW

    //AS BEFORE
  }
```

TASK:
  * Create a route `/managers` inside the `setupEndpoints()` method to display all the managers.
  * Create the necessary template file to render a table with all the managers
  * Test it by running the app and going to `localhost:4567/managers` in the browser.

POSSIBLE SOLUTION:

```java
//ManagersController.java
  private void setupEndpoints() {
    get("/managers", (req, res) -> {
      Map<String, Object> model = new HashMap<>();
      List<Manager> managers = DBHelper.getAll(Manager.class);
      model.put("template", "templates/managers/index.vtl");
      model.put("managers", managers);
      return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());
  }

```

```html
<!-- template/managers/index.vtl -->

  <p class="pageHeader">Managers</p>

  <div class="container">
    <table>
      <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Department</th>
        <th>Budget (£)</th>
      </tr>
        #foreach($manager in $managers)
          <tr>
            <td>$manager.getFirstName()</td>
            <td>$manager.getLastName()</td>
            <td>$manager.getDepartment().getTitle()</td>
            <td>$manager.getBudget()</td>
          <tr>
        #end
    </table>
  </div>

```

## Adding a new manager

So now that we can display all the managers, let's think about adding a new manager. Thinking of the RESTful routes we will actually require two routes for this:

* a `GET` request to get the form to enter the details for the new manager - `managers/new`
* a `POST` request to send the data entered in the form back to the controller

We probably want to have a link we can click on to send the `GET` request to bring up the form. We can add a link for the `GET` request in the `index.vtl` template:

```html
<!-- template/managers/index.vtl -->

  <p class="pageHeader">Managers</p>

  <div>
      <a href="/managers/new" class="button center">New Manager</a>  <!-- NEW -->
  </div>

  <div class="container">

    <!-- AS BEFORE -->

  </div>
```

TASK

* Create a template file in `templates/managers` called `create.vtl`. In this file create a form. Submitting this form should send a `POST` request to `/managers`. This form should contain input fields for the following fields in the `Manager` class:
  * firstName
  * lastName
  * budget
  * salary

We'll leave `department` for now.

* Create a route for a `GET` request in the `ManagersController` class for `/managers/new` which returns a file called `create.vtl` (which will contain the form)
* Test by going to `localhost:4567/managers` and clicking on the `New Manager` link.

SOLUTION:

```java
//ManagersController.java

  get ("/managers/new", (req, res) -> {
    Map<String, Object> model = new HashMap<>();
    model.put("template", "templates/managers/create.vtl");
    return new ModelAndView(model, "templates/layout.vtl");
  }, new VelocityTemplateEngine());
```

```html
<!-- templates/managers/create.vtl -->

<p class="pageHeader">New Manager</p>

<form action="/managers" method="post">
    <label for="firstName">First Name:</label>
    <input class="inputbox" type="text" name="firstName" required="true">

    <label for="lastName">Last Name:</label>
    <input class="inputbox" type="text" name="lastName" required="true">

    <label for="salary">Salary(£):</label>
    <input type="number" name="salary" required="true" class="inputbox">

    <label for="budget">Budget(£):</label>
    <input type="number" name="budget" required="true" class="inputbox">

    <input type="submit" value="Save" class="button">
</form>

```

### Selecting the Department

So we haven't added an input to our form yet for the new manager's department. Given that we have a `departments` table in our database then it makes sense to want to use a dropdown to select an item from that table for the manager's department.

The first thing we want to do is get all the departments in our `/managers/new` route and then pass that to our form. We can do this by calling the `getAll()` method in the `DBHelper` class and then adding it to our `model` hashMap:

```java
//ManagersController.java

  get ("/managers/new", (req, res) -> {
    Map<String, Object> model = new HashMap<>();
    List<Department> departments = DBHelper.getAll(Department.class); //NEW
    model.put("departments", departments); //NEw
    model.put("template", "templates/managers/create.vtl");
    return new ModelAndView(model, "templates/layout.vtl");
  }, new VelocityTemplateEngine());
```

Then, in our form, we can add an html `select`. We need to create an option for each department passed in. We can do this using Velocity's `#foreach` construct. The value for each option will be the id of the department, with the text being its title:

```html
<!-- templates/managers/create.vtl -->

  <p class="pageHeader">New Manager</p>

  <form action="/managers" method="post">
    <!-- AS BEFORE -->
    <label for="lastName">Last Name:</label>
    <input class="inputbox" type="text" name="lastName" required="true">

    <label for="department">Department:</label> <!-- NEW -->
    <select id="department" name="department">
      #foreach( $department in $departments )
        <option value="$department.getId()">$department.getTitle()</option>
      #end
    </select>
    <!-- AS BEFORE -->
  </form>

```


## POSTing The Form Data

At the moment, if we click the `Save` button on our form, we get a `404 Not Found` error. This is because we are not handling the `POST` request sent on submitting the form. So what do we want this route to do?

* Get the data from the form
* Create a `Manager` object from that data
* Save that `Manager` object to the database
* Return to the `index` page.

TASK: have a go at this yourself (if you are not sure then refer back to the `FlightsController` we wrote in a previous lesson)

SOLUTION:

```java
//ManagersController.java
  post ("/managers", (req, res) -> {
    int departmentId = Integer.parseInt(req.queryParams("department"));
    Department department = DBHelper.find(departmentId, "Department");
    String firstName = req.queryParams("firstName");
    String lastName = req.queryParams("lastName");
    int salary = Integer.parseInt(req.queryParams("salary"));
    int budget = Integer.parseInt(req.queryParams("budget"));
    Manager manager = new Manager(firstName, lastName, salary, department, budget);
    DBHelper.save(manager);
    res.redirect("/managers");
    return null;
  }, new VelocityTemplateEngine());

```

## Debugging in Spark

Debugging is very important in Spark, especially when trying to deal with forms like the one we have just written.

Let's start by putting a breakpoint in on the first line of the post request.

```java
//ManagersController.java
  post ("/managers", (req, res) -> {
    // ADD BREAKPOINT TO FOLLOWING LINE
    int departmentId = Integer.parseInt(req.queryParams("department"));
  }, new VelocityTemplateEngine());
```

Now if we run the debugger and make a post request, the program will stop here and we can see what we're working with.

Let's run the debugger, click on New Manager fill out the fields and submit our new manager. You will see that once we make the request, IntelliJ should come to the forefront and we can click Step Over and see each property.

This is extremely useful for making sure that we are correctly dealing with what is being passed through the form.
