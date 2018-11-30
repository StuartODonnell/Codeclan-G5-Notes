# Active Record
1hr

# Pre-requisites
 action_pack lesson.

## Learning Objectives
  - Explain the term Model.
  - Create a model in Rails.
  - Describe how a model relates to a database table
  - Describe the advantages/disadvantages of using an Object Relational Mapping(ORM)
  - Describe how migrations are used to keep a record of the database structure.
  - Describe the difference between new, create, save methods.

## Intro
http://guides.rubyonrails.org/active_record_basics.html

Active Record is Model M in MVC.  Models are the layer responsible for the business data and logic. Active Record is an Object Relational Mapping (ORM). Objects are mapped to tables in a relational database, making them easy to persist. (Gives them a home)

## Active Record in Rails
Continue on from animals application. (action_pack.md)

In our animals application we had setup the request response cycle to return a hard coded list of animals in the animals controller.  What we want to do is to have the animal objects handled in the Model layer using Active Record. How would we do this without Active Record using a relational database?

- Create a table in database
- Access database from controller.  Or create an animals class that talks to the database.

Active Record will take care of all this object relation mapping for us.  Often Rails applications do not have a line of hand written SQL.

Let's see this in action.

## Creating the Animal model
Here we will use the Rails helpers to create our active record models and required database counterparts.  We tell it what field we want it to have, columns in database and what the type of the column should be.

```
# terminal

  rails generate model Animal name:text
```

The generator has generated some files for us.

- Migration File.
- The model file.
- And some test files

Let's look at the model file.

### Model file
The model file gives us the class for animal objects.  It inherits from the ActiveRecord::Base class that gives the model the ability to persist in the database.

### Migration file
The model needs somewhere to live.  For this we need to create a database table.  The rails way of creating updating and creating a record of database tables is through migrations.  Let's have a look at the migration ruby file.

```ruby
# db/migrate/<DateTimeStamp>_create_animals.rb

class CreateAnimals < ActiveRecord::Migration
  def change
    create_table :animals do |t|
      t.text :name

      t.timestamps null: false
    end
  end
end
```

Migrations are a way for us to manage the creation and alteration of our database tables in a structured and organized manner.

Each migration is a seperate file, which Rails runs for us when we instruct it. Rails keeps track of what's been run, so changes don't get attempted more than once.

[http://guides.rubyonrails.org/v4.2.4/migrations.html]

Migrations are really cool. It means we don't need to keep syncing databases between developers and getting into a hideous mess. Everyone has a record of the migrations that have taken place, and running them in sequence gives the current scheme of the database.

Coupling this with seed data means we have a database ready to go the minute we pull down a project!


### Running Migrations
Running the migrations will set up the database to reflect what our application needs.  We can share our list of migrations so another instance of our application can be easily created.

We do migrations using the following command.

```
# terminal

rake db:migrate
```

Table has been created.  Woop!  Our animals have somewhere to live let's go and create some of them.

## Rails Console
The rails terminal is an incredibly useful tool to allow us to enter our rails environment.  It is particularly useful for interacting with the model layer.  It can be thought of like irb but in the your rails environemnt for your project

```
# terminal
  
  rails console
```

## Active Record in action

In here we can see we have the animal class.

```
# terminal
 
  Animal
```

But this isn't just any normal class.  Because it inherits from the ActiveRecord:Base it knows it should have a matching database table (convention over configuration).  And because of this we can use class methods to query the database through the class.

```
# terminal
  
  Animal.count
```

We can see the SQL query that has been abstracted away from us.

### Creating models
We can use this class to create object just like a plain old Ruby object.

```
# terminal
 
  my_animal = Animal.new
  my_animal.name = "Tiger"
```

Active record objects have a special method that comes out of the box. save.  This method will take the details of the object and place it as a row in the database! 

```
# terminal
 
  my_animal.save
```

If we now count the models, we should see it has been added.

```
# terminal
 
  Animal.count
```

### Retrieving models
We can now get our model back

```
# terminal
 
  Animal.all
  Animal.first
  Animal.find(1)
```

This animal will still be about even if we close our terminal as it now lives in the database.

### Using our models in our request/response Cycle.
Now time for the big reveal. How can we use our model animals in our HTTP request response cycle.

```ruby
  #app/controllers/animals_controller.rb
  
  class AnimalsController < ApplicationController
    def index  
      @animals = Animal.all
      render :json => @animals
    end
  end
```

Well done we have created a full MVC Rails application using action pack for the request response cycle, and plugging in our model using active record.

Worth mentioning that you can send down either JSON or HTML, depending on the request. In order to handle both, we have to add a few extra lines:

```ruby
  #app/controllers/animals_controller.rb
  
  class AnimalsController < ApplicationController
    def index  
      @animals = Animal.all
      respond_to do |format|
        format.html
        format.json{render :json => @animals}
    end
  end
```

Please remember that in order to make it work, we have to have a layout for our index in the views/animals directory!

### Adding other fields

Let's say we want to dd the animals diet. How can we add this?

We need to run a migration!

```
# terminal

rails generate migration AddDietToAnimal diet:string
rake db:migrate
```

### Removing Fields

Let's say we add a column for an animal's lightsaber

```
# terminal

rails generate migration AddLightsaberToAnimal lightsaber:string
rake db:migrate
```

Hmm... animals don't have lightsabers so... we're in a bit of a pickle here. How can we remove it?

```
# terminal

rails generate migration RemoveLightsaberFromAnimal
```
This will generate an empty migration which we can modify.

```ruby
# app/migrate/<migrationfilename>.rb

class RemoveLightsaberFromAnimal < ActiveRecord::Migration
  def change
    remove_column :animals, :lightsaber
  end
end
```
Now when we run rake db:migrate, the rogue column will be gone.

### Making Seeds

Let's make some seed data, just like we did in Sinatra. This should feel very familiar too you.

```ruby
# db/seeds.rb

Animal.delete_all
Animal.create({name: "Slug", diet: "Lettuce"});

# terminal
rake db:seed
```
How do we check this has worked? Yep, the rails console.

```
# terminal

rails c
Animal.all
```

TASK: Make a few more animals.
s