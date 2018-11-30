class AnimalsController < ApplicationController
  def index
    @animals = ["Tiger", "Snow Leopard"]
    render :json => @animals
  end
end