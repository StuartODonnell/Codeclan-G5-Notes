# Velocity - Intro - Part II

## Objectives

* Be able to pass lists of objects to a template file.
* Be able to use code constructs in a template.
* Be able to create a master(layout) template file.
* Be able to use CSS to our templates.

## Introduction

So far we have only passed one single item to our velocity templates

> Give out starter code and get students to look through for a few minutes

In the example, we only have one flight which we pass to our template. Say we were going to use this to create an app to keep track of all the flights we had booked. We would be maintaining a list of flights. How would we deal with a list of flights, rather than a single flight?

Let's start by adding another couple of flights in our route, making an ArrayList of all the flights

> Perhaps get the students to do this for themselves maybe get them to make one flight have no seats available

```java
//FlightsController.java
import java.util.ArrayList; //ADDED AT TOP

  ArrayList<Flight> flights = new ArrayList<>(); //ADDED
  Flight flight = new Flight("SC666", "Scottish Airways","Inverness", "New York");
  Flight flight2 = new Flight("US123", "US Airlines", "New York", "Sydney"); //ADDED
  Flight flight3 = new Flight("AU555", "Aussie Airways", "Sydney", "London");  s//ADDED
  flight2.setSeatsAvailable(false);

  flights.add(flight); //ADDED
  flights.add(flight2); //ADDED
  flights.add(flight3); //ADDED

  get("/flights", (req, res) -> {
      HashMap<String, Object> model = new HashMap<>();
      model.put("flight", flight);
      return new ModelAndView(model, "flights.vtl");
  }, velocityTemplateEngine);
```

We can now pass our whole list of flights to our template:

```java
//FlightsController.java

  ArrayList<Flight> flights = new ArrayList<>();
  Flight flight1 = new Flight("SC666", "Scottish Airways","Inverness", "New York");//MODIFIED
  Flight flight2 = new Flight("US123", "US Airlines", "New York", "Sydney");
  Flight flight3 = new Flight("AU555", "Aussie Airways", "Sydney", "London");

  flights.add(flight1);
  flights.add(flight2);
  flights.add(flight3)

  get("/flights", (req, res) -> {
      HashMap<String, Object> model = new HashMap<>();
      model.put("flights", flights);   //MODIFIED
      return new ModelAndView(model, "flights.vtl");
  }, velocityTemplateEngine);
```

If we try running our app now, we get some really strange output. This is because what we pass through does not match what the template expects. We are passing through a whole arraylist of `Flight` objects, but the template is only expecting a single `Flight`.

So how do we get our template to display all our flights? One approach would be to pass through each flight individually, by adding each flight individually to the model and then rendering each flight object in the page.

> Perhaps show this.

This is fine for a couple of flights, but what if the list had 10, 50, 100 flights or more? What we can do is to still pass the list of flights to the template, but in the code for the template we can loop through the list of flight objects, rendering each one individually.

## Adding a loop to our template file

Say we wanted to add a new row to our table for each flight in the list. We can loop through the list of flights, and for each flight, create a new row in the table and print out its details.

We can use velocity's `foreach` loop, which is something that looks very similar to the enhanced 'for' loop in java.

```html
#foreach ($refinarg)
  statement
#end
```

Since we want to loop through the list of flights, which is passed in as `$flights`, we would loop through this array. Each flight can then be referred to as `$flight` i.e.

```html
#foreach ($flight in $flights)
#end
```

NOTE the `#` at the start of each line.

Inside the loop we will add a new table row for each iteration:

```html
<!-- flights.vtl -->

<table>
    <tr>
        <th>Flight Number</th>
        <th>Airline</th>
        <th>From</th>
        <th>To</th>
        <th>Seats<th>
    </tr>

    #foreach ($flight in $flights)  <!--ADDED -->
        <tr>
        </tr>
    #end <!--ADDED-->
</table>
```

Finally, for each new table row we can print out the details, using the current value of `$flight`:

```html
<!-- flights.vtl -->

<table>
    <tr>
        <th>Flight Number</th>
        <th>Airline</th>
        <th>From</th>
        <th>To</th>
        <th>Seats<th>
    </tr>

    #foreach ($flight in $flights)
        <tr>
            <td>$flight.getFlightNumber()</td>
            <td>$flight.getAirline()</td>
            <td>$flight.getDepartureAirport()</td>
            <td>$flight.getArrivalAirport()</td>
            <td>$flight.isSeatsAvailable()</td>
        </tr>
    #end
</table>
```

Run the app now, what do you see?

## Adding conditional code to our template file

As well as adding loops to our template file we can also add conditional code. At the moment we only display the actual value returned by the `isSeatsAvailable()` method i.e. `true` or `false` but what if we wanted to display something more meaningful e.g. display 'Seats Available' if the value is `true` and 'Full' is the value is `false`.

We can use Velocity's `if...else...end` construct to do this

```html
      #if ($flight.isSeatsAvailable())
        Seats Available
      #else
        Full
      #end
```

So our template should now look something like:

```html
<!-- flights.vtl -->

<table>
    <tr>
        <th>Flight Number</th>
        <th>Airline</th>
        <th>From</th>
        <th>To</th>
        <th>Seats<th>
    </tr>

    #foreach ($flight in $flights)
        <tr>
            <td>$flight.getFlightNumber()</td>
            <td>$flight.getAirline()</td>
            <td>$flight.getDepartureAirport()</td>
            <td>$flight.getArrivalAirport()</td>
            <td>
                #if ($flight.isSeatsAvailable())
                    Seats Available
                #else
                    Full
                #end
            </td>
        </tr>
    #end
</table>
```

