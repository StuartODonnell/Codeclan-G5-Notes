# Spark and Velocity - Forms

## Objectives
* Be able to create forms using template files
* Be able to get back values submitted in forms

## Introduction

So far we've been able to create templates and pass data to our templates so that it can be rendered in our html. But how do we send data to our app rather than using the URL? We can do this by using html forms.

## The Plan

What we are going to do is add a link to our current flights list page to allow us to add a new flight to the list. This will bring up a form where we can enter details for a new flight and then when we submit the form it will add our new flight to the list and bring back up the 'list' page with (hopefully!) our new flight added to the list shown.

So we need to go through the following steps:

* Add a link to the current page to add a new flight
* Add another `GET` route to our controller to match the route from the link
* Create a template for our form. Submitting this form will create a `POST` request
* Add a `POST` route to our controller to handle the form submission

## Adding the link

Let's add a link to our form. The ref for this link will be "/flights/new" which will be a new route we will create in our controller.

```html
<!-- flights.vtl -->
<table>
  <!-- as before -->
</table>

<a href="/flights/new">Add Flight</a>
```

## Adding the `GET` Route to our Controller

Clicking on the link will result in a `GET` request being made to our server. The route for this request will be "/flights/new" so we need to create a handler for a `GET` request which matches our route. This route will take three arguments: the route, the lambda expression for the code to be executed, and the `velocityTemplateEngine` object.

```java
//FlightsController.java
  get ("/flights/new", (req, res) -> {  //NEW

  }, velocityTemplateEngine);
```

In the code for the handler we need to create a hashMap for our model as before. This time the HashMap only requires one entry - the template for the form we are going to render. We are going to call this form `create.vtl`

***TASK*** - have a go at completing the route

***SOLUTION***:
```java
//FlightsController.java

  get ("/flights/new", (req, res) -> {
      HashMap<String, Object> model = new HashMap<>();
      model.put("template", "create.vtl");
      return new ModelAndView(model, "layout.vtl");
  }, velocityTemplateEngine);
```

## Adding a form

Let's add a form to our app, so that we can create a new flight and add it to our list. First of all, let's create a new template form, called `create.vtl`

> maybe get the students to do this themselves

Let's add the boilerplate for our form. When the 'Submit' button is clicked this is going to generate a `POST` request, with the route being "/flights":

```html
<!-- create.vtl -->

<p class="pageHeader">New Flight</p>

<form action="/flights" method="post">

  <input type="submit" value="Save" class="button">
</form>
```

The submit button is sending an HTTP `POST` request to `/flights,` which is what we put in the HTML <form> method and action. Actually this is why we used a form in the first place. An HTML form can send any HTTP request type we want, but the address bar of a browser can only send GET requests. This is a security feature. It means that users can't just POST any stuff they want to our server. We get to control the data they POST with our form inputs.

So now we can add the inputs for our form. We need to add for text inputs for the attributes for a `Flight` object. We're not going to bother about the `seatsAvailable` attribute as we'll assume that a new flight has empty seats. So we need to add text inputs for the following attributes:

* flightNumber
* airline
* from
* destination

Let's add our first input:

```html
<!-- create.vtl -->

<form action="/flights" method="post">
      <label for="flightNumber">Flight Number:</label>
      <input type="text" class="inputbox" name="flightNumber" required="true" id="flightNumber"/>

    <input type="submit" value="Save" class="button">
</form>
```

The `for` properties on the `<label>`s should match the id property on the relevant `<input>`. This is important for accessability to allow screen readers to read our website correctly.

TASK: go and add the rest of the inputs

SOLUTION:

```html
<!-- create.vtl -->

<form action="/flights" method="post">
    <label for="flightNumber">Flight number:</label>
    <input type="text" class="inputbox" name="flightNumber" required="true" id="flightNumber"/>
    <label for="airline">Airline:</label>
    <input type="text" class="inputbox" name="airline" required="true" id="airline">
    <label for="departureAirport">From:</label>
    <input type="text" class="inputbox" name="departureAirport" required="true" id="departureAirport">
    <label for="arrivalAirport">To:</label>
    <input type="text" class="inputbox" name="arrivalAirport" required="true" id="arrivalAirport">

    <input type="submit" value="Save" class="button"/>
</form>
```


## Adding the `POST` route

