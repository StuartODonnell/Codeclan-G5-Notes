# Http Recap

We are about to look at our first adventure into HTTP requests. If you recall, HTTP is a way that a client (browser) can communicate to a server.

There are many kinds of HTTP request we can make that have different behaviours, but for today we are going to look at the GET method.

You probably didn't know it at the time, but you have been using get requests all the time when you use your browser.

http://google.com -> get request
http://amazon.co.uk -> get request

GET is a method which a client can communicate to a server and ask for some resource. The important thing about a GET is that it has no side effects - nothing should be updated or change. It simply "gets" something - a file, a web page, an image etc. 

## What is a web framework?

A web application framework, or web framework, supports the development of software which is used on the internet. Every time we wanted to create a web app we would need to setup a server. This is time consuming and having a framework that sets this up and is tested is hugely efficient.

Then we need work out some system where we could handle requests. This is known as routing.

When we go to www.bbc.co.uk/news this request responds with all the latest news. If we then go to /sport we get the latest sport. These are two seperate routes that GET different information/resources. 

You can imagine the pages and routes underneath these sections on the BBC website and so on. How do we deal with these routing issues? A good framework will be able to handle this which allows us to really focus on creating solid programs that are well tested.
