# Action Pack

 - 'From request to response'

2hrs

## Learning Objectives
 - Understand the role of ActionPack
 - Create a rails application using ActionPack functionality.
 - Describe the role of
  - Router
  - Controllers
 - Create a static application using ActionPack responding with json

## Introduction
Handling incoming HTTP requests and creating the response. This is the cycle that web frameworks are created to handle.  In Rails it is the router, controllers and views role to handle this. ActionPack is the name given to this part of Rails.

https://github.com/rails/rails/tree/master/actionpack

Let's create a new application to see action pack in action.
We're going to create an application that will return a list of animals.

```
# terminal

  rails new animals
  cd animals
  subl .
  rails server
```

We would like to be able to go to localhost:3000/animals and see a list of animals.

Rails will tell us what is missing.

"No route matches [GET] "/animals""

It also give us a link to the documents.

## Routing

The router is the first object we control that the request comes into contact with.
The routers role is is to recognise the URL and action of the HTTP request and dispatch it to a controller action.

> Draw single router and Controller

Our application is telling us that there is no route defined for a GET to '/animals'.

Let's go and tell our router about this.

goto /config/routes.rb

There are lots of commented out examples of ways to set up routes.

We're going to setup a regular route.  We want to make a route for

GET /animals

```ruby
# config/routes.rb

  get 'animals' => 'animals#index'
```

What happened here.  get is just a method, which takes in a hash.

```ruby
# config/routes.rb

  get( { 'animals' => 'animals#index' } )
```

Ruby allows us to remove the brackets for calling a method and passing in a hash.
As long as it's the last method.

The router has dispatched the request to be handled by an AnimalsController.  We haven't made this yet.  We'll do that next.

# Controller
MVC - Model View Controller.

The router determines which controller to use.  The controllers job is to make sense of the request and to produce the appropriate output.

Let's generate the controller. Rails does give command line tools to help creating files,  but we simply just need to create a controller here.  Let's do it manually.

!!!! Notice that controller names are plural !!!!

```
# terminal

  touch app/controllers/animals_controller.rb
```

We now need to create the class of AnimalsController.  This will inherit (discuss) from the the application controller. Giving it this out the box controller functionality.  

```ruby
# app/controllers/animals_controller.rb

  class AnimalsController < ApplicationController
  end
```

localhost:3000/animals

We now have a different error.  Unknown action. The controller has been found but the action index is not defined.   Actions are defined as methods on the controller.  Let's define the index action.

```ruby
# app/controllers/animals_controller.rb

  class AnimalsController < ApplicationController
    def index
    end
  end
```

A new error. Great!. The router dispatched the request to the controller.  The controller expects there to be a template defined. In the case of our API server, we want it to render json. If we wanted the view to be HTML, then we would create an embedded ruby file (index.html.erb) in the views/animals folder. Then we would develop like we would when we used Sinatra.

What we would like is to display a list of animals.  Let's create a list of animals in our controller and render it out as json.

```ruby
# app/controllers/animals_controller.rb

  class AnimalsController < ApplicationController
    def index
      @animals = [ {name: "Tiger"}, {name: "Snow Leopard"} ]
      render :json => @animals
    end
  end
```

Back to localhost:3000/animals and we should now see our Tiger and Snow Leopard.

When using Rails as an API we also need to alter the application controller. This is to protect again CSRF attacks:

"an attack outlined in the OWASP Top 10 whereby a malicious website will send a request to a web application that a user is already authenticated against from a different website. This way an attacker can access functionality in a target web application via the victim's already authenticated browser. Targets include web applications like social media, in-browser email clients, online banking and web interfaces for network devices"
[https://www.veracode.com/security/csrf/] (https://www.veracode.com/security/csrf/)

```ruby
# app/controllers/application_controller.rb

  protect_from_forgery with: :null_session
```

We should see the JSON being return when going to http://localhost:3000/animals

# Exercise
- Make a new api app of your choice with hard coded data
