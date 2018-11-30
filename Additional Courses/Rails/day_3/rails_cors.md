## Allows Cross Origin Sharing

Install the rack-cors gem.

```
# terminal

gem install rack-cors
```

```
# gemfile

gem 'rack-cors', :require => 'rack/cors'
```

Let's bundle! 

```
# terminal

bundle
```
We need to tell our application how to use the CORS gem.

```ruby
# config/application.rb

module !!YOURMODULENAMEHERE!!!! //THIS IS IN THE FILE ALREADY
  class Application < Rails::Application

    config.middleware.insert_before 0, "Rack::Cors" do
      allow do
        origins '*'
        resource '*', :headers => :any, :methods => [:get, :post, :options, :delete]
      end
    end

    config.active_record.raise_in_transactional_callbacks = true
  end
end

```

Lastly we need to update the config.ru file to use CORS.

```ruby
# config.ru

use Rack::Cors do

  allow do
    origins '*'
    resource '/public/*', :headers => :any, :methods => :get
  end
end
```
