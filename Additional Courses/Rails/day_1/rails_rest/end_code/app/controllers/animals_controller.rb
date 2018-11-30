class AnimalsController < ApplicationController
  def index
    @animals = Animal.all
    render :json => @animals.as_json(methods: :move)
  end

  #get api/animals
  def show
    @animal = Animal.find(params[:id])
    render :json => @animal.as_json(methods: :move)
  end

  #post api/animals/1
  def create
    animal = Animal.create( animal_params )
    render :json => animal
  end

  #put api/animals/1
  def update
    animal = Animal.find(params[:id])
    if animal.update_attributes(animal_params)
      render json: animal
    else
      render json: {status: :update_failed}
    end
  end

  #delete api/animals/1
  def destroy
    animal = Animal.find(params[:id])
    if animal.destroy!
      render json: { status: :success  }
    else
      render json: { status: :delete_failed }
    end
  end

  private
  def animal_params
    params.require(:animal).permit([:name, :legs])
  end
end