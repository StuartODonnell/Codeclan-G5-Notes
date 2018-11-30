# Devise

### Pre-Requisites
 - Authentication_introduction.md
 - Auth play project from Authentication introduction.

### Learning Objectives
 - Show implementing a session based authentication system using Devise

## Introduction
We are going to implement a session based authentication system in Rails.  To do this we are going to use a popular Gem called Devise.

Before we follow the devise install instructions, let's think about what we will need following our earlier discussion.

- Signing Up.  Somewhere to store the details of the users who are signing up.
- User table - username, password.
- Password will be encrypted,  never wise to store plaintext passwords.

> Can discuss one-way hashing algorithm, quick one way, hard the other. EG multiplication and factoring.

# Installing Devise.

  Let's follow the instructions on the devise github to get it setup.
  https://github.com/plataformatec/devise

```ruby
# Gemfile
gem 'devise'
```

```bash
# terminal
bundle install
rails generate devise:install
```

```
rails generate devise User
rake routes
rake db:migrate
  ```

```ruby
# app/controllers/accounts_controller.rb
before_action :authenticate_user!
```

This goes at the top of the class, before any methods.

Devise automatically provides a sign_in sign_up form. If we try to go to /accounts it will redirect us to the sign in page. Since we only specified that we would respond with json the app is now crashing. Wo should add html.

```ruby
# app/controllers/application_controller.rb
respond_to :json, :html
```

Try and get our protected resource.  Okay we need to get our passcard, session cookie!

It does give us a form to sign up and log in.
http://localhost:3000/users/sign_up

Let's sign in and then return to   http://localhost:3000/accounts

'Inspect'(by right-clicking anywhere on the page and choosing 'Inspect') the page and go to Application/cookies  and we will see our cookie.  Delete this and we lose our pass.

## Getting accounts of the logged-in User
Currently we get all the accounts when we get accounts. We are now going to alter the application so that accounts belong to users and we only get the accounts for the user who is logged in.

```bash
# terminal
rails g model Account name:text amount:decimal user:references
rake db:migrate
```

And let's tell the User model that a User has many accounts.

```ruby
# app/models/user.rb

  has_many :accounts #ADDED
```

We can now add some seed data:

```ruby
# db/seeds.rb

User.destroy_all
Account.destroy_all

user_one = User.create!({email: 'john@gmail.com', password:'password', password_confirmation:'password'})
user_two = User.create!({email: 'jarrod@gmail.com', password:'password', password_confirmation:'password'})

user_one.accounts.create({name:'local', amount:100.00})

user_two.accounts.create({name:'local', amount:0.01});
user_two.accounts.create({name:'Guernsey Marketing', amount:100000.01});
```

```bash
# terminal

rake db:seed
```

We now need to modify the index route of the accounts controller:

```ruby
# app/controllers/accounts_controller.rb
def index
  @accounts = current_user.accounts
  render( { json: @accounts } )
end
```

Now run the app and log in as Jarrod. You should only see his accounts. 

If you delete the cookie and log in as John you should see different account(s) 
