# Authentication Introduction

## Learning Objectives
 - Explain the need for Authentication
 - Recap HTTP request/response cycle.
 - Describe that HTTP is stateless.
 - Describe types of HTTP Authentication and their disadvantages, advantages
 - Basic HTTP Authentication
 - Session Based Authentication


## Introduction

>Draw request response cycle. 

The role of our server is to receive HTTP requests and to respond correctly.  So far in our application how the server responds has not been affected by who is sending the request.  Responses have been the same regardless of who or where the request has come from.


Let's first create a new application to play around with this.

```
# terminal

  rails new auth_play
  cd auth_play  
```

We'll create a get request for an account in our routes

```ruby
# config/routes

  get 'accounts' => 'accounts#index'
```

We now need to create file our accounts_controller.rb file:

```
# terminal

touch app/controllers/accounts_controller.rb
```

```ruby
# app/controllers/accounts_controller.rb

  class AccountsController < ApplicationController
    def index
      accounts = [ { name:'offshore', amount:1000000.00 },{name:'local',amount:0.01}]
      render json: accounts 
    end
  end
```

As we're making an API, it would be nice to be able to tell the app to respond to requests globally with JSON. And we can! But to do it we'll have to install a new gem.

```ruby
# Gemfile

gem 'responders'
```

Now run: 

```
# terminal

bundle install
```

This gives us the ability to add a `respond_to` call into our `ApplicationController`, so that every controller which inherits from it knows to respond using JSON, which means we don't have to worry about rendering anything else.

```ruby
# controllers/application_controller.rb

protect_from_forgery with: :null_session
respond_to :json
```

Lets go to localhost:3000/accounts and make sure its all good.

Run:
```
# terminal

rails server
```

Great. It works!

Now say we're a big-time bank and we don't want everyone to be able to see the details of the accounts.

The response depends on who is asking for it. Are they authorised to get the resource they have requested?  To do this the server needs to authenticate the request.  How can we do this?

## HTTP basic Authentication
The simplest form of authentication is HTTP basic.  Here we ask for the username and password from the user whenever a request is made.  Rails comes with helpers out the box for this.  Let's see it in action.

```ruby
# app/controllers/accounts_controller.rb

http_basic_authenticate_with name: "me", password: "pass"
```

>This goes at the top of the class, before any methods

> Try accessing in Insomnia. Pass authentication details in Auth tab.

With basic authentication the username and password need to be passed with every request.  If we imagine we're trying to get into a protected conference.  

This is like us having to give the secret password everytime we popped outside and tried to get back in.  

There is no way of the security guard knowing we had just been in. HTTP is stateless.  We would have to give our password everytime.

The user will have to input their name and password to make every request, or the client will save the password somewhere and input in every request.  The former is very inconvenient,  can you imagine every time you click a button on twitter or facebook you had to enter your password.  The latter is risky as passwords and usernames are very valuable information to anyone wanting to hack your accounts.

This method also requires sending your password and username together on every request, this will have to be encrypted.

## Session Based authentication
HTTP is stateless.  The server handles each request separately and is executed in it's own context.  If you make two requests quickly one after an other, the server has no idea that they might have come from the same person.

Sessions are a way to get round this.  We can think of sessions like a security pass. When you go to the conference you can then pop out and come back in using your security pass and not have to give your password again.  There are two key steps in most session based security system.

Sign-up.  This is sending your details up so that you are registered.  On the list for the conference.

Sign-In/Log-In.  This is the step that creates your session.  Get the pass to the conference.  You will have to give your details which will be checked against the details you gave in Sign-Up, given they match, the session will be created. You get the pass!

This pass can then be used for whenever you make subsequent requests.  

## Cookies
Typically Cookies are used to manage user sessions.  Cookies are small bits of information that a server can create that can be saved on the client.  They will get passed to the server that created them by the client on every request.  Making it the ideal structure to use as the security pass.

When a user signs in, the server creates a cookie on the client.  When subsequent requests are sent the cookie information is passed. The server can then look at this and authenticate the request from the user that signed-in.

## Why session based authentication
In session based authentication the username - password values are only transfered to create the session cookie.  After that it is only the session information that is passed. 

Gaining access to someone's session cookie can let you imitate them.  However session cookies are given an expiry date,  making them less dangerous to expose than username and password info.

## HTTPS
Whenever we are implementing an application where sensitive information is transfered between client and server,  we need to encrypt the information.  HTTPS does this.  It encrypts in the client in a way that the server knows how to decrypt it.  Keeping it safe from a man in the middle attack.
