# Assets and Resources

## Learning Objectives

* Learn what Resources and Assets are in Android Apps
* How to add an image resource to an app
* The difference between Resources and Assets
* When to use Resources and when to use Assets

# Assets and Resources

Every application needs external content, not matter the platform it runs on and mobile apps are no exception. Can you think of some examples of external content your Android application might need?:

* an application icon that is displayed in the app list
* logo bitmap that you want display on your main activity
* an MP3 file that contains a sound effect for your game

Android gives us two major ways to handle external content – Resources and Assets. We will talk about both in this lesson.

## Resources

Let’s start with the first way Android gives us to handle external content – it's called just Resources.

When you create an Android app in Android Studio, one of the directories created by the Android Project wizard is called ```res```. So what is this directory?

The ```res``` directory is a magic directory that is treated in a special way during the Android app build process (which happens every time you modify your application in Android Studio). As the result, resources you put into that directory will have the following special properties:

Each resource is assigned a unique precompiled ID. This ID is defined inside the auto-generated R class. The R class is generated in your application package and you should never try to modify it, but you can and will use it in other classes that you write yourself.

For example, the icon.png file that is located in the res/drawable folder will have a constant unique int value assigned to it in the R class. You can address it in your code as R.drawable.icon

If you think back to the Eight Ball app, we've been using this Id to access elements in our views when we've called the ```findViewById``` method e.g.

```
TextView nameText = (TextView) findViewById(R.id.text_name);
```

Various Android APIs provide extensive support for resource IDs so you will very rarely need to load your resources manually, using streams and parsers. Just look for an operation that supports a resource ID and it will load it internally.

Examples:
 ```TextView.setText()``` – allows you to set a text view value to a text resource, for example defined in the default res/values/strings.xml.
 ```BitmapFactory.decodeResource()``` – loads a bitmap from a resource. This operation wants you to provide a Resources instance too, so here you can use the getResource() method that is included in your activity class.

You can also refer to resources when defining other resources. If you think about it, we've been doing this all the way through our app.

For example, when we created the 'Shake the ball' button in ```activity_details.xml``` (which is a resource) the text we give refers to a string from res/values/strings.xml (the @ sign is used to indicate a resource reference):

```
<!-- activity_main.xml -->

    <Button
        android:id="@+id/shake_button"
        android:text="@string/shake"
        android:layout_marginTop="25dp"
        android:layout_marginLeft="25dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

```


But remember that if you want to store your resources as Resources (in the res folder) and enjoy all the benefits, you have to remember the following rules:

Resources are always stored in a fixed hierarchy. For example, you can’t organize your images into folders. They will always be ```R.drawable.*```
You have to store resources in the correct, predefined subdirectories of res, according to their type. For example, all images go to ```res/drawable``` and layouts to ```res/layout```. If in doubt, consult the Android Dev Guide.

Resource file names must be valid Java identifiers, for example they must start with a letter and may not contain any special symbols. (Android Studio will remind you if you forget.)

***NOTE*** image resource file names can only contain the following characters:

* 'a' - 'z' ***LOWER CASE ONLY***
* 0-9
* underscore

Resource IDs are not typed, so a piece of code where you pass a text resource as an ImageView image will compile and then break at run-time. So be careful.


### Adding Image Resources to the Eight Ball App
So let's add some a new resource to our app. We'll add an image file.

[i]: send students the directory with the 8 Ball image

The first thing you need to do is locate the image(s) you want to import.

[i]: on my machine this was in the ```~/Downloads``` directory.

Select the required image(s) and right(two-fingered)-click and select 'Copy'.

Go to Android Studio, find the ```res/drawable``` directory, right(two-fingered)-click on it and select 'Paste'.

In the pop-up dialog box, make sure that the path displayed ends with ```/main/res/drawable```. If it does, then click 'OK'

The ```res/drawable``` directory should now contain your image(s).

You can now access them as you would for any other image resource.

So now you've had a look at how Resources work, let's have a quick look at Assets.

## Changing our button to use our image

Now that we have an image of an 8-Ball, let's use it in our app so that, instead of clicking a simple button, we now click on an image of an eight-ball.

