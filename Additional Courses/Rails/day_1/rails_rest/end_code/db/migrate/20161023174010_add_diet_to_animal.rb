class AddDietToAnimal < ActiveRecord::Migration
  def change
    add_column :animals, :diet, :string
  end
end
