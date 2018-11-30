# CodeClan Magic 8-Ball - Using Models

## Learning Objectives

* Identify model(s)/class(es) required to store our answers
* Revisit MVC Pattern

## CodeClan Magic 8-Ball

At the moment, the code our app uses to get an answer to our question from a list of answers is all done within the button's onClickListener event. It works fine, but, to be honest, it's a wee bit smelly. At the moment, we've only got a couple of answers, but what if we were getting our answers from a huge list of answers, or even from an API?

It makes much more sense to use a model for the answers.

## MVC Pattern

[i]: Draw MVC skeleton of app on the board. Remind them that this is how things were done in Sinatra.

* M - Models
* V - Views. In our Android App these are called 'Activities'
* C - Controllers/Java for Activities

[i]: If we are following this pattern, then it's better to have 'thin' controllers, where all the business logic (or as much is possible) is done within the models.

### Models/Classes

So let's get on with creating a model for our answers. This is just a review of some of the stuff you covered last week in Java. We are going to need a model to store our answers and to get back an answer when we shake the ball.

## Create an Interface

There are many different ways we can get the answers. We may have a simple list of answers, or we may get them from an API. But whatever way we get them, and in whatever type of model they are stored, we need to be able to do the following:

* set up our answer source
* get an answer to display

[i]: possibly write these on the board as a reminder for later

This forms a 'contract' for the model we use to store our answers. Thinking back to last week's Java lessons, what can we use to describe this 'contract' in our code? An ***interface***.

This interface will be implemented by whatever model(s) we use to store our answers and get them from, and so any model we use ***MUST*** implement the methods declared in the interface.  

So lets create our interface. We do this by right-clicking on our package (example.codeclan.com.eightball) directory and selecting ```New->Java Class```.

From the ```Kind``` dropdown menu, select 'Interface'

Now we need to supply an name for our new interface. Our interface concerns the answer we get, so we could call it ```Answer```, but we'll follow the Java coding convention, which uses the ```...able``` , so we'll call this interface ```Answerable```.

This should give us the following:

```
//Answerable.java

public interface Answerable {
}
```


[i]: note that in the Android Studio project window, the icon for the file is not a 'C' but an 'I'.

So what methods do we want to declare in our interface? ([i]:Going back to our list on the board) we need to set up the answers. Let's call this method ```setUpAnswers()```. Do we want it to return anything? NO - we just want it to set up the list of answers and be done with it, so it will be a ```void``` function.

```
    void setupAnswers();
```

So now that we've declared a method to set up the answers, we now need one which will get an answer from the list.

```
    String getAnswer();
```

So now we have an interface which declares two functions:

```
//Answerable.java

public interface Answerable {

    String getAnswer();

    void setupAnswers();
}
```

## Creating a base class

So now that we've got an interface, we need to implement its methods in a class. So thinking about our app, how do we want to store our answers? What about a list of strings. So we could create a class which has an ArrayList of strings and implement the required methods.

BUT. There are various ways in which we could set up our list of strings. We might populate it from a simple array of strings, or we might be parsing JSON we get from an API call. So what we could do is have a base class, which implements most of the required methods, and have a sub-class which implements the setup method.

[i]: draw diagram on the board and keep referring back to it

So lets create our abstract class. So right-click on our package (example.codeclan.com.eightball) directory and selecting ```New->Java Class```. Let's call it ```Answers```.

We need to make it abstract and state that it implements the ```Answerable``` interface:

```
//Answers.java

public abstract class Answers implements Answerable {

}
```


Add an ArrayList of strings to store all our answers
```
//Answers.java

public abstract class Answers implements Answerable {
    protected ArrayList<String> answers;
}
```


Add default constructor.

```
//Answers.java

public abstract class Answers implements Answerable {
    protected ArrayList<String> answers;

    public Answers() {
        answers = new ArrayList<String>();
    }

}
```

Add a second constructor which allows us to create a new instance and passes in an ArrayList of strings:

