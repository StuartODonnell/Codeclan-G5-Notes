# Rails Shows API Homework Review
> Ask students to spend a few minutes answering these questions on their own, then to spend five minutes with the person sitting next to them to discuss their answers.

## Questions

1. The following command generates a model for a Show object with a 'title' property:

`rails generate model shows title:string`

It also generates a migration file. What will the migration file do when run?

2. The following command creates a 'description' property on the Show model:

`rails generate migration AddDescriptionToShow description:text`

It also generates a migration file. What will this migration file do when run?

3. The routes for the Shows resource get hit with requests. What does the routes dispatch the requests to?

4. What is the role of the ShowsController?


## Answers

1. It will create a 'shows' table in the database with a 'title' column and add it to the database schema
2. It will add a 'description' column to the shows table
3. The ShowsController
4. To make sense of the requests and to produce the appropriate output. In this case to render the appropriate JSON for each of the restful routes.

