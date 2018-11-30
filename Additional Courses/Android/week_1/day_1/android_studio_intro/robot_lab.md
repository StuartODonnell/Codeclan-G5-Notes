# Lab - Robot

## Introduction

You are tasked with treating a small app in Android Studio to control the behaviour of a service droid.

Remember to use TDD!


### Task 1

* Create a Robot. This should take a name and colour in to its constructor and also have a battery level of type double which should default to 100.0 when Robot is instantiated.

### Task 2
Go and write tests for the following methods in the ```Robot``` class, and them implement them:

* ```double checkBattery()```
  * returns percentage of battery life left
* ```String makeDrink (String drink)```
  * passes in the name of the drink being made e.g. tea
  * uses up 10% of the battery life
  * returns the string "I am making" + drink
* ```String washDishes()```
  * uses up 30% of battery life
  * returns the string "I am washing the dishes"
* ```String doDusting()```
  * uses up 20% of the battery life
  * returns the string "I am dusting"
* ```void rechargeBattery()```
  * resets battery to 100.0

  ## Extensions

  * Before robot performs any task make sure that there is enough battery to do so. If not return a string "Not enough battery".

  * Add any other functionality you wish!
