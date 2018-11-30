# Learning Objectives
* Make a basic map using Leaflet
* Add markers
* Add click event

# Mapping and Geolocation

Maps and geolocation has become a big thing - almost every website you visit will have a map or a "share location" feature on it. Today we are going to look at how we can utilise this in our own apps.

# Google maps

> Hand out start point

The most popular solution for maps on the web is Google Maps. It was the first and is probably still the best. However, as of 16th July 2018, Google Maps requires a credit card to access its API, so we're going to use a free alternative - OpenStreetMap - and a JavaScript library called Leaflet that makes working with maps in the browser dead simple.

First we need to include a link to the script that gives us access to the Leaflet library. We also need to import some default styles for the map from Leaflet's website. Let's add these inside the `<head>` tags.

```html
<!-- index.html -->

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.1/dist/leaflet.css" integrity="sha512-Rksm5RenBEKSKFjgI3a41vrjkw4EVPlJ3+OiI65vTjIdo9brlAacEuKOiQ5OFh7cOI1bkDwLqdLw3Zg0cRJAAQ==" crossorigin=""/>
<script src="https://unpkg.com/leaflet@1.3.1/dist/leaflet.js" integrity="sha512-/Nsx9X4HebavoBvEBuyp3I7od5tA0UzAxs+j83KgC8PU0kgB4XiK4Lfe4y4cgBtaRJQEIFCW+oC506aPT2L1zw==" crossorigin=""></script>
```

Once this is added we should be able to go into the browser console and type `L` and see the Leaflet interface object with all its methods that are now available to us.

We also need a div for our map to live inside which we will add inside the body tags.

```html
<!-- index.html -->

<div id='main-map'></div>
```

At the very least, this div must have a height or it won't display. Let's go to our `style.css` file in the `public` folder and give our map a height.

```css
/* main.css */

#main-map {
  height:300px;
}
```

There's a script in the public folder called `app.js` where we are going to start putting our code. Again we need to add a link to this in our HTML. Let's add it after the Leaflet link (order is important!).

```html
<!-- index.html -->

<script src='app.js'></script>
```

Now we are ready to add our map! We need to make sure our map loads AFTER the DOM is ready and the elements we expect to be there exist. In our case, it's our "map" div.

```js
//app.js

window.addEventListener('DOMContentLoaded', app);
```

This calls the `app` function when the window has loaded so any code we write in there is going to execute when the initial HTML document has been completely loaded and parsed.

Let's define this function.

```js
//app.js
//PUT THIS ABOVE window.addEventListener !!

const app = function() {

}
```

To begin with, all we want is our script to create a map. We can do this by calling on the `L` object our Leaflet script has given us access to, which has a method we can call on it by the name of `map()`. This method takes in an argument, which is the id of the html element we want the map to render in. Like so:

```js
//app.js

const app = function() {
  const map = L.map('main-map');
}

```

(`L.map()` can also handle taking the actual element we want to render to if we grab it from the DOM via `document.getElementById('main-map')`, but the convention for Leaflet is just to pass the element's ID)

And now if we look in our browser we shall see... something. A gray square, with the Leaflet logo in the corner. So we know it's doing something!

For our map to work, there's a couple more steps we need to take - firstly we tell the map where in the world we want it to centre on, using the `setView()` method. Handily, many Leaflet functions return a map object that we can then chain other function calls on to, like this:

```js

//app.js

const app = function() {
  const map = L.map('main-map').setView();
}

```

`setView()` takes in two arguments - an array of coordinates in the format of `[latitude, longitude]` and a `zoom` - which defines how closely zoomed into the map we are, with 1 being so far out you can see the whole map of the globe, and 20 being so close that you could look up just now, wave at the user and they would probably see you. Let's set up our map so to begin with we'll see the whole world in our hands:

```js

//app.js

const app = function(){
  const map = L.map('main-map').setView([0,0], 1);
}

```

And the final step to get a map on screen is to give our map what Leaflet refers to as a `TileLayer`. Tiles are essentially how online maps handle displaying the world map - they cut them up into smaller squares so they only have to concentrate on showing the area the user is specifically looking at at any given time. A `TileLayer` is how Leaflet creates and defines the appearance of the tiles it uses. We can create a TileLayer in our app by using another built-in method attached to our Leaflet object called... TileLayer! This takes in a url pointing to a tile server, and luckily we've found one for you.

```js

//app.js

const app = function(){

  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")//ADDED

  const map = L.map('main-map').setView([0,0], 1);
}

```

And now that we've created our TileLayer, we can use it on our map by using the `addLayer` method on our map, which we can chain with our other map methods like before.


```js

//app.js

const app = function(){

  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")

  const map = L.map('main-map').setView([0,0], 1).addLayer(osmLayer);//ADDED
}

```

And now we should be able to see a map... of the WHOLE WORLD! Great!

Although we're zoomed all the way out, this map is centered on the coordinates 0,0 - where the equator meets the prime meridian (for the geography-minded among you, we're talking in Gulf of Guinea in the Atlantic Ocean, about 380 miles south of Ghana and 670 miles west of Gabon, if memory serves.)

