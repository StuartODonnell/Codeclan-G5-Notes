require 'test_helper'

class VenueTest < ActiveSupport::TestCase
  test "venues have a name" do
    assert venues(:one).name == "King Tuts"
  end

  test "venues have a location" do
    assert venues(:one).location == "Glasgow"
  end

  test "venues have gigs" do
    assert_equal(1, venues(:one).gigs.count)
  end

  test "venues have artists" do
    assert_equal(1, venues(:one).artists.count)
  end
end
