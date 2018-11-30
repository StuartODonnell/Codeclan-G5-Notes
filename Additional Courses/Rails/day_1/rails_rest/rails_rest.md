# Rails Rest

## Learning Objectives
 - Be able to implement the RESTful routes in Rails

# Intro
  We want to extend our animals application so that we can see specific animals (show).  And add animals (new create).  We are no longer going to be concerned with rendering html. We are going to focus on handling/serving JSON. 

  Let's remove the code that returns html.

```ruby
# app/controllers/animal_controller.rb

  class AnimalsController < ApplicationController
    def index
      animals = Animal.all
      render :json => animals
    end
  end
```

# Show

What url will we need to go to show the animal with id 1 according to the rest convention?

http://localhost:3000/animals/1

How can we set up the route for this?
    
```ruby
# config/routes.rb

  get 'animals/:id' => 'animals#show' # NEW
```

*Debugging aside*
One of the easiest ways to debug in rails is to just raise an error.  We can then access an interactive console in the browser.
  
```ruby
# app/controllers/animals_controller.rb
  
  def show
    raise
  end
```
  
```
// browser terminal

params
{"controller"=>"animals", "action"=>"show", "id"=>"1"}
```

We can see the params hash gives us the controller action, and the id.How we do we get the model with this id?

```ruby
# app/controllers/animals_controller.rb

  def show
    animal = Animal.find(params[:id])
    render :json => animal
  end
```

## Creating an animal

What route do we need to create an animal?
POST http://localhost:3000/animals

Rails has functionality out the box for restful routes. We can see the routes we have set up using the command

```bash
# terminal

rake routes
```

We can see the two restful routes we have setup.

To add the post route we could add.

```ruby
# config/routes.rb

  post 'animals' => 'animals#create'
```

However we can setup routes for all the restful routes, using the resources route.

```ruby
# config/routes.rb

resources :animals
```

```rake routes``` will show all the routes that have been set up.

Check our current routes still work.

Let's wrap our api with a prefix.

```ruby
# config/routes.rb

  scope path: "api" do
    resources :animals
  end
```

How cool is that?

## Testing our post request.
  Testing our post request. Insomnia
  POST localhost:3000/api/animals {"animal": {"name": "Sheep"}}

  Strong parameters, we need to white list what we can pass when we create an animal.
  
```ruby
# app/controllers/animals_controller.rb

  def create
    animal = Animal.create( params.require(:animal).permit([:name, :legs]) )
    render :json => animal
  end
```

We have created an API that allows us to add animals and get them as JSON.

### Common properties

Before we go any further, let's pull out the common params we will be using across actions.

```ruby
# app/controllers/animals_controller.rb

def animal_params
  params.require(:animal).permit([:name, :legs])
end

def create
  animal = Animal.create(animal_params) # UPDATED
  render :json => animal
end
```

## Updating an animal

What would the action be called that we need to use? Update! We can see this in rake routes.

=> put api/animals/1

```ruby
# app/controllers/animals_controller.rb

def update
  animal = Animal.find(params[:id])
  
  if animal.update_attributes(animal_params)
    render :json => animal
  else
    render :json => { status: :update_failed }
  end
end
```

## Delete

Lastly we want to delete an animal.

```ruby
# app/controllers/animals_controller.rb

def destroy
  animal = Animal.find(params[:id])
  
  if animal.destroy!
    render :json => { status: :success  }
  else
    render :json => { status: :delete_failed }
  end
end

```

### One final thing

We might want to render the output from a method, like our "move" on the animal. We need to just make a slight modification to make the magic happen.

```ruby
# app/controllers/animals_controller.rb

def index
  @animals = Animal.all
  render :json => @animals.as_json(methods: :move) # UPDATED
end

def show
  @animal = Animal.find(params[:id])
  render :json => @animal.as_json(methods: :move) # UPDATED
end
```
