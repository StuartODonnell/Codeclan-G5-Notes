# CSS Flexbox Grid

### Learning Outcomes

- Build a CSS grid with Flexbox

## Intro

One of the most useful things we can do with CSS is build a grid system for our web app.

Flexbox is a relatively new feature of CSS which makes this easier than before.

Let's build one so you have a reference for future projects.

First let's create the files.

```bash
#terminal

mkdir flexbox_grid
cd flexbox_grid
touch style.css index.html
```

Open index.html and type html:5 and then ctrl+e to make emmet create our boiler-plate html.


Remember to link to our stylesheet!

```html
<!-- index.html -->

<link rel="stylesheet" type="text/css" href="style.css">
```


```html
<!-- index.html -->

<div class="container">
  <div class="box green">1</div>
  <div class="box red">2</div>
  <div class="box purple">3</div>
  <div class="box peach">4</div>
</div>
```

Let's go to our stylesheet now and give our grid a border so we can see it and the box class some styling.

```css
/*style.css*/

.container {
  border: solid 1px black;
}

.box {
  width: 500px
}

.green {
  background-color: lightgreen
}

.red {
  background-color: tomato
}

.purple {
  background-color: plum
}

.peach {
  background-color: peachpuff
}

```

Now let's add some flex to the styling

```css
/*style.css*/

.container {
  border: solid 1px black;
  height: 250px;
  display: flex;
  flex-direction: row;
}

```
Flex direction row is the default but we'll put it in to show exactly what we're doing and for the benefit of a change we'll do in a bit.

Next we'll add the flex property to our boxes.

```css
/*style.css*/

.box {
  width: 10px;  
  flex-grow: 1;
}

```
Flex-grow specifies what amount of space inside the flex container the item should take up.  So at the moment all boxes are taking up an equal amount of space.

```css
/*style.css*/

.green {
  width: 10px;  
  flex-grow: 3;
}

```

We can now see that the green box is taking up 3 times the amount of space of the others.

Another flex property is flex-basis.  When the flex direction is row this refers to the width and when flex direction is column this refers to the height.

So lets refactor our box style so that it takes in flex basis rather than width.

```css
/*style.css*/

.box {
  flex-basis: 10px;  
  flex-grow: 1;
}

```

These two lines can be further refactored to just flex.

```css
/*style.css*/

.box {
  flex: 1 10px
}

```

If we go back to our browser we can see these changes have it displaying exactly like it was before.

## Making it responsive

To make it responsive we use media queries.  You can think of these as break points in terms of the width we give them.

```css
/*style.css*/

@media all and (max-width: 600px) {
  .container {
    flex-direction: column;
  }
}
```

What we're saying here is that this media query is suitable for all media types so screen, print, speech synthesisers and when we are at 600px width the container will display as a column.

You can have as multiple media queries so lets add another one.


```css
/*style.css*/

@media all and (max-width: 400px) {
  .green {
    background-color: darkgreen;
    flex-grow: 1
  }
}
```

And now we have a responsive container which kind of looks like how nav bars display across mobile and website layouts.
