# Getting Data via an API call

## Learning Objectives

* Add new dependencies to our app using Gradle
* Use AsyncHttpClient to send a query to an API
* Get JSON sent back by API call
* Allow an app to connect to the internet

## Getting Data via an API call
We're now going to look at using an API call to get data which our App can use. We are going to use an API which has already been set up on Heroku so we need to get our App to connect to that API using the internet.

[i]: the following is taken from part 3 of the Android tutorial, found [here] (https://www.raywenderlich.com/78578/android-tutorial-for-beginners-part-3)

## Networking Considerations
There are a few things we need to remember when adding networking capabilities to our apps.

For example, we need to think about how to keep all network interactions off the main UI thread, so that a user can continue to use your app without everything stopping until a download has finished. Can you imagine how hacked off you'd get if you were using an app and it became completely unresponsive for ages at a time.

If your App does become unresponsive, then Android will ask the user if they want to quit the App, which is not good for your App if people are leaving it like rats off a sinking ship.

Luckily, there are third-party libraries which can help us to resolve such issues. These have been specially designed and supported by Android experts to facilitate networking. Some of the most well-regarded include Retrofit, Volley, and Android Async Http. To get information from the web you can use the Android Async Http library.


### A Quick Look at Gradle

When we created our project in Android Studio, you might remember that we mentioned something called 'Gradle'. Well now we are going to use it to add the Android Async Http library to our project.

To add the library to our project, first of all open the ```build.gradle``` file. This can be found by clicking on the 'Gradle Scripts' icon in the project window.

Open build.gradle. Note that there are two build.gradle files in your project. You want the one that’s within the app folder (i.e. the one with 'Module:App' in brackets after the name of the file) – not the one at the project root level :

We now need to add the following code under 'dependencies' in the build.gradle file

```
// build.gradle
dependencies {
    ...
    // there may or may not be a support library above these
    compile 'com.loopj.android:android-async-http:1.4.4'
}
```

Finally, right-click on the build.gradle file and select 'Synchronise 'build.gradle' '

And that's it. The library is now part of your project for you to use.

## The Craggy Island 8-Ball API

We're now ready to have a look at the datasource we are going to use for our App - the Craggy Island 8-Ball API. Go to Chrome and enter the following in the location bar:

```
https://craggy-island-8-ball-api.herokuapp.com/
```

You should see a big load of JSON on the screen. 

[i]: it's not a full API as heroku wants a Credit Card before it will let you use a Mongo DB :-(, so this page just returns JSON for the whole lot - equivalent to a GET of all the entries in the DB.

### Creating another sub-class

Now that we have an idea as to what to expect from the datasource, it's time to go and write the code to go and get that data in our app. We do this by creating another sub-class of our abstract class ```Answers```. We can call this ```AnswersFromAPI```. 

Thankfully, there's one we prepared earlier :-)

[i]: give out AnswersFromAPI.java and talk through the code

```
//AnswersFromAPI.java

public class AnswersFromAPI extends Answers  {

    private final static String API_URL="https://craggy-island-8-ball-api.herokuapp.com/";

    ...
}
```

This is simply a reference to the URL we’ll be calling. It’s much better to hold this URL as a static String, so we don’t have to go searching through your code for it.

Because this class extends the ```Answers``` class, then this also has to implement the ```setUpAnswers``` method:

The first thing we need in our method is to create an instance of the ```AsyncHttpClient``` class to create a client to perform networking with: 

```
//AnswersFromAPI.java

public void setupAnswers() { {
    // Create a client to perform networking
    AsyncHttpClient client = new AsyncHttpClient();
}

```
We have available to use lots of methods on the client object we've just created. It's bit like the XMLHttpRequest in JavaScript. 

But we're just going to use the GET method to grab the data form our API. It's a bit like an AJAX request.

Ok, so now we have an object we can use to get the data, let's use the GET method in our code.

We can use the API_URL variable that we set earlier.

```
//AnswersFromAPI.java

public void setupAnswers() {
    ...
    client.get(API_URL);
}
```

What else do you think we'll need to make this GET request? Think back to AJAX.

We need something to deal with the response we get back, assuming the call is successful. 

In this case it's going to be an anonymous class. What? 

Because we're only using this class once, we can declare it and pass it in at the same time!

```
//AnswersFromAPI.java
public void setupAnswers() {
    ...
        client.get(API_URL, new JsonHttpResponseHandler(){
    
    });
}
```

Now let's add two methods to our class to deal with success, and failure. Remember we have to use the special @Override syntax.

In our success method we're predicting that we'll get a JSONObject passed in from the get method.

```
//AnswersFromAPI.java
public void setupAnswers() {
    ...
    client.get(API_URL, new JsonHttpResponseHandler(){
        @Override //NEW
        public void onSuccess(JSONObject jsonObject){} // NEW
    });
}
```
Now let's add a method to deal with a failed request. It's going to receive 3 things from the get methods if the request fails.

1. A status code
2. A throwable object
3. A JSON object which we'll call error

```
//AnswersFromAPI.java

client.get(API_URL, new JsonHttpResponseHandler(){
    @Override
    public void onSuccess(JSONObject jsonObject){}

    @Override
    public void onFailure(int statusCode, Throwable throwable, JSONObject error){}
});
```


## Using our new Sub-class

So now that we've created another sub-class of ```Answers```, let's try using this in our app. So what do we do?

If you go back to our ```MainActivity``` class then you'll see that when we declared ```mAnswers``` as an instance of the ```Answers``` superclass, yet we initialised it by calling the constructor of a subclass, in this case ```AnswersFromSimpleStrings```.  Well, all we need to do to change from using ```AnswersFromSimpleStrings``` to using ```AnswersFromAPI``` is simply to change the constructor call. I.E. we only need to change

```
//MainActivity.java

    mAnswers = new AnswersFromSimpleStrings();
```

to 

```
//MainActivity.java

    mAnswers = new AnswersFromAPI();
```

So let's try running the app now. 
What would we expect to see in our log when we run the app?

```
E/EightBall: failure:: 0 Unhandled exception: Permission denied (missing INTERNET permission?)
```

This is because we need to let Android know that we plan to access the internet in our App. Where do you think we would do this? 

In our android_manifest.xml file, using a 'uses-permission' tack. So let's do this. We add it near the top of the file, just before the 'application' tag:

```
<!--- android_manifest.xml -->

 <uses-permission android:name="android.permission.INTERNET" />
```

[i]: Android has lots of these permissions e.g. for accessing an external SD card

So now let's try running the our App again. 

What do you see in the log file?

Hopefully you should see the JSON returned from the API call.






