class ArtistsController < ApplicationController

  def index
    artists = Artist.all
    artists.to_json()
  end

  def show
    artist = Artist.find( params[:id] )
    render( json: artist.as_json( { include: :albums } ) )
  end

end