# Mongo Web App

## Learning Objectives
- Understand how to use Mongo in our JS Server
- Perform a GET request on an API which uses a Mongo database.
- Perform a POST request on an API which uses a Mongo database.
- Perform a DELETE request on an API which uses a Mongo database.
- Perform a PUT request on an API which uses a Mongo database.

## Duration
2 hours

# Intro

We’re going to build a simple list application that allows you to keep track of things within a list (like a Todo List for example).

Well, a todo list is kind of boring. How about we make a list of quotes from Star wars characters instead? Awesome, isn’t it?

In this lesson we'll be working server-side. We're creating an Express server which will receive requests and respond by sending JSON data to the user. We're writing our own API!

The quotes data will be stored in MongoDB. Our Express server will have a route for each CRUD operation, and will perform the appropriate action on our database.

The start point provides a UI with a form. We'll use this front-end in the next lesson to make requests to the API we create in this lesson, meaning we will have built a full-stack JavaScript app.

- Hand out start code

Lets start by installing the dependencies we will need.

```
# terminal

npm install
```

Lets start our server and webpack.

```
# terminal

npm start
```

Open a new tab in terminal.

```
# terminal

npm run build
```

Make sure `mongod` is running somewhere as well.

### Connecting to the DB

As we are going to store our quotes into Mongo DB we have to install MongoDB npm library if we want to access our database in our server.js.

```
#terminal

npm install mongodb --save
```

Once installed, we can connect to MongoDB through the `MongoClient`‘s `connect` method.

Start by requiring the `MongoClient` then setting up our connection. We will also move our server start code into this `MongoClient.connect` block so that the server only starts when connected to the mongo DB. This means that if we have no database then our server won't start.

```js
// server.js

const MongoClient = require('mongodb').MongoClient

MongoClient.connect('mongodb://localhost:27017', function(err, client) {
  if (err) {
    console.log(err);
    return;
  }
  const db = client.db("star_wars");

  console.log('Connected to database');

  server.listen(3000, function(){
    console.log("Listening on port 3000");
  });
})
```

Wait... We haven't created a star wars database in Mongo?? We don't have to. When Mongo tries to connect to the star_wars db it will create it if it doesn't exist!

We’re done setting up MongoDB. Now, let’s add some quotes to our database.

### CRUD - Create

Our CREATE operation will be performed when a POST request is received by the server.

We can create / access a collection by passing in the name of the collection to MongoDB’s `db.collection` method. We can then save an entry into MongoDB with the `save` method.

```js
// server.js

// MongoClient.connect above

// CREATE route
server.post('/api/quotes', function(req, res){
  const quotesCollection = db.collection('quotes');
  const quoteToSave = req.body;
  quotesCollection.save(quoteToSave, function(err, result){
    if (err) {
      console.log(err);
      res.status(500)
      res.send();
    }

    console.log('saved to database');
  });
});
// server.listen below
```

Let's test that this is working. What tool can we use to test HTTP methods against our server? Insomnia!

Open insomnia and create a POST request to `http://localhost:3000/api/quotes` and put an object with two key-value pairs in the body for name and quote.

```json
# Inside insomnia post JSON body
  {
    "name": "Yoda",
    "quote": "Best kind of teacher, failure is."
  }
```

You will notice that we get our console log 'saved to database' but insomnia hangs.

Once we’re done saving, we have to respond to the user (or they’ll be stuck waiting forever for our server to reply). In this case, we will be responding with the resource that we've just created. We can access this from the `result` object by first getting an array of the 'operations' that we have performed, then (since we've only done one thing) accessing the first element of that array.

```js
// server.js

// CREATE route
server.post('/api/quotes', function(req, res){
  const quotesCollection = db.collection('quotes');
  const quoteToSave = req.body;
  quotesCollection.save(quoteToSave, function(err, result){
    if (err) {
      console.log(err);
      res.status(500);
      res.send();
    }

    console.log('saved to database')
    res.status(201); // ADDED
    res.json(result.ops[0]); // ADDED
  });
});
```

> We're using the `/api/` prefix on our paths to indicate that this route returns raw JSON data and is therefore primarily for consuption by computer programs rather than human users / customers.

When we get this response in Insomnia, we can see our object now has an `"_id"` key. This has been added by the database.

Now that our CREATE action is working, let's make another POST request from Insomnia to add a second quote to our database.

### CRUD - Read

Let's now create a route for getting the whole collection of quotes back from the database.

We can get the quotes from Mongo by using the find method that’s available in the collection method.

Our READ route will use `server.get`, and will return the JSON array of all quotes when hit.

```js
// server.js

// READ route
server.get('/api/quotes', function(req, res) {
  const quotesCollection = db.collection('quotes');
  quotesCollection.find().toArray(function(err, allQuotes){
    if(err) {
      console.log(err);
      res.status(500)
      res.send();
    }

    res.json(allQuotes);
  });
});
```

Lets check in insomnia. Make a GET request to `http://localhost:3000/api/quotes` and see all the quotes in the response.


### CRUD - Delete

#### Task

See if you can figure out how to delete all quotes. Remember to use the DELETE request type in insomnia to check it. Look at the documentation for mongodb to see how to delete all documents in a collection. [Hint: Use the deleteMany() method](http://mongodb.github.io/node-mongodb-native/3.0/api/Collection.html#deleteMany)

#### Task Solution:

```js
// DELETE route
server.delete('/api/quotes', function(req,res) {
  const filterObject = {};
  const quotesCollection = db.collection('quotes');
  quotesCollection.deleteMany(filterObject, function(err, result){
    if(err) {
      res.status(500);
      res.send();
    }

    res.status(204);
    res.send();
  });
});
```


### CRUD - Update

Finally we need to implement a route to update a quote.

The HTTP method will be **PUT** and we'll use the MongoDB Driver `update` method.

`update` will take 3 arguments

1. The id of the document to update
2. The request body representing the new document
3. A callback

The pattern is similar to our delete route. Instead of passing an empty object as the 'filter' argument, we pass an object with the `ObjectID` for the database entry that we want to update.

```js
// server.js

// We need to require a reference to the ObjectID class from mongodb
const ObjectID = require('mongodb').ObjectID;

// UPDATE route
server.put('/api/quotes/:id', function(req, res){
  const quotesCollection = db.collection('quotes');
  const objectID = ObjectID(req.params.id);
  const filterObject = {_id: objectID};
  const updatedData = req.body;
  quotesCollection.update(filterObject, updatedData, function(err, result){
    if(err) {
      res.status(500);
      res.send();
    }

    res.status(204);
    res.send();
  });

});

```

Let's check it in Insomnia.

Make a PUT request to `http://localhost:3000/api/quotes/5a5dfa9d5efb55d9fd6d1483`

The big long number must be a valid `_id` of a document in **your** database so you'll have to find the `_id` of the document you want to update. Do a GET request to retrieve all your documents and copy the `_id` of an existing document. (If you deleted them all, add one now!)

The body will look something like this

```json
{
  "name": "Yoda",
  "quote": "Some great new quote, I've learned."
}
```


# Recap:

* What is the name of the npm package we need to use so that our server.js can talk to mongodb?
* Why should we connect to our database first before the server listens?
* What should we be doing if we get an error object from our mongo actions?
* What class did we need to require in order to do an update?
