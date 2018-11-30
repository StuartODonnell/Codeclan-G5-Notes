class GigsController < ApplicationController

  protect_from_forgery()

  def index
    gigs = Gig.where( { artist: params[:artist_id] } )
    render( json: gigs.as_json( { :include => :venue } ) )
  end

  def show
    gig = Gig.find( params[:id] )
    render( json: gig.as_json(include: :venue) )
  end

  def create
    gig = Gig.create( { price: params[:price], date: params[:date], venue_id: params[:venue_id], artist_id: params[:artist_id] } );
    render( json: gig );
  end

end