# TicketMaster - Rails relations

### Objectives

- Build on SQL relationships learned in module one
- How we can use Rails & ActiveRecord to setup relations
- Build a working one-to-many and many-to-many api using rails

## Overview

We are going to build a ticketmaster app. It will be specifically for Music artists. In our app we will be able to:

- see Artists and associated Albums
- see Artist Gigs

In the next lesson we will extend this to create a JSON API.

## Relationships

has\_many - Indicates a one-to-many connection with another model. This association indicates that each instance of the model has zero or more instances of another model.

belongs\_to - A belongs\_to association sets up a one-to-one connection with another model, such that each instance of the declaring model "belongs to" one instance of the other model.

has\_and\_belongs\_to\_many - A has\_and\_belongs\_to\_many association creates a direct many-to-many connection with another model, with no intervening model. We don't often make these as they restrict us - if we ever want to add information to the association we can't. 

has\_many :through - A has\_many :through association is often used to set up a many-to-many connection with another model. This association indicates that the declaring model can be matched with zero or more instances of another model by proceeding through a third model.


## Artists and Albums

First of all we have an Artist who will have, potentially, many albums. What's the relationship here? One to Many.

An Artist will also play at many Venues. But a Venue will also host many Artists. What's the relationship here? This is a Many-to-Many relationship. So we need a join table, Gig would be a good name for this table.

## Setup

Let's setup our app, in Terminal:

```
  rails new ticketmaster
  cd ticketmaster
  rake db:create
```

## Artist

The first model we want to setup is our Artist, for now it will just have a Name column. We setup a model using the Rails command line tools:

```
# terminal

  rails generate model Artist name:string
  rake db:migrate
  rails console
```

If we look in app/models there will be a artist.rb with and Artist class. 

We can create some data in seeds.rb - remember 'create' does both 'new' and 'save' at once. We are saving these artists to variables in our seed file to be used later:

```ruby
# db/seeds.rb

Artist.delete_all()

a1 = Artist.create( {name: 'Oasis'} )
a2 = Artist.create( {name: 'Iron Maiden'} )
```

```
# terminal 

rake db:seed
```

## Setting up our one to many

We now want to have associated albums. So we are going to need an Album Class:

```
# terminal 

  rails generate model Album title:string artist:references
  rake db:migrate
```

This sets up a new model in app/models. If we have a closer look at this model though we will notice something. There is a function being called.

```ruby
# app/models/album.rb

belongs_to( :artist )
```

Using the ```artist:references``` in our rails command sets this up for us. If we have a look in our schema.rb we will see our new table and a column for artist_id.

This ```belongs_to``` sets up the one to one connection with another model. We still need to migrate for these changes to take affect:

```ruby
# db/seeds.rb

Album.delete_all()

Album.create( { artist_id: a1.id, title: 'Roll With It' } )
Album.create( { artist_id: a2.id, title: 'Number of the Beast' } )
```

We can also just pass the Artist to Album.create rather than the id e.g.

```ruby
# db/seeds.rb

Album.create( { artist: a2, title: 'Piece of Mind' } )
```

```
# terminal

rake db:seed
rails console
```

In rails console we now have access to our models and their functionality, which is currently being provided by the inherited ActiveRecord module:

```ruby
# rails console

  # View all albums - note the artist_id
  Album.all()
  # Save the last artist
  album = Album.last
  # Retrieve the associated artist
  album.artist
```

We now have the artist through the album. At the minute though we can't get all the albums associated to an artist. Try it and we get an error. How can we fix this?

We can invoke a function to setup a has_many link to albums in our Artist class:

```ruby
# app/models/artist.rb

  has_many( :albums ) #ADDED
```

```
# rails console:

  artist = Artist.first
  artist.albums
```

## Many to Many - Has many through

Now we can associate Artists to Albums we want to be able to associate Artists to Venues.

> DRAW diagram showing the following

An Artist has many Venues and Venues have many Artists. We are going to use a Gig join table which will hold the venue_id and an artist_id. Each row in Gig will also include a ticket price and a date. We call this association in Rails a "has many through".

```
# terminal

  rails generate model Venue name:string location:string
  rake db:migrate
  rails generate model Gig price:integer date:datetime artist:references venue:references
  rake db:migrate
```

OK, we now have our table and model setup but we need to use the ActiveRecord methods so we can read the associated data.

#### Venue

A venue has many gigs but these are associated through the gigs table. So we can say in venue.rb:

```ruby
# app/models/venue.rb

  has_many( :gigs )
  has_many( :artists, {through: :gigs} )
```

#### Artist

The artist model has the same relationship just inversely:

```ruby
# app/models/artist.rb

  has_many( :gigs )
  has_many( :venues, {through: :gigs} )
```

#### Gig

Finally, a row in the gigs table belongs to a row in the other models (venue and artist):

```ruby
# app/models/gig.rb

  belongs_to( :artist )
  belongs_to( :venue )
```

This should already be setup because of our references in our command line command when generating the model.

We can now add to our seeds.rb:

```ruby
# top:
Gig.delete_all()
Venue.delete_all()

# bottom:
v1 = Venue.create( { name: 'Hammersmith Odeon', location: 'London' } )
v2 = Venue.create( { name: 'Playhouse', location: 'Edinburgh' } )

Gig.create( { price: 15, date: "2017-01-14", artist_id: a1.id, venue_id: v1.id  } )
Gig.create( { price: 15, date: "2017-03-23", artist_id: a2.id, venue_id: v2.id  } )
```

```
# terminal

rake db:seed
```

Now in the rails console we can see artist.gigs, venue.gigs, venue.artists etc.
