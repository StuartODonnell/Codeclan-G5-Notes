# Saving JSON to SharedPreferences

## Learning Objectives

  - Know what data persistence options there are in Android
  - Understand serialization and deserialization
  - Be able to store JSON in SharedPreferences
  - Be able to implement a Toast

### Introduction

Android offers us several options for data persistence. The method that you choose depends on your specific needs, such as whether the data should be private to your application or accessible to other applications (and the user) and how much space your data requires.

The methods are:

* Shared Preferences - store private primitive data in key-value pairs.

* Internal Storage - store private data on the device memory.

* External Storage - store public data on the shared external storage e.g. SD card .

* SQLite Databases - store structured data in a private database.

* Network Connection - store data on the web with your own network server.

We're going to use SharedPreferences which is a simple store of key/value pairs, a bit like a hash. 

Often, you'll just want to store simple key/value pairs such as user IDs or session variables. But it's also possible to store JSON in SharedPreferences which gives us a way of storing complex data structures including ArrayLists for example.

### Why Are We Doing This?

We're doing this because becoming familiar with JSON is a very useful skill to have as a programmer, and not just in the world of Java/Android. Also, it could be very useful when it comes to building an app for your project.

### I Do

Let's build on our TopMoviesList. So far we are passing a Movie object through to the getMovie method and logging out the title of the movie.

> Draw a diagram of the relationship between these elements and refer back to it throughout the lesson.
> See json_data_persistence.png


### We Do: Serialization in Java

Open Movie.java

We're going to have the Movie class implement Serializable. We have to do this in order to pass the Movie object to another activity. In this case, a FavouritesActivity (which we haven't created yet)

> In computer science, in the context of data storage, serialization is the process of translating data structures or object state into a format that can be stored or transmitted

> Deserialization is the process of converting the serialized form of an object back into a copy of the object.

Although Serializable is an interface, it has no methods that we need to implement. It is simply a "flag" or "marker" that says we are allowing this object to be serialized by the JVM.

```java
//Movie.java

import java.io.Serializable;

public class Movie implements Serializable{
    //SAME AS BEFORE
}

```


### You Do

> TASK:

```
1. Create a new activity called FavouritesActivity
2. Update the getMovie method, adding a new Intent and add the Movie object as an extra
3. Update the getMovie method to start the FavouritesActivity 
```

> SOLUTION:

```java
//TopMoviesActivity

public void getMovie(View listItem) {
    Movie movie = (Movie) listItem.getTag();
    Log.d("Movie Title: ", movie.getTitle());

    Log.d("Selected Movie: ", movie.getTitle());

    Intent intent = new Intent(this, FavouritesActivity.class); // UPDATED
    intent.putExtra("movie", movie); // UPDATED

    startActivity(intent); // UPDATED
}

```

Let's run it in our emulator. We should be taken to a new blank activity (FavouritesActivity) when we click on a movie item.


### I Do: Using Shared Preferences & JSON

Shared preferences use the ```SharedPreferences``` class. 

This class gives us a general framework that lets us save and retrieve persistent key-value pairs of primitive data types. We can use SharedPreferences to save any primitive data: booleans, floats, ints, longs, and Strings. This data will persist across user sessions (even if you kill your app).

JSON is a common way to store data. And it's going to be useful for us because we can store JSON as a String. Therefore we can store a JSON string (which represents a Java object) in SharedPreferences.

We want to store an ArrayList of our favourite movies. But first, we'll have to convert the ArrayList to a String and for that, we'll need to use the GSON library.

GSON: A Java serialization/deserialization library that can convert Java Objects into JSON and back.

> NOTE: This is not quite the same as having a class in Java implement Serializable.

Let's add it to our Gradle file dependencies.

```java
// build.gradle (Module: app)

compile 'com.google.code.gson:gson:2.3.1' // NEW

```

### We Do: Implementing SharedPreferences

In our FavouritesActivity let's get the existing favourites from SharedPreferences.

Here's our plan:

> DRAW ON BOARD: 
> 1. Get the existing favourites from SharedPreferences
> 2. Add the new favourite movie to the existing ones
> 3. Display a simple String of the favourite movie titles in a TextView (optimistic update)
> 4. Save the list of favourites (including the new one) back to SharedPreferences


### Get the existing favourites from SharedPreferences

We'll have to import SharedPreferences. Also, we'll have to create a string resource called ```preference_file_key```. We'll give it the value "FAVOURITES".

We can think of this key almost as the name of our database. 

Remember SharedPreferences works as a key/value system. So we're going to attempt to get the value associated with the key "MyFavourites". The second argument for the getString method is what we'll get back if "MyFavourites" doesn't yet exist. 

So favouriteMovies is now going to be a reference to a String representation of the ArrayList of favourite Movie objects. 

```java
//FavouritesActivity
SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
String favouriteMovies = sharedPref.getString("MyFavourites", new ArrayList<Movie>().toString());
Log.d("Favourites String", favouriteMovies);
```

Let's run it and see what our Favourites String looks like logged out.

### Add the new favourite movie to the existing ones

First we need to deserialize the ArrayList using the GSON library.

> We need a TypeToken in order to tell Gson what type of thing (i.e. an ArrayList of Movies) that the JSON should be turned into.

```java
//FavouritesActivity
Gson gson = new Gson();
TypeToken<ArrayList<Movie>> movieArrayList = new TypeToken<ArrayList<Movie>>(){};
ArrayList<Movie> myFavourites = gson.fromJson(favouriteMovies, movieArrayList.getType());
Log.d("myFavourites", myFavourites.toString());
```

At this point myFavourites is an ArrayList of Movie objects. So now, we can add the Movie we passed from the main activity. Nice!

```java
//FavouritesActivity
Movie newFavourite = (Movie) getIntent().getSerializableExtra("movie");
myFavourites.add(newFavourite);
Log.d("myFavourites", myFavourites.toString());
```


Let's run it and see what our logged out ArrayList message looks like.

> Should look something like:

```
D/myFavourites: [com.codeclan.topmovieslist.Movie@1dbad3a]
```


### We Do: Display a simple String of the favourite movie titles in a TextView (optimistic update)

We're just going to concatenate a String and add it to a TextView to see our Movie titles.

```xml
//activity_favourites.xml

<TextView
    android:id="@+id/favourites_list"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:padding="20dp"
    android:textSize="18dp"
    />
```


```java
//FavouritesActivity

TextView list = (TextView)findViewById(R.id.favourites_list);
String movieString = "";

for(Movie m : myFavourites){
    movieString += m.getTitle() + " " + m.getYear() + "\n";
}

list.setText(movieString);
```

Run the app and it should display a the movie we clicked on. Last thing we need to do is save it back into SharedPreferences.

### We Do: Save the list of favourites (including the new one) back to SharedPreferences

To add a key/value pair to SharedPreferences we need to get a handle to and Editor object and ask GSON to serialize our ArrayList using the toJson method.

We're also going to show a Toast! A toast is like a popup type thing that we can use for confirmations.

```java
//FavouritesActivity

//Save new fave to SharedPref
SharedPreferences.Editor editor = sharedPref.edit();

editor.putString("MyFavourites", gson.toJson(myFavourites));
editor.apply();

Toast.makeText(this, "Movie addded! Hurrah!", Toast.LENGTH_LONG).show();

```

Now our favourites list is being persisted to SharedPreferences. Our work here is done.

### Summary

Have we hit our learning objectives?

- Know what data persistence options there are in Android
- Understand serialization and deserialization
- Be able to store JSON in SharedPreferences
- Be able to implement a Toast
