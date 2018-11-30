# ListViews

## Learning Objectives

* Add a ListView control to an app
* Display data from an ArrayList in a list
* Access individual items in a listview

## Viewing Lists in Android

We are now going to look at how to display lists of items.

So what do we do to display lists in Android?

## Listviews

To display lists of data in Android we use something called a ListView. A ListView is a view group which displays a list of scrollable items. It is a group of individual views, one for each item in the list.

## Set up project
First of all we need to set up a project for our app, so let's do that now.

```
// setup

launchpad > android studio

Start a new android project

// application settings

Application name: SimpleListViewExample
Company Domain: com.codeclan.example

Select the sdk version version 16 (Jelly Bean)
```

We now need to create a layout called for our main activity:

```
Right click on res
Select new > directory
Directory name: layout

Right click layout
New > Layout resource file
file name: activity_main
Keep LinearLayout
```

So the first thing to do is add a ```ListView``` to our ```activity_main.xml```

```
<!-- activity_main.xml -->

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent">

    <ListView
        android:id="@+id/my_listview"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">
    </ListView>

</LinearLayout>
```

[i]: By default, each entry in a ```ListView``` is displayed as a ```TextView``` control. You can change this, but you need to create a layout for each row in the list.

We now need to add the activity to our manifest file:

```
<!-- AndroidManifest.xml -->

<manifest package="com.codeclan.example.simplelistviewexample"
          xmlns:android="http://schemas.android.com/apk/res/android">

    <application android:allowBackup="true"
                 android:icon="@mipmap/ic_launcher"
                 android:label="@string/app_name"
                 android:supportsRtl="true"
                 android:theme="@style/AppTheme">

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>

</manifest>
```


We now need to create our Java class:

```
Right click Java > <Our Package>  > New > Java Class
Name: MainActivity
```

```
//MainActivity.java

public class MainActivity extends AppCompatActivity {
}
```

Before we do anything we need some data to display in our list. For this lesson, we're just going to have a simple ListArray of strings, so lets create a function which sets this up and add it to our class:

```
//MainActivity.java

public class MainActivity extends AppCompatActivity {

  private ArrayList<String> setUpStringsList() {
          ArrayList<String> stringsList = new ArrayList<String>();

        String[] strings = {
                "Jack Jarvis",
                "Victor McDade",
                "Winston Ingram",
                "Tam Mullen",
                "Isa Brennan",
                "Boaby the Barman",
                "Naveed Harrid"
        };

        for (int i = 0; i < strings.length; i++) {
            stringsList.add(strings[i]);
        }
        return stringsList;
    }
}

```

[i]: might be an idea to send this out via Slack

We can now get on coding the ```onCreate()``` method:

```
//MainActivity.java

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private ArrayList<String> setUpStringsList() {
      ...
    }
  }
```

We now need to create a member variable of type ```ListView``` and initialise it using ```findViewById```

```
//MainActivity.java

public class MainActivity extends AppCompatActivity {
    ListView listView;                                     <== ADDED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.my_listview); <== ADDED
    }
  }
```

## Adapters

So now that we have created our ListView, how do we get the items from our ArrayList into our ListView?

We need to use something called an ```Adapter```. An Adapter pulls content from a data source such as an array or database query and converts each item into a view, which is then placed into to the list.

Android provides us with several subclasses of the ```Adapter``` class depending on the type of data being retrieved. As our data source is an ````ArrayList``` of strings then we will use the ```ArrayAdapter``` subclass.

```
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1,
        setUpStringsList());
```

The ```ArrayAdapter``` constructor takes three arguments:

* the context
* something called ```android.R.layout.simple_list_item_1```. This is is a reference to an built-in XML layout document that is part of the Android OS.
* the data to be displayed. In our case this is the ```ArrayList``` of strings returned by the call to ```setUpStringsList()```.

[i]: if you are using a custom data source, e.g. a JSON string, then you need to create your own custom adapter.

Now that we have an instantiated ```adapter``` object, we need the ListView to use this adapter, so  we need to call the ```setAdapter``` method on ```listView```, passing in ```adapter```.

```
    listView.setAdapter(adapter);
