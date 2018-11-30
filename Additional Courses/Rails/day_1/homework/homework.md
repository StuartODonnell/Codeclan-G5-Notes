# Rails API Homework

Create a Rails app called shows_api. Your API should return and repond to JSON (i.e. no HTML views required)

The INDEX route should be localhost:3000/api/shows

It's going to have a resource called Shows to manage TV shows and films.

You only have to implement an INDEX and CREATE in the ShowsController (i.e not all the RESTful routes)

The Show resource should have:

  - title:string
  - series:integer
  - description:text
  - image:string
  - programmeID:string


## Example Show

```
{
  "title": "The Great British Bake Off",
  "series": 7,
  "description": "Master bakers Mary & Paul and the incomparable presenting duo of Mel & Sue are back for another interesting series about baking cakes.",
  "image": "placeholder.jpg",
  "programmeID": "b013pqnm" 
}
```

Create some shows in your seeds file.
