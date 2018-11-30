require 'test_helper'
class GigTest < ActiveSupport::TestCase
  test "gigs have a price" do
    assert gigs(:one).price == 10.00
  end

  test "gigs have a date" do
    assert gigs(:one).date == "2016-04-13 13:00:00"
  end

  test "gigs have an artist" do
    assert_equal(artists(:one), gigs(:one).artist)
  end

  test "gigs have an venue" do
    assert_equal(venues(:one), gigs(:one).venue)
  end
end