The first change we need to make is in our ```activity_main.xml``` file. We need to change our ```Button``` to an ```ImageButton```, setting the ```android:src``` attribute to that of the image resource we've just added:

```
  android:src="@drawable/eight_ball"
```

```
<!-- activity_main.xml -->

                                              __                              
<!--<Button                                      \
        android:id="@+id/shake_button"            |    
        android:text="@string/shake"              |
        android:layout_marginTop="25dp"           | <== OLD CODE
        android:layout_marginLeft="25dp"          |
        android:layout_width="wrap_content"       |
        android:layout_height="wrap_content"/> __/


                                              __
    <ImageButton                                 \
        android:id="@+id/shake_button"            |
        android:layout_width="wrap_content"       |  <== NEW CODE
        android:layout_height="wrap_content"      |  
        android:layout_gravity="center"           |
        android:src="@drawable/eight_ball" />  __/

```

There is still one more thing we need to do. In our java file, ```MainActivity.java``` our ```mButton``` instance variable is of type ```Button```. We need to change this to ```ImageButton``` i.e.

```
// MainActivity.java

Button shakeButton;
```

becomes

```
// MainActivity.java

ImageButton shakeButton;
```

And our cast in the call to ```findViewById``` also needs to change i.e.

```
// MainActivity.java

@Override
protected void onCreate(Bundle savedInstanceState) {
  ...
  shakeButton = (Button)findViewById(R.id.shake_button);
  ...
}
```

becomes

```
// MainActivity.java

@Override
protected void onCreate(Bundle savedInstanceState) {
  ...
  shakeButton = (ImageButton)findViewById(R.id.shake_button);
  ...
}
```

## Changing launcher icon

We can even change the launcher icon(the icon you see in the list of apps installed on the device) of our app to use our image resource. This is just a simple change in our main manifest file, ```AndroidManifest.xml```. We only need to change the ```android:icon``` attribute of the ```application``` tag to point to our image resource i.e.:

```
<!-- AndroidManifest.xml-->
<application
        android:allowBackup="true"
        android:icon="@drawable/eight_ball"   <!-- MODIFIED -->
```

## Assets

Basically, Assets are a raw approach to resource management. You can place any files you want into the assets directory of your project:

[i]: our app might not have an ```assets``` folder, but we're not going to code through an example anyway, but just talk about assets.

Then, if you have a reference to any Context subclass, such as an Activity, you can get a reference to the AssetManager instance provided by the platform:

```
    AssetManager assetManager = getAssets();
```

Once you have an AssetManager reference, you can just open a raw InputStream for any asset you put into the assets directory like this:
```
        InputStream stream = null;
        try {
            stream = assetManager.open("generated-keys.bin");
        } catch (IOException e) {
            // handle
        }
```

Of course, you have to close the stream in finally, as in any Java application:

```
        finally {
                  if (stream != null) {
                        try {
                             stream.close();
                        } catch (IOException e) {}
                  }
            }
```

Unlike Resources, Assets can have any filename that is supported by the OS and they can be organized into subfolders in the assets directory.

The *only* thing you can do with an asset is get an input stream. So it doesn't make much sense to store your strings or images in assets. But you can store custom-format data such as input correction dictionaries or game maps.

In fact, Android platform designers took care to support most of your app resource use cases within the Resources framework, so you won’t need to use Assets at all in most typical apps.

## The Difference Between Resources and Assets

So what's the difference between Resources and Assets?

To be honest, it isn't that much on the surface, but generally, you'll use resources much more often than you'll use assets.

The real difference is that anything placed in the resources directory will be easily accessible from your application from the R class, which is compiled by Android. On the other hand,  anything placed in the assets directory will maintain its raw file format and, in order to read it, you must use the AssetManager to read the file as a stream of bytes. So keeping files and data in resources (res/) makes them easily accessible.

## Which Should You Use

The Android API includes a very comfortable Resources framework that is also optimized for most typical use cases for various mobile apps. You should try to get used to using Resources and try to use them wherever possible.

However, if you need more flexibility for your special case, Assets are there to give you a lower level API that allows organizing and processing your resources with a higher degree of freedom.

## Task

Change the launcher icon and/or the button image in the EightBall app to something different. Feel free to search for your own images, but remember the naming rules.
