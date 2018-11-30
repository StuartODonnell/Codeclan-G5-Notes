require 'test_helper'

class AnimalTest < ActiveSupport::TestCase
  test "Animals have a name" do
    assert_equal("Tiger", animals(:one).name)
  end

  test "Animals with legs walk" do
   assert animals(:one).move == "walk"
  end

  test "Animals that dont slither" do
    assert animals(:two).move == "slither"
  end
end