```


```
//MainActivity.java

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.my_listview);

        ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                setUpStringsList());    <== ADDED LINE

        listView.setAdapter(adapter);  <== ADDED LINE
    }

    private ArrayList<String> setUpStringsList() {
      ...
    }

  }
```

## Accessing Individual ListView Items

So far we've managed to display our list of strings,  but what if we wanted to select an item in the list and do something with that data ? How can we do this?

Well, let's try running the app. What happens if we click on an item in the list? It's background colour changes. That's because ListView items are *clickable*.

So the background changes on an item when you click it - wowee!(not!). So what do you think we need to do to make clicking on an item more useful?

CLUE - think back to when we had buttons in an app.

Yes - we need to create some kind of listener for the items click event.

Given the method we have for setting the listener for the button click do you have any idea what the method may be called, and on what object it will be called on?

Well, we will be calling our method on our ```listView``` object for a start.
The method we will be calling will be ```setOnItemClickListener```. This goes inside our onCreate method within the MainActivity class.

```
listView.setOnItemClickListener();
```

Can you think of what we will be need to pass as a paramter to ```setOnItemClickListener```?

We will need to pass it an anonymous class, like we did for the button click. Well if think/look back to when we set the listener for the button click, we passed in a new ```View.OnClickListener``` object so we need to do something similar here.

But we are using a ListView. Well a ListView is actually a subclass of a type of view called an ```AdapterView```. An ```AdapterView``` is a view whose children are determined by adapters. We use an ```Adapter``` to set the items in our listview so that's probably a good place to start. Also just as the View class has a nested class called OnClickListener, the AdapterView class has a nested class called ```OnItemClickListener``` which might suggest that we do something similar to that which we did for the button. In this case we would create a new ```AdapterView.OnItemClickListener()``` and pass that to setOnItemClickListener

```
//MainActivity.java

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

});
```

Now what? Well this OnItemClickListener object has an onItemClick method which we will want to use, but will we want to use this? NO. So what do we have to do? Override it.

```
public void onItemClick(AdapterView<?> parent, View view, int position, long id)
```

* ```parent``` - the AdapterView where the click happened. In our case ``listView```
* ```view``` - the view within the AdapterView that was clicked (this will be a view provided by the adapter) In this case, the view we created for the row
* ```position``` - the position of the view in the adapter.
* ```id``` - the row id of the item that was clicked.

```
//MainActivity.java

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
```

So our ```onCreate``` method should now look something like:

```
//MainActivity.java

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.my_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, setUpStringsList());

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new                        
          AdapterView.OnItemClickListener() {                        
            @Override                                                
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                                 
                //TODO: add code here                                
            }                                                        
        });
    }

    private ArrayList<String> setUpStringsList() {
      ...
    }
  }
```

So what do want to do first in this function? Well since we want to look at an particular item in the list, then we want to get the data for the item we have just clicked. So how do you think we would do that?

Well first of all, what kind of object do we want? A String.

```
    String selected;
```

So where do you think we might get the data from? From the relevant item in the list.

To get the item selected in the list we call the```getItemAtPosition()``` method, passing in the ```position``` i.e.:

```
String selected = (String)listView.getItemAtPosition(position);
```

So let's also add a log message to show the item selected:

```
Log.d("ListView:", selected + " selected");
```

So our class should now look something like:

```
//MainActivity.java

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.my_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, setUpStringsList());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)listView.getItemAtPosition(position);
                Log.d("ListView:", selected + " selected");
            }
        });
    }

    private ArrayList<String> setUpStringsList() {
      ...
    }
  }
```

Try running the app and clicking item in the list. The items should appear in the log output.