```
//Answers.java

public abstract class Answers implements Answerable {
    protected ArrayList<String> answers;

    public Answers() {
        answers = new ArrayList<String>();
    }

    public Answers(ArrayList<String> someAnswers) {
        answers = someAnswers;
    }
}
```

Add a method to get all the answers. It should return an ArrayList of type ```String```. All this function needs to do is return whatever is stored in the ```answers``` member of the Answer class so we could write it like this:

```
public ArrayList<String> getAnswers() {

    return answers;
}
```

But this is not really good practice. Because ArrayLists are always mutable i.e. changeable, so if you simply return ```answers``` then you're really giving the calling function full access to it and so you're actually letting it change a private class member (oh no, no, no!)

So, to prevent this, rather than returning the list, we return a ***copy*** of the list. We can do this quite easily by simply calling the ArrayList constructor and pass in an existing list, in our case, ```answers```

```
//Answers.java

    public ArrayList<String> getAnswers() {
        return new ArrayList<String>(answers);
    }
```

Next add a method to add an answer to the list. We can use the ArrayList ```add``` method:

```
//Answers.java

    public void add(String answer) {
        answers.add(answer);
    }
```

Add a method to get the size of the list:

```
//Answers.java

public int getLength() {
    return answers.size();
}
```

We want to get a particular answer in the list. We can use it's position in the list, like an array index. So we can use the ArrayList ```get``` method, passing it in a number.

```
//Answers.java

    public String getAnswerAtIndex(int index) {
        return answers.get(index);
    }

```

Finally, we can add a method to actually get a random answer from the list. This will implement the ```getAnswer``` method declared in our interface.

```
//Answers.java

    public String getAnswer() {
        //code goes here
    }
```

So how do we want to get our random answer:

* get a random number between 0 and the size of the list
* get the entry in the list at that position in the list and return it

So first of all, we want to generate a random number. We begin by creating an object of the Java ```Random``` class.

```
//Answers.java

    public String getAnswer() {
        Random rand = new Random();
    }
```

Next, we need to get the size of the list, by calling the ```getLength``` method we added earlier:

```
//Answers.java

    public String getAnswer() {
        Random rand = new Random();
        int listSize = getLength();
    }
```

We then need to get a random number between 0 and the size of the array. We can do this using the ```nextInt``` method from the ```Random``` class. We pass in the size of the list:

```
//Answers.java

    public String getAnswer() {
        Random rand = new Random();
        int listSize = getLength();
        int index = rand.nextInt(listSize);

    }
```

Now that we have a random number we can get the item in the list at our 'random' position by calling the ```getAnswerAtIndex``` we implemented earlier. The string returned by ```getAnswerAtIndex```is what our ```getAnswer``` method will return i.e.:

```
//Answers.java

    public String getAnswer() {
        Random rand = new Random();
        int listSize = getLength();
        int index = rand.nextInt(listSize);
        String answer = getAnswerAtIndex(index);
        return answer;
    }
```

We still have one method to implement to fulfill the 'contract' set out in our interface - the ```setupAnswers``` method. We are not going to implement it here, but will leave that to subclasses of ```Answer```. They will have to implement this method or else there will be a compilation error. To do this, we make the method abstract, just as we do when declaring a superclass i.e.:

```
//Answers.java

    abstract public void setupAnswers();
```

So the completed class should now look something like this:

```
//Answers.java

public abstract class Answers implements Answerable {

    protected ArrayList<String> answers;

    public Answers() {
        answers = new ArrayList<String>();
    }

    public ArrayList<String> getAnswers() {
        return new ArrayList<String>(answers);
    }

    public String getAnswerAtIndex(int index) {
        return answers.get(index);
    }

    public int getLength() {
        return answers.size();
    }

    public void add(String answer) {
        answers.add(answer);
    }

    public String getAnswer() {
        Random rand = new Random();
        int listSize = getLength();
        int index = rand.nextInt(listSize);
        String answer = getAnswerAtIndex(index);
        return answer;
    }

    abstract public void setupAnswers();
}

```
