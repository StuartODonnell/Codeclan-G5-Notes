# TicketMaster - Rails relations

### Objectives

- Build on SQL relationships learned in module one
- How we can use Rails & ActiveRecord to setup relations
- Build a working one-to-many and many-to-many api using rails

## Overview

We are going to build a ticketmaster app. It will be specifically for Music artists. Our app will be able to:

- Send Artists and associated Albums as JSON
- Send Artist Gigs and Venues as JSON

So a JSON API.

## Relationships

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
  rails generate model Artist name:text
  rake db:migrate
  rails console
```

### Testing Artist
```
  //test/fixtures/artists.yml
  one:
    name: Justin Bieber

  two:
    name: Oasis
```
```
  //test/models/artist_test.rb
  require 'test_helper'

  class ArtistTest < ActiveSupport::TestCase
    test "Artist has a name" do
      assert_equal("Justin Bieber", artists(:one).name)
    end
  end
```
If we look in app/models there will be a artist.rb with and Artist class. Notice that this inherits from ActiveRecord, an ORM. This means our model has all the methods this provides.

The Rails console allows us to interact with our models and database. Once in here we have access to ActiveRecord. So, for example:

```
  rails console

  Artist.create( { name: 'Oasis' } )
  Artist.all()
```
We can see now that ActiveRecord gives us these methods on our Artist class. ActiveRecord also provides some useful instance methods as well. If we want to make a new artist and then persist it, we could say:

```
artist = Artist.new( { name: 'Justin Bieber' } )
artist.save()
```
We can create some data in seeds.rb:

```
Artist.delete_all()

a1 = Artist.create( {name: 'Oasis'} )
a2 = Artist.create( {name: 'Justin Bieber'} )

```

## Setting up our one to many

We now want to have associated albums. So we are going to need and Album Class:

```
  rails generate model Album name:text artist:references
```
This sets up a new model in app/models. If we have a closer look at this model though we will notice something. There is a function being called - ```belongs_to( :artist )```

Using the artist:references in our rails command sets this up for us. If we have a look in our schema.rb we will see our new table and a column for artist_id.

This belongs_to sets up the one to one connection with another model. We still need to migrate for these changes to take affect:

```
rake db:migrate
```

### Testing Album

```
//fixtures/albums
one:
  name: Definatly Maybe
  artist_id: 2

two:
  name: Biebs Hits
  artist_id: 1
```

```
//fixtures/artist
one:
  id: 1
  name: Justin Bieber

two:
  id: 2
  name: Oasis
```

```
/test/models/album_test
test "Album has a name" do
  assert albums(:one).name == "Definatly Maybe"
end
test "Album has an artist" do
  assert albums(:one).artist == artists(:two)
end
```

# In seeds.rb:

Album.new( { artist_id: a1.id, name: 'Definatly Maybe' } )
Album.new( { artist_id: a2.id, name: 'Beibs Greatest Hits' } )

# In terminal:
```
rake db:seed
rails console
```

In rails console we now have access to our models and their functionality, which is currently being provided by the inherited ActiveRecord module:

```
  # View all artists and first or last
  Artist.all()
  Artist.last()
  # View all albums - note the artist_id
  Album.all()
  # Save the last artist
  album = Album.last
  # Retrieve the associated artist
  album.artist
```
We now have the artist through the album. At the minute though we can't get all the albums associated to an artist. Try it and we get an error. How can we fix this?

## Setup Test Artist has many Albums
```
test "Artist albums" do
  assert artists(:one).albums.count == 1
end
```
```
rake test
```

We can invoke a function to setup a has_many link to albums in our Artist class:

```
  has_many( :albums )
```

```
rake test
```

```

  # in rails console:

  artist = Artist.first
  artist.albums
```

## Many to Many - Has many through

Now we can associate Artists to Albums we want to be able to associate Artists to Venues.

DRAW:

An Artist has many Venues and Venues have many Artists. We are going to use a Gig join table which will hold the venue_id and an artist_id. Each row in Gig will also include a ticket price and a date. We call this association in Rails a has many through.

in terminal:

```
  rails generate model Venue name:text location:text
  rake db:migrate
  rails generate model Gig price:decimal date:datetime artist:references venue:references
  rake db:migrate
```
##### Testing Venues
```
//venue_test.rb
require 'test_helper'
class VenueTest < ActiveSupport::TestCase
  test "venues have a name" do
    assert venues(:one).name == "King Tuts"
  end

  test "venues have a location" do
    assert venues(:one).location == "Glasgow"
  end
end
```
##### Testing Gigs
```
//gig_test.rb
require 'test_helper'
class GigTest < ActiveSupport::TestCase
  test "gigs have a price" do
    assert gigs(:one).price == 10.00
  end

  test "gigs have a date" do
    assert gigs(:one).date == "2016-04-13 13:00:00"
  end
end
```
#### Testing Relations
venues
```
test "venues have gigs" do
  assert_equal(1, venues(:one).gigs.count)
