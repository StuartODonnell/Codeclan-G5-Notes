## Cheat Sheet

A User
  - has_many :favourite_shows 
  - has_many :favourites, through: :favourite_shows, source: :show

A Show
  - has_many :favourite_shows
  - has_many :favourited_by, through: :favourite_shows, source: :user


```
rails g model FavouriteShow show:references user:references
rails g model User name:string
```

```
class FavouriteShow < ActiveRecord::Base
    belongs_to :show
    belongs_to :user
end
```

```
class User < ActiveRecord::Base
  has_many :favourite_shows
  has_many :favourites, through: :favourite_shows, source: :show
end
```

```
class Show < ActiveRecord::Base
  has_many :favourite_shows
  has_many :favourited_by, through: :favourite_shows, source: :user
end
```

```
rake db:migrate
```

```
//rails c
u = User.create({name: 'Barney Rubble'})
u.favourites << Show.find(1)
u.favourite_shows
```

This is another amazing thing Rails can do really simply.
It's basically just aliasing the relationship as 'favourites'.

Users have many shows (through favourite_shows) - but users also have many 'favourites' and the `source :users` bit at the end of the line is saying "These 'favourites' that the Users have, they're actually shows, and you should get them from the shows table."

So from the User object we can call u.favourites to get their favourite shows.

User and Show both need to 'have many favourite_shows', (and FavouriteShow needs to 'belong_to' both user and show) - that bit's just setting up the basic many-to-many. So after the first line you can say `u.favourite_shows` and you get a fairly uninformative list of foreign keys on the join table (favourite_shows table)
