class RemoveFaveFoodFromAnimal < ActiveRecord::Migration
  def change
    remove_column :animals, :favourite_food
  end
end
