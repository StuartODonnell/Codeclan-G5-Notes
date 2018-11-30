require("minitest/autorun")
require("minitest/rg")

require_relative("../musician")
require_relative("../instrument") # UPDATED

class MusicianTest < MiniTest::Test

  def setup
    strat = Instrument.new("Fender Strat", "guitar") # UPDATED
    grand_piano = Instrument.new("Bechstein", "piano") # UPDATED
    @musician = Musician.new("Jimi Hendrix", strat) # UPDATED
    @musician2 = Musician.new("Freddie Mercury", grand_piano) # UPDATED
  end

  def test_musician_has_name
    assert_equal("Jimi Hendrix", @musician.name)
  end

  def test_musician_can_play_song
    assert_equal("I'm playing Hey Joe!", @musician.play_song("Hey Joe"))
  end

  def test_musician_can_play_song_on_piano
    assert_equal("Plink plonk... I'm playing Bohemian Rhapsody!", @musician2.play_song("Bohemian Rhapsody"))
  end

end
