Gig.delete_all()
Artist.delete_all()
Venue.delete_all()

a1 = Artist.create( {name: 'Oasis'} )
a2 = Artist.create( {name: 'Justin Bieber'} )

Album.delete_all()

Album.create( { artist_id: a1.id, name: 'Roll With It' } )
Album.create( { artist_id: a2.id, name: 'An album title' } )

v1 = Venue.create( { name: 'o2', location: 'London' } )
v2 = Venue.create( { name: 'Corn Exchange', location: 'Edinburgh' } )

Gig.create( { price: 15, date: DateTime.now() + 30, artist_id: a1.id, venue_id: v1.id  } )
Gig.create( { price: 15, date: DateTime.now() + 30, artist_id: a2.id, venue_id: v2.id  } )