We've seen in handing the tilelayer to the `addLayer` method that we can use variables for our arguments being passed into our methods. With that in mind, how would we do this for the coordinates and zoom being passed to `setView`?

[TASK:] Find out the coordinates for a place of interest and centre the map on it.

[SOLUTION:]
```js

//app.js

const app = function(){

  const coords = [-50.6067956, 165.968396];//ADDED (points at Disappointment Island)
  const zoom = 13;//ADDED
  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")

  const map = L.map('main-map').setView(coords, zoom).addLayer(osmLayer);//ALTERED
}

```

Great! So we've now got working code that can render a map in our browser - fantastic! But why are we currently looking at my home from home, Disappointment Island?

We're DISAPPOINTED because this code isn't REUSABLE. But we can fix it, we're programmers!

Let's make a wrapper function - we'll abstract this code out so that we can easily reuse it and make as many maps as we want! This will hold the Leaflet Map we create and also methods to interact with it. We'll make this in a separate script called `mapWrapper.js`.

```
# terminal

touch public/mapWrapper.js
```
We will need to add this to our index in a script tag, above `app.js`.

```html
<!-- index.html -->

<script src='mapWrapper.js'> </script>
```

And now let's take everything we've got in our `app.js` that renders the map, and move it to `mapWrapper.js`, like so:

```js
//mapWrapper.js

const MapWrapper = function(coords, zoom) {
  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
  this.map = L.map('main-map').setView(coords, zoom).addLayer(osmLayer);
}
```

Which means we can go back and refactor `app.js` to call our new wrapper function by passing in the coordinates and zoom:

```js
//app.js

const app = function() {
  const coords = [-50.6067956, 165.968396];
  const zoom = 13;
  const mainMap = new MapWrapper(coords, zoom);
}
```

This is still just rendering to one container though - `'main-map'`. Let's make one more tweak so that we can tell our map to render in whatever container we choose.

```js
// app.js
function app() {
  const containerID = "main-map";//NEW
  const coords = [-50.6067956, 165.968396];
  const zoom = 13;
  const mainMap = new MapWrapper(containerID, coords, zoom);//NEW
}
```

Then we tell our MapWrapper constructor to expect to be passed an element ID. Inside the constructor, we can pass this element in and draw our map inside that element:

```js
//mapWrapper.js

const MapWrapper = function(containerID, coords, zoom) {//NEW
  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
  this.map = L.map(containerID).setView(coords, zoom).addLayer(osmLayer);//NEW
}
```

## Markers

Wouldn't it be nice to put a marker on the map to show something we are interested in? Let's do it. The easiest one to add is a marker for the centre of the map. Remember how we pulled the lat and long into variables? They're going to come in useful now.

We can use the Leaflet `marker` method, which only needs one parameter, an array of latitude and longitude. Then we can add it to our map with the `addTo` method:

```js
//mapWrapper.js
const MapWrapper = function(containerID, coords, zoom) {//NEW
  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
  this.map = L.map(containerID).setView(coords, zoom).addLayer(osmLayer);

  L.marker(coords).addTo(this.map); // NEW
}
```

Actually, this would be better encapsulated in its own method, rather than being stuck in the constructor. That way, we can add a marker whenever we like, not just when the map is created:

```js
// mapWrapper.js
MapWrapper.prototype.addMarker = function(coords) {
  L.marker(coords).addTo(this.map);
};
```


Let's add a marker to the centre of our map.

```js
//app.js

mainMap.addMarker(coords);
```

There is a handy website we can use to get the lat longs for things http://www.latlong.net/ let's use this to add interesting things to our map.

[TASK]: Go off and find an interesting place nearby and add a marker to it.

## Click events

There are many map events that we can hook into. The simplest one is the click event on the whole map. Leaflet uses method called `on` to handle events. This method takes in a name of the event to listen for, and a callback function to be executed when the event fires.

We'll attach this event listener in the MapWrapper constructor, and log out the event so we can see what we're working with:

```js
//mapWrapper.js

const MapWrapper = function (element, centre, zoom) {
  const osmLayer = new L.TileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");

  this.map = L.map(element).addLayer(osmLayer).setView(centre, zoom);

  this.map.on("click", function(event) {
    console.log(event);
  });
}
```

If you expand this event object in DevTools, you'll see that it has a `latlng` property, which we can use to add a new marker. We just need to call our `addMarker` method and pass it this `event.latlng` object:


Do you think you can combine this with the add marker function to add a marker at the location where someone clicks?

Hint: You need to be careful with the scope of "this"...

> Give them 15 mins to try this.

```js
//mapWrapper.js

this.map.on("click", function(event) {
  this.addMarker(event.latlng);
});
```


In a callback, "this" is set to the element that triggered the callback, not as we would like here, to be the object that the method was defined on.  This has cause many Javascript programmer to table flip.

We can overcome this using the bind method.  This is a method on a function that clones the function it was called on, but with this explicitly set to what we pass in.

```js
//mapWrapper.js

this.map.on("click", function(event) {
  this.addMarker(event.latlng);
}.bind(this));
```

Alternatively we can use an ES6 arrow function:

```js
this.map.on("click", event => this.addMarker(event.latlng));
```

Great! We can now add a marker any time we click on the map.