end
```
artist
```
test "artists have gigs" do
  assert_equal(1, artists(:one).gigs.count)
end
```
gigs
```
test "gigs have an artist" do
  assert_equal(artists(:one), gigs(:one).artist)
end

test "gigs have an venue" do
  assert_equal(venues(:one), gigs(:one).venue)
end
```



OK, we now have our table and model setup but we need to use the activerecord methods so we can read the associated data.

#### Venue

A venue has many gigs but these are associated through the gigs table. So we can say in venue.rb:

```
  has_many( :gigs )
  has_many( :artists, {through: :gigs} )
```

#### Artist

The artist model has the same relationship just inversely:

```
  has_many( :gigs )
  has_many( :venues, {through: :gigs} )
```

#### Gig

Finally, a row in the gig_table belongs to a row in the other models (venue and artist):

```
  belongs_to( :artist )
  belongs_to( :venue )
```
rake test

## Has many through, tests



venues
```
test "venues have artists" do
  assert_equal(1, venues(:one).artists.count)
end
```
artist
```
test "artists have venues" do
  assert_equal(1, artists(:one).venues.count)
end
```

#### Venue

A venue has many gigs but these are associated through the gigs table. So we can say in venue.rb:

```
  has_many( :artists, {through: :gigs} )
```

#### Artist

The artist model has the same relationship just inversely:

```
  has_many( :venues, {through: :gigs} )
```



This should already be setup because of our references in our command line command when generating the model.

We can now add to our seeds.rb:

```
# top:
Gig.delete_all()
Venue.delete_all()

# bottom:
v1 = Venue.create( { name: 'o2', location: 'London' } )
v2 = Venue.create( { name: 'Corn Exchange', location: 'Edinburgh' } )

Gig.create( { price: 15, date: DateTime.now() + 30, artist_id: a1.id, venue_id: v1.id  } )
Gig.create( { price: 15, date: DateTime.now() + 30, artist_id: a2.id, venue_id: v2.id  } )
```

## JSON API
Let's send our data down in JSON.
We need to add it to our routes.rb:

```
  //options
  get 'artists' => 'artists#index'
  resources( :artists )
```

 We have an Artist so we need a controller to deal with sending the data. Let's create a controller.

```
touch app/controllers/artists_controller.rb
```

inside artists_controller:
```
class ArtistsController

  def index
    artists = Artist.all
    render json: artists
  end

end
```

Here we have setup a controller and passed in an index route by creating a methos 'index'. Inside of this we read all the artists and send them down in JSON. Fire up the serve and go to '/artists'. Why do we get this error?


The routes.rb deals with our incoming requests and delegates them to a controller and action split by the #.

Great, now we can GET all the artists we want to GET one. So we need a show route - remember REST?

```
def show
  artist = Artist.find( params[:id] )
  render( json: artist )
end
```
And the routes.rb:
```
  get 'artists/:id' => 'artists#show'
```
Now we can get individual artists. We now want to expand our data and get the artists gigs? How can we do this?

## Nested Resources

Nested resources allow us to get data in reference to logical child or parent data. For example, we can get a single artist by /artists/6. Let's say we want to get the gigs that artist has. Logically, this would be a good route:
```
  artists/6/gigs
```
This would get us the all the gigs artist id 6 has. Let's make a gigs controller.

```
class GigsController < ApplicationController

end
```

Now we want to setup our routes. We have currently been setting up single routes but we can actually setup an entire set of RESTful routes with one rails method:

```
resources( :artists )

# in terminal:
rake routes
```

Now we can see what has been setup for us! OK, we want to setup a nested route, how can we do that? In routes.rb, in rails we can:

```
resources( :artists ) do
  resources( :gigs )
end

# in terminal:
rake routes
```

Now we can see we have this nested structure in place. Result. Let's make our index route in gigs_controller.rb:

```
def index
  gigs = Gig.where( { artist: params[:artist_id] } )
  render( json: gigs.as_json( { include: :venue } ) )
end
```

Two things are happening here:

1. We are grabbing all the gigs where the artist_id is equal to the artist_id in params. Note this is now artist_id ( Rails has done this for us because of our RESTful resources )
2. We then render this down in JSON which is nothing new. However, here we are using the as_json method on all our gigs which takes a hash argument. here we can stipulate an include property and set, as a symbol, the model we want to attach. Rails magic.

TASK: Include our albums on our artists show controller action [ 10 mins ]:

```
def show
  artist = Artist.find( params[:id] )
  render( json: artist.as_json( { include: :albums } ) )
end
```

### Creating a new Gig

If we wanted to create a new Gig we can do with Insomnia. We need to add in:

```
protect_from_forgery()

def create
  gig = Gig.create( { price: params[:price], date: params[:date], venue_id: params[:venue_id], artist_id: params[:artist_id] } );
  render( json: gig );
end
```
