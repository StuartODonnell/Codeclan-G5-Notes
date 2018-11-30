class AnimalsController < ApplicationController
  def index
    @animals = Animal.all
    respond_to do |format|
      format.html
      format.json{ render :json => @animals }
    end
  end
end