class ShowsController < ApplicationController
  before_action :authenticate_user!
  
  def index
    shows = Show.all  
    render :json => shows.to_json()
  end

  def create
    show = Show.create( show_params )
    render json: show, status: :created
  end


  private
  def show_params
    params.require(:show).permit([:title, :series, :description, :image, :programmeID])
  end

end
