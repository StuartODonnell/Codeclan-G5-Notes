# Spark/Velocity Homework - Randomiser

## Objectives
- Practice creating a basic Java Spark application
- Practice creating routes in a Java Spark application
- Practice using Velocity Templates in a Java Spark application

## The Task

### MVP

You are required to create a Java Spark application to generate random names from a list, or generate random pairs of names from a list. The list of names should be pre-populated.

Create a class which holds a pre-populated ArrayList of Strings, for the names to be randomised. This class should have a method for each of the following:
 * to be able to return a random name from the ArrayList
 * to be able to get a pair of random names from the ArrayList and return those names using an ArrayList

Create a Spark application which uses the class you have create above. It should have the following routes:
`/one` - this should get a single random name from the list and display that name in the browser
`/two` - this should get a random pair of names from the list and display that pair in the browser.

You should display the results using Velocity Templates. You will need a different Velocity Template for each route.

### Possible Extensions
- Add further routes for:
  - Groups of three
  - Groups of four
- Add checking to make sure that the list contains enough entries for the number of names being asked for. If it does not, then simply return all the names in the list.
- Any other extensions you can think of