## Remember

We use a `$` before any variable we want to refer to in our template e.g. `$flight` and a `#` before any code e.g.

```html
      #if ($flight.isSeatsAvailable())
        Seats Available
      #else
        Full
      #end
```

> BREAK

## Layout(Master) files

Let's just say we wanted to add some html layout code to our template file. We would end up with something like:

```html
<!-- flights.vtl -->

<!DOCTYPE html>
  <html lang='en'>
    <head>
      <meta charset='utf-8'>
      <title>Velocity Flights</title>
    </head>
    <body>
      <table>
          <tr>
              <th>Flight Number</th>
              <th>Airline</th>
              <th>From</th>
              <th>To</th>
              <th>Seats<th>
          </tr>

          #foreach ($flight in $itinerary)
              <tr>
                  <td>$flight.getFlightNumber()</td>
                  <td>$flight.getAirline()</td>
                  <td>$flight.getDepartureAirport()</td>
                  <td>$flight.getArrivalAirport()</td>
                  <td>
                      #if ($flight.isSeatsAvailable())
                          Seats Available
                      #else
                          Full
                      #end
                  </td>
              </tr>
          #end
      </table>
    </body>
  </html>
```

But then we would need to add that to **_ALL_** our template files, which would not be very dry.

Velocity allows us to nest template files i.e. to include one template within another template. It does this by using the `#parse` directive which renders a local template that is parsed by Velocity. It takes a velocity template as a variable and renders it as if it were part of the template including the `#parse` directive.

```
  #parse( $template )
```

This means that we can create a template file which contains scaffolding code and then pass the template we want it to render.

Lets create our scaffolding template file:

> perhaps send out the scaffolding code via Slack

* Right click on `src/main/resources`
* Go to New->File
* Enter `layout.vtl` as the filename and click `OK`
* Add the scaffolding code as shown below

```html
<!-- layout.vtl -->

<!DOCTYPE html>
<html>
  <head>
    <title>Velocity Flights</title>
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

We now want this `layout.vtl` file to be rendered by our `GET` route in FlightController. How would we do this?

> Ask the class if they can think of how we would do this.

In our `FlightController` class, we we create the `ModelAndView` we need to change the layout from `flights.vtl` to `layout.vtl`:

```java
//FlightsController.java

  get("/flights", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("flights", flights);
            return new ModelAndView(model, "layout.vtl"); //MODIFIED
        }, velocityTemplateEngine);
```

So no try stopping and restarting our app. What do you see when you refresh the page - NOTHING? WHY?

> Ask the class for ideas

We are now rendering the `layout.vtl` but it does not include the code from `flights.vtl`. This is because of the following line in `layout.vtl`:

```
  #parse( $template )
```

At the moment `$template` has no value, so nothing is rendered. How do we fix this?

> Ask the class for ideas, or get them to fix it for themselves

We need to pass the template file as part of the model i.e. we need to add it to the HashMap passed to the constructor for `ModelAndView`. The variable used in `layout.vtl` to tell the `#parse` directive which template file to render is called `$template` so the key for the entry in the hashmap will be "template". The value will be the name of the template file i.e. `flights.vtl` and will be passed as a String i.e.:

```java
  model.put("template", "flights.vtl");
```

So our route should now look something like:

```java
//FlightsController.java

  get("/itinerary", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("flights", flights);
            model.put("template", "flights.vtl"); //ADDED
            return new ModelAndView(model, "layout.vtl");
        }, velocityTemplateEngine);
```

Now try restarting our app and reloading the page. You should see the table as before.

## Customising the output

From this point on, the template files can be treated like HTML, and to customise the appearance of HTML, we need some CSS.

We'll create a "public" directory to store static assets. Velocity looks for any matching path in the public directory before matching routes for requests.

* Right click on `src/main/java/resources`
* Go to New->Directory
* Enter `public` as the directory name and click `OK`

Let's now create a CSS file called `style.css` in the `public` directory

* Right click on `src/main/java/resources/public`
* Go to New->File
* Enter `style.css` as the filename and click `OK`

> perhaps get the students to do this for themselves

Let's add some styling for our table:

```css
/* style.css */

th,
td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}
```

To get our code to recognise the css file we need to do two things.

Firstly, we need to tell our app that all static assets are in the `public` folder. Velocity has a method called `staticFileLocation()` which does this. It takes the name of a directory as a string and then by default, will look in that folder for static assets. We do this in our `FlightController` class

```java
//FlightsController.java

public class FlightsController {
    public static void main(String[] args) {
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
        staticFileLocation("/public"); //ADDED

        //AS BEFORE
      }
}
```

NOTE - you will need to import `static spark.SparkBase.staticFileLocation`

Finally, we just need to tell our layout file to use our css file:

```html
<!-- layout.vtl -->

  <head>
    <title>Velocity Flights</title>
    <link rel='stylesheet' href='/style.css'> <!-- ADDED -->
  </head>
```

Try stopping and re-starting the app. You should now see the styling applied to the table.

## Summary

We have covered:

* how to write looping code in our template
* how to add conditional code to our templates
* how to create a master/layout template and render templates within it
* how to use CSS in our templates
