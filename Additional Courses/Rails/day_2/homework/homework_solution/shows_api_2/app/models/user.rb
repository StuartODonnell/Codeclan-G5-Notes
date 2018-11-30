class User < ActiveRecord::Base
  has_many :favourite_shows
  has_many :favourites, through: :favourite_shows, source: :show
end
