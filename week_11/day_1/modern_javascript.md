# Modern JavaScript

## Learning outcomes

* learn about the changes in JavaScript
* learn the limitations of the new syntax
* introduce how to overcome the limitations

## ES5, ES6, ES2015, what...?

In the beginning, when the Internet was formless and desolate, Internet Explorer reigned supreme and new versions of browsers were released every couple of years.  Web pages were mostly static content, but gradually as the web became more mainstream and less the preserve of academics and scientists, more "dynamic" features were incorporated, and JavaScript started being used more and more.

When the "modern" browsers came on the scene, namely Google Chrome, Safari, and Firefox, they had very short release cycles - weeks and months instead of years.  New features could be incorporated very quickly.  The Node.JS team came up with the idea of using JavaScript as a more general purpose language and extracted the V8 JavaScript engine out of Chrome, and started using it on the command line.

Suddenly JavaScript was getting a lot of attention.

In the last three or so years, there has been an explosion of new features suggested for the language to make programmer's lives easier and to improve the general purpose usefuless of the language.

As we covered before, the name of the JavaScript specification is "ECMAScript" (or ES for short) and the version that existed prior to this explosion was called ES5.  The committee in charge managed to agree on a subset of features, and these made it into the new version, which was released in 2015 and is the 6th edition of the specification - is therefore unofficially known as ES6 or ES2015.

People continue to use features that haven't quite made it into the official spec yet, features sometimes categorised as "ES6+" or "ES next".

## Limitations

Not every browser supports all of the ES6 features.  Chrome, Safari and Firefox keep themselves up-to-date automatically, so should support most new things.  Microsoft's new Edge browser is much better than Internet Explorer, but is sometimes still lacking.

Lots of people, especially in corporates and government, are still stuck on old versions of Internet Explorer - these barely even support HTML and CSS properly never mind latest JavaScript features.

So generally, unless you're running on an environment you can control such as in Node.JS or writing apps specifically for e.g. Chrome, you can't use the nice new features. (_directly_...)

## Overcoming the limitations

But the new features are much nicer!  So, as we'll see a bit later in the course, various clever people have written various tools which can covert modern ES6+ into old-fashioned ES5, so that it'll still run in your Grandma's browser.

