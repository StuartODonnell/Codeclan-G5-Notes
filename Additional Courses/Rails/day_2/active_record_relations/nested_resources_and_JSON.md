# Nested Resources and JSON

### Pre-requisites
- Active Record Relations lesson

### Objectives
- learn to use nested resources in rails
- learn to render data from active relations as JSON

## Overview

We are going to update our ticketmaster app so that it will be able to:

- Send Artists and associated Albums as JSON
- Send Artist Gigs and Venues as JSON

So a JSON API.

## JSON API

So far with our Ticketmaster app we've been viewing our data in the rails console. 

Let's send our data down in JSON. We have an Artist so we need a controller to deal with sending the data. Let's create a controller.

```
touch app/controllers/artists_controller.rb
```

```ruby
# app/controllsers/artists_controller.rb

class ArtistsController < ApplicationController

  def index
    artists = Artist.all
    render :json => artists 
  end

end
```

Here we have setup a controller and passed in an index route by creating a method 'index'. Inside of this we read all the artists and send them down in JSON. Fire up the server and go to '/artists'. Why do we get this error?

We need to add it to our routes.rb:

```ruby
# config/routes.rb

  get 'artists' => 'artists#index'

```

The routes.rb deals with our incoming requests and delegates them to a controller and action split by the #.

Great, now we can GET all the artists.

We also want to be able to GET just one Artist.

[TASK: 10-15 mins] Add the artists SHOW route and the controller action (which should render JSON)

```ruby
# app/controllers/artists_controller.rb

def show
  artist = Artist.find( params[:id] )
  render :json => artist 
end
```

And the routes.rb:

```ruby
# config/routes.rb

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
# terminal:
touch app/controllers/gigs_controller.rb
```

```ruby
# app/controllers/gigs_controller.rb
class GigsController < ApplicationController

end
```

Now we want to setup our routes. We have currently been setting up single routes but we can actually setup an entire set of RESTful routes with one rails method:

```ruby
# config/routes.rb
resources :artists 

```

```
# terminal

rake routes
```

Now we can see what has been setup for us! OK, we want to setup a nested route, how can we do that? In routes.rb, in rails we can:

```ruby
# config/routes.rb
resources :artists  do
  resources :gigs 
end
```

```
# terminal
rake routes
```

Now we can see we have this nested structure in place. Result. Let's make our index route in gigs_controller.rb:

```ruby
# app/controllers/gigs_controller.rb

def index
  gigs = Gig.where( { artist: params[:artist_id] } )
  render :json => gigs
end
```

We are grabbing all the gigs where the artist_id is equal to the artist_id in params. Note this is now artist_id ( Rails has done this for us because of our RESTful resources ). Then we send them down as json as before.

### Creating a new Gig

If we wanted to create a new Gig we can do with Insomnia.

Before we do this we need add a line to our application_controller or else we will get an authenticity token error:

```ruby
# app/controllers/application_controller.rb

protect_from_forgery with: :null_session
```

```ruby
# app/controller/gigs_controller.rb

def create
  gig = Gig.create( { price: params[:price], date: params[:date], venue_id: params[:venue_id], artist_id: params[:artist_id] } )
  render( json: gig )
end
```

In Insomnia we can now POST to localhost:3000/artists/5/gigs to create a new gig for Artist number 5.

```json
//JSON to POST in Insomnia

{ 
  "price": 15, 
  "date": "2016-09-09", 
  "venue_id": 1, 
  "artist_id": 5 
}
```

We can also actually take out the artist id from our request as it's already in the params from the URL.


```json
//JSON to POST in Insomnia

{ 
  "price": 20, 
  "date": "2016-09-09", 
  "venue_id": 1
}
```


## Serialisation

Rails gives us some extra options when sending information as JSON. As well as sending information about one model, we can also include extra information about its associated models. Basically we are formatting the available data in the most useful way for us. This is called serialisation in Rails. 

The way we do this is to use a method called ```as_json``` on the information we are sending down. We can then tell it to include the information about one of its associations. For example with our list of artist's gigs, we can say to include the venue for each gig.

```ruby
# app/controllers/gigs_controller.rb

def index
  gigs = Gig.where( { artist: params[:artist_id] } )
  render :json => gigs.as_json( { include: :venue } ) #MODIFIED
end
```

[TASK: 10-15mins] In the same way, Include our albums on our artists show controller action:

```ruby
# app/controller/artists_controller.rb

def show
  artist = Artist.find( params[:id] )
  render :json => artist.as_json( { include: :albums } ) #MODIFIED
end
```

We can also tell Rails which fields to include or exclude. For example for our gigs, let's only show the venue name.

```ruby
# app/controllers/gigs_controller.rb

def index
  gigs = Gig.where( { artist: params[:artist_id] } )
  render json: gigs.as_json( 
    { include: 
      { venue: { only: :name } } #MODIFIED
    } 
  )
end
```

This can be a bit complicated to format (lots of brackets!) but can be really useful for formatting our APIs exactly how we want them to appear. 

More on serialisation:
http://api.rubyonrails.org/classes/ActiveModel/Serializers/JSON.html
