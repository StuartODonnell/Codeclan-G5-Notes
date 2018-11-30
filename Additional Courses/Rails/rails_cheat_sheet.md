# Rails Cheat Sheet

## Important files/folders:

__/config/routes.rb__ >> has all the routes in.

__/app/controllers/__ >> controllers go here. Names must be plural i.e. animals_controller.rb

__/db/seeds.rb__ >> seed file for database.

__/db/schema.rb__ >> schema which updates along with your database - see what columns and tables should currently exist.

__/db/migrate/__ >> database migration files - used for creating or deleting tables, among other things.

__/test/fixtures/__ >> YAML files which set up sample data for tests. 

__/test/models/__ >> test files for your models. 

__Gemfile__ >> lists all gems to be used in the app. After updating this you need to run 'bundle install' to install any new gems. 

__/app/views/__ >> holds all views. Views must be in a folder named after the resource you are showing eg /animals/index.html.erb.

## Commands:

```rails new app_name``` = makes new Rails app. Optionally add '-d postgresql' to use a Postgres database.

```rails server / rails s``` = runs Rails server.

```rails console / rails c``` = opens Rails console for inspecting/editing database items.

```rails generate model Name attribute:type``` = makes a new model, name should be singular and then list attributes/column names with their data types eg string. 

```rake db:create``` = makes database for your app.

```rake db:migrate``` = run migrations for the database - any that have already been run will not be re-run.

```rake db:drop``` = drop the database and all its contents.

```rake db:seed``` = run the database seeds file. 

```rake routes``` = shows all available routes.

```rake test``` = runs tests from the tests folder.

## Active Record methods

These can be used in the Rails console or in other Rails ruby files e.g. controllers. When referring to a model you must use the singular of that model and uppercase the first letter. 

```Animal.all``` = returns all animals.

```Animal.first``` = returns first animal.

```Animal.last``` = returns last animal.

```Animal.find(id)``` = returns animal with that id.
 
```Animal.create({ name: "Barnaby" })``` = creates and saves to database a new record.

```Animal.new``` = creates a new animal but does not save it.

```my_animal.save``` = saves to the database.

```my_animal.update({ name: "Boba" })``` = updates a record.

```my_animal.destroy!``` = deletes the animal from the database.



## Setup of new routes:

1. Add a route in config/routes e.g. "get 'animals' => 'animals#index' "
2. Make a controller for the resource (new controllers must inherit from ApplicationController, must use plural name e.g. animals_controller.rb)
3. Make appropriate method to match routes in the controller e.g. 'def index'
4. Either make view file in app/views or return JSON e.g. 'render :json => animals'

