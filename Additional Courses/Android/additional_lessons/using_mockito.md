# Adding Mockito Testing Framework to and Android Studio Project

## Learning Objectives
* Add Mockito testing framework to an Android Studio project

### A Quick Look at Gradle

When we created our project in Android Studio, you might remember that we mentioned something called 'Gradle'. Well we can use it to add the Mockito testing Framework to our project, so that we can use mocking and stubbing when testing our Java classes.

To add the library to our project, first of all open the ```build.gradle``` file. This can be found by clicking on the 'Gradle Scripts' icon in the project window.

Open build.gradle. Note that there are two build.gradle files in your project. You want the one that’s within the app folder (i.e. the one with 'Module:App' in brackets after the name of the file) – not the one at the project root level :

We now need to add the following code under 'dependencies' in the build.gradle file

```
// build.gradle

testCompile 'org.mockito:mockito-core:1.10.19'
```

At the moment we already have Junit so we just add our line to include mockito immediately after the inclusion of Junit i.e.:


```
// build.gradle
dependencies {
    ...
    // there may or may not be a support library above these
    testCompile 'junit:junit:4.12'
    testCompile 'org.mockito:mockito-core:1.10.19'  //ADDED LINE
}
```

Finally, right-click on the build.gradle file and select 'Synchronise 'build.gradle' '

And that's it. The library is now part of your project for you to use.