Finally, we need to add the handler for the `POST` request to "/flights". As we are dealing with a `POST` request, we need to import the following (similar to the import for `GET`):

```java
//FlightsController.java

import static spark.Spark.post; // ADDED AT TOP OF FILE
```

So we can now add the boilerplate for our route handler:

```java
//FlightsController.java

  post("/flights", (req, res) -> { //ADDED
      HashMap<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "layout.vtl");
  }, velocityTemplateEngine);

```

So in this route we are going to:

* get the data from the form
* create a new `Flight` object from that form
* add the new `Flight` object to the list
* render the original `itinerary` template, passing it the updated list.

### Getting the data from the form

The data from the form is contained within the request sent to the server. In our example, this means that the data is contained within the `req` argument in our handler. Before we saw how we could get the data passed in via the url by calling the `params()` method on the request.

```java
  req.params()
```

When getting the data from the form we call the `queryParams()` method on the request This returns a set of the all the parameters passed in by the request

```java
request.queryParams();
```

This gives us all the parameters, but how do we get just the items in the form? Each item in the form makes up a separate query parameter. To get an individual query parameter we can pass a string value to `queryParams` for the particular paramater we are looking for.

In our example, the parameters correspond to the `name` attribute in our inputs on our form.

> refer to this code

```html
<label for="flightNumber">Flight Number:</label>
<input type="text" class="inputbox" name="flightNumber" required="true">
```

So if we wanted to get what the user entered for the flight number we would be looking for the `flightNumber` parameter e.g..

```java
  req.queryParams("flightNumber");
```

So now we can get this value, saving it in a variable:

```java
//FlightsController.java

  post("/flights", (req, res) -> {
      String flightNumber = req.queryParams("flightNumber"); //ADDED

      HashMap<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "layout.vtl");
  }, velocityTemplateEngine);

```

#### TASK

* use `queryParams` to get the data for each of the other fields in the form, assigning each of the values to variables.
* create a new `Flight` object using the data in these variable
* add the new `Flight` object to our `flights` list.

SOLUTION:

```java
//FlightsController.java

  post("/flights", (req, res) -> {
      String flightNumber = req.queryParams("flightNumber");
      String airline = req.queryParams("airline");
      String departureAirport = req.queryParams("departureAirport");
      String arrivalAirport = req.queryParams("arrivalAirport");

      Flight newFlight = new Flight(flightNumber, airline, departureAirport, arrivalAirport );
      flights.add(newFlight);

      HashMap<String, Object> model = new HashMap<>();
      return new ModelAndView(model, "layout.vtl");
  }, velocityTemplateEngine);

```

### Showing the updated list

So now that we have our updated list we want to display it.

TASK:
* modify our `POST` request handler so that it renders our `flights.vtl` template
* pass our updated list to our route

SOLUTION:

```java
//FlightsController.java

  post("/flights", (req, res) -> {
      String flightNumber = req.queryParams("flightNumber");
      String airline = req.queryParams("airline");
      String from = req.queryParams("from");
      String destination = req.queryParams("destination");

      Flight flight = new Flight(flightNumber,airline, from, destination );
      flights.add(flight);

      HashMap<String, Object> model = new HashMap<>();
      model.put("flights", flights);  //ADDED
      model.put("template", "flights.vtl"); //ADDED
      return new ModelAndView(model, "layout.vtl");
  }, velocityTemplateEngine);

```

This is all good and well but what happens if we refresh the page?

What??? It resubmits the form and keeps adding our flight.

A better idea rather than creating the new template and rendering it from the post method is to just redirect to `/flights` as a get request.

In here we won't need to return a template so we will just return `null`.

```java
//FlightsController.java

post("/flights", (req, res) -> {
           String flightNumber = req.queryParams("flightNumber");
           String airline = req.queryParams("airline");
           String departureAirport = req.queryParams("departureAirport");
           String arrivalAirport = req.queryParams("arrivalAirport");

           Flight newFlight = new Flight(flightNumber, airline, departureAirport, arrivalAirport);
           flights.add(newFlight);
           res.redirect("/flights"); // NEW
           return null; //NEW
       });

```


## Summary

What we have covered:

* adding links to our template files
* creating a form in a Velocity template
* adding a handler for a `POST` request
* getting data from a request using `queryParams()`
