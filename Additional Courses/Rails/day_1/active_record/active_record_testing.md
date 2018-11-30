# Active Record Further

## Learning Objectives
 - Show testing models in Rails
 - Show adding attributes to models
 - Show adding functionality to models


We want to extend the functionality of our model. Say we wanted to add a number of legs attribute to our animal. How would we do this.  Just now we should be thinking.  Hey what about testing.  We're creating this business logic but no tests.  Good let's get on this.


## Testing models in Rails
Rails set up tests for us when we created our animal model
test/models/animal_test.rb

Rails uses MiniTest out the box.  http://guides.rubyonrails.org/testing.html.  We can use other testing frameworks such as RSpec.  What testing framework you use is a matter of choice. The important thing is that you test.


We can run our tests using the following command
```
# terminal

rake test
```
Let's add some tests.

Rails comes out the box with a concept called fixtures.  These help set up models in a test database that we can use for testing.  Rails has already set up some fixtures for our animals.

```
//test/fixtures/animals.yml

one:
  name: Tiger

two:
  name: Snake
```

```
//test/models/animal_test.rb

test "Animals have a name" do
  assert_equal("Tiger", animals(:one).name)
end
```

```
//test/fixtures/animals.yml

one:
  name: Tiger

two:
  name: Snake
```

# Adding number of legs to our animals.
Say we want to add a number of legs to our animal.  We can set up our fixtures so that they try to create animals with legs.

```
//test/fixtures/animals.yml

one:
  name: Tiger
  legs: 4

two:
  name: Snake
  legs: 0
```

```
# terminal

rake test
```

We have tried to create some animals that our models are not setup for.  Let's add the ability for animals to have legs.


What do we need to have to do this?
  Add column to animals table.
  Add functionality to the model legs method

## Add column to animals table
terminal (not rails console)

```
# terminal

  rails generate migration AddLegsToAnimal legs:integer
```
We can see that this has added the migration file.  We now need to actually run the migrations to update the database.

```
# terminal

  rake db:migrate
```

Now we have the table in the database let's try running our tests.

Wow our tests are working.  Our model automatically has the getter and setters for the columns in the database.  This is the magic active record.


## Adding functionality.
Now say we want to add some functionality to our model.  Say the ability to move. If it has legs it returns walk, otherwise slither.  Let's add the tests for this.

```
  test "Animals with legs walk" do
    assert animals(:one).move == "walk"
  end

  test "Animals with no legs can slither" do
    assert animals(:two).move == "slither"
  end
```

Write the functionality to get this working.

```ruby
# models/animal.rb

  def move
    movement = "walk"
    movement = "slither" if !legs || legs == 0
    return movement  
  end
```
