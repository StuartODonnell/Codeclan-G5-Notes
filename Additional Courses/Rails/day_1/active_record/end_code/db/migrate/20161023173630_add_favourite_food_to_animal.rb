class AddFavouriteFoodToAnimal < ActiveRecord::Migration
  def change
    add_column :animals, :favourite_food, :string
  end
end
