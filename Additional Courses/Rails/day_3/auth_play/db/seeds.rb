# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

User.destroy_all
Account.destroy_all

user_one = User.create!({email: 'jay@gmail.com', password:'password', password_confirmation:'password'})
user_two = User.create!({email: 'rick@gmail.com', password:'password', password_confirmation:'password'})

user_one.accounts.create({name:'local', amount:100.00})

user_two.accounts.create({name:'local', amount:0.01});
user_two.accounts.create({name:'Guernsey Marketing', amount:100000.01});