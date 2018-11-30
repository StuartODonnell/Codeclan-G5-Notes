# Mongo Web App part 2

## Learning Objectives
- Perform a GET, POST, DELETE request on an API using XMLHttpRequest.

## Duration
1.5 hours

# Intro

We’re going to continue with the star wars web app. We have used insomnia to test the back-end and we know it works. So now we can write front-end code (in our `client` directory) to communicate with our back-end API using `XMLHttpRequest`s.

> Give students 5 minutes to read through the `client` start code (`request.js`, `quoteView.js`, `app.js`)

## CRUD - Read

When the website loads, we want to get all of the quotes so that we can render them in the DOM. We want to call a `get` method on our request object, passing it a callback which will be executed when the get request completes. Let's do this at the end of the `app` method.

```js
// app.js
const appStart = function(){
  request.get(getQuotesRequestComplete); // NEW
}
```

Now we need to declare that callback function.

The callback (whenever it is called), will be passed an array of all of our quotes.

```js
  // app.js
  const getQuotesRequestComplete = function(allQuotes)  {
    console.log(allQuotes);
  }
```

Lets now create this `get` method in request.js.

```js
// request.js
Request.prototype.get = function(callback) {
  const request = new XMLHttpRequest();
  request.open('GET', this.url);
  request.addEventListener('load', function() {
    if(this.status !== 200) {
      return;
    }

    const responseBody = JSON.parse(this.responseText);

    callback(responseBody);
  });
  request.send();
}
```

Let's refresh the page and see all the quotes in the browser console.

Our Request class is generic, it will work in the same way for any resourse at any URL. We can reuse this for not just quotes but films or anything we want!

`Request.get` is passing the server's response in to our `getQuotesRequestComplete` callback. Let's use our `quoteView` object to display our quotes in the page.

```js
  // app.js
  const getQuotesRequestComplete = function(allQuotes)  {
    allQuotes.forEach(function(quote) {
      quoteView.addQuote(quote);
    });
  }
```
And we can see it loads. Perfect.

## CRUD - Create

Next we're going to use a POST request to enable users to add new quotes using our front-end.

We have a `<form>` in our HTML. We could add the attributes `method="POST"` and `action="/api/quotes"` but form submission's default behaviour will cause the page to load every time. But we're making a dynamic Single Page Application. We want to use JavaScript to do everything in the background without reloading the page. This gives our app a smoother user experience, our users can keep using the site while requests complete.

Inside `app.js`, we're going to handle the submit button click.

```js
const appStart = function(){
  request.get(getQuotesRequestComplete);

  const createQuoteButton = document.querySelector('#submit-quote'); // NEW
  createQuoteButton.addEventListener('click', createButtonClicked); // NEW
}
```

Now we'll write the `createButtonClicked` callback function. Our first step is to prevent the page from reloading.

```js
// app.js
const createButtonClicked = function(event) {
  event.preventDefault();
  console.log('form submit clicked');
}

const appStart = function(){
  // as before
}
```

If we now click the button it does not refresh the page (Yay!) or submit the form (Oh no!) We will send the data to the server manually.

Now you might ask, why not use a button instead of a form? Buttons don't redirect to a new page by default. That is a good question, but forms do have some nice features that we would like to keep. For example, a well-written forms improves the accessiblity of your website. And a `<form>` with a `<submit>` will, be default, let a user hit the Enter key to submit the form, rather than needing to click the `<button>`.

We need to get the values from the name input box and the quote input box.

```js
// app.js
const createButtonClicked = function(evt) {
  evt.preventDefault();
  console.log('form submit clicked');

  const nameInputValue = document.querySelector('#name').value;
  const quoteInputValue = document.querySelector('#quote').value;
}
```

So lets write a `Request.post` method. It will require a callback and a request body. The body of the response will look exactly like what you POSTed from Insomnia in the previous lesson. Lets define the data for the quote we're going to POST using the values we grabbed from the form.

```js
// app.js
  const nameInputValue = document.querySelector('#name').value;
  const quoteInputValue = document.querySelector('#quote').value;

  const quoteToSend = { // NEW
    name: nameInputValue,
    quote: quoteInputValue
  };
```

Let's call the post method that we are about to write, passing it the `quoteToSend`, along with a callback to execute when it's complete.

```js
// app.js
request.post(createRequestComplete, quoteToSend);
```

Okay lets define the `post` method.

- We open a post request.
- We check for the 201 status code, meaning "Created". We set up our server's CREATE route to respond with a 201 code in the previous lesson.
- We parse the response, and pass it to the callback.

```js
// request.js
Request.prototype.post = function(callback, body) {
  const request = new XMLHttpRequest();
  request.open('POST', this.url);
  request.addEventListener('load', function() {
    if(this.status !== 201) {
      return;
    }

    const responseBody = JSON.parse(this.responseText);

    callback(responseBody);
  });
}
```

Finally we need to stringify the body and then send it to the server.

```js
// request.js
  request.addEventListener('load', function() {
    // as before
  });
  request.send(JSON.stringify(quoteToSend)); // NEW
```

We haven't yet defined this `callback` that `request.post` uses. Let's write it now back in `app.js`. `request.post` passes the newly created quote (that our API server responds with) into this callback. Let's just `console.log` it and check if our Create button is working.

```js
// app.js
const createRequestComplete = function(newQuote) {
  console.log(newQuote);
}
```

> Fill in form and submit it. Check browser console.

Odd, why is it saying undefined. Well actually we need to tell the server what we are passing it, otherwise it doesnt know. Forms do that automatically but now we need to do that bit. Its quite straightforward, if sending content in a body to the server, tell it what it is. In this case, we tell the server that the `'Content-Type'` is `'application/json'`. Back in our post method.

```js
// request.js
Request.prototype.post = function(callback, payload) {
  const request = new XMLHttpRequest();
  request.open('POST', this.url);
  request.setRequestHeader('Content-Type', 'application/json'); // NEW
  request.addEventListener('load', function() {
    if(this.status !== 201) {
      return;
    }

    const responseBody = JSON.parse(this.responseText);

    callback(responseBody);
  });
  request.send(JSON.stringify(payload));
}
```

Once that's `console.log`ing successfully, we can do something a bit more useful. Take the quote and pass it in to our `quoteView.addQuote` method.

```js
// app.js
const createRequestComplete = function(newQuote) {
  quoteView.addQuote(newQuote); // UPDATED
}
```

And now it all works. Nice.

### CRUD - Delete All

#### Task (15 - 20 minutes)

* What should the Delete All button's click event do? (What request method should it call, do we need a new method in request.js?)
* Which HTTP method are we using when we make our request from our front-end ?
* What status code are we expecting in response to a successful deletion? (We specified this in the previous lesson in server.js)
* Are we expecting any data / body in the response from the server?
* What should the callback do if the request is successful? What method on quoteView do we need to call?

#### Task Solution:

First lets define what happens when the button is clicked

```js
// app.js
const deleteButtonClicked = function() {
  request.delete(deleteRequestComplete);
}

const appStart = function(){
  // as before

  const deleteButton = document.querySelector('#deleteButton');
  deleteButton.addEventListener('click', deleteButtonClicked);
}
```

Then in request.js, we create the method we're calling in app.js

```js
// request.js
Request.prototype.delete = function(callback) {
  const request = new XMLHttpRequest();
  request.open('DELETE', this.url);
  request.addEventListener('load', function() {
    if(this.status !== 204) {
      return;
    }

    callback();
  });
  request.send();
}
```

Notice the 204 status code and the fact we do not need to parse any JSON as our server doesnt respond with anything.

Finally, we define our `deleteRequestComplete` callback function to pass into `request.delete`

```js
// app.js
const deleteRequestComplete = function() { // NEW
  quoteView.clear(); // NEW
} // NEW

const deleteButtonClicked = function() {
  request.delete(deleteRequestComplete);
}
```

# Recap:

* What are the benefits of putting our requests into the request.js file?

<details>
  <summary>Answers</summary>
  <ul>
    <li>Reusability of the request methods</li>
    <li>Abstraction of the lower level details of making the request.</li>
  </ul>
</details>

---

* What header needs to be added to the post method? Why do we need it?

<details>
  <summary>Answers</summary>
  <ul>
    <li>Content-Type: application/json</li>
    <li>This is neccessary when sending some JSON data in the body of the request. We have to tell the server the what the contents of the request body are</li>
  </ul>
</details>

---

* What http methods can you pass a body with?

<details>
  <summary>Answers</summary>
  <ul>
    <li>POST</li>
    <li>PUT</li>
  </ul>
</details>
