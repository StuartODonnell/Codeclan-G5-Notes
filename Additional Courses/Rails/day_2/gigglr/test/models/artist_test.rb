require 'test_helper'

class ArtistTest < ActiveSupport::TestCase
  test "Artist has a name" do
    assert artists(:one).name == "Justin Bieber"
  end
  test "Artist albums" do
    assert artists(:one).albums.count == 1
  end
  test "artists have gigs" do
    assert_equal(1, artists(:one).gigs.count)
  end
  test "artists have venues" do
    assert_equal(1, artists(:one).venues.count)
  end
end
