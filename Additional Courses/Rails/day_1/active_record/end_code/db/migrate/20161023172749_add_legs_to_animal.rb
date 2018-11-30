class AddLegsToAnimal < ActiveRecord::Migration
  def change
    add_column :animals, :legs, :integer
  end
end
