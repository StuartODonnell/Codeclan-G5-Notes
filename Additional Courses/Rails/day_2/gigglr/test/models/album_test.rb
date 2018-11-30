require 'test_helper'

class AlbumTest < ActiveSupport::TestCase
  test "Album has a name" do
    assert albums(:one).name == "Definatly Maybe"
  end
  test "Album has an artist" do
    assert albums(:one).artist == artists(:two)
  end
end
