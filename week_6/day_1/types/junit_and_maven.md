# Junit with Maven

## Junit

JUnit is a unit testing framework for the Java programming language. JUnit has been important in the development of test-driven development.

JUnit is included in IntelliJ and is installed through Gradle.

JUnit, along with other libraries is available as a JAR (Java ARchive) file.

A JAR  is a package file format typically used to combine many Java class files and associated resources (text, images, etc.) into one file for distribution.

There are 3 major build tools used in Java. `Ant`, `Gradle` and `Maven`

### What does this mean?

Which build tool you use will depend on the needs of your application. If build speed is important to you, Maven may be best. If documentation and support is important, then Gradle could be the way to go. If full control is what you want, then you should lean towards Ant. All three of these are good options, so it really comes down to what is best for you and the application..

## Maven

We are going to use Maven as it is a very useful tool for what we are going to be doing.  We will cover more of this later but for now you just need to know that it allows us access to various dependencies and for now we're using it to access JUnit.

This means we now have access to the classes and methods included in JUnit. Similar to how we used MiniTest.

We can create functions marked as tests to run, create a setup method to be ran before each test and use assertEqual methods to check our results.  

This is one of the powerful features of Gradle and it saves us having to download all the JAR's we may need for projects and adding them to our libraries manually.

You can add available packages through Gradle from SQL libraries through to other testing tools to make them available for use in your applications. 
