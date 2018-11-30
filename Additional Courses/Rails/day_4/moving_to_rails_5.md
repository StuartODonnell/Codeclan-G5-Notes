## Moving to Rails 5

At the start of Rails week we used a particular switch to install Rails onto our Macs - 

`gem install rails -v=4.2.8`

The `-v` switch allowed us to specify which version of Rails we wanted to install. If we ran this command without the switch we'd automatically get the latest stable version of Rails installed - 5.1.2 (as of June 2017).  

This document will give a little background into a couple of the differences between Rails 4 and 5 in case you are interested in exploring Rails further.

### Further reading

Further information on Rails upgrades can be found here -

[http://edgeguides.rubyonrails.org/upgrading_ruby_on_rails.html](http://edgeguides.rubyonrails.org/upgrading_ruby_on_rails.html)

The Rail 5.0 release notes are here -

[http://edgeguides.rubyonrails.org/5_0_release_notes.html](http://edgeguides.rubyonrails.org/5_0_release_notes.html)

If you are starting a brand new Rails app today you may as well make it in Rails 5. Companies with big codebases would likely to weigh up the costs of developer time in deciding whether to upgrade their existing code. This blogpost gives a good overview into the considerations of this kind of scenario -

[http://blog.planetargon.com/entries/should-i-upgrade-to-rails-5](http://blog.planetargon.com/entries/should-i-upgrade-to-rails-5)


### Wait...was that `rake` or `rails`?!

In Rails 5 we can always use `rails`. Every command that we used to use`rake` for can be replaced with `rails`.

```
rails db:migrate
```

Sometimes behind the scenes `rake` is still doing the work for us.

Bear in mind that you'll likely still see people talking about the old `rake` commands on Stack Overflow etc. All the official documentation now specifies `rails` by default. It's recommended to use `rails` for everything from this point.

### API only applications

A lot of the heavy lifting of a web app is now shifting to the browser, leveraging the power of JavaScript to bring dynamic pages and less static / server-generated content. Sometimes we just want use Rails purely as an API to serve any client we like. We don't always need Rails to generate views for us we just want to serve up some JSON. Rails 5 recognises this shift and provides us with a way of creating a stripped down app. The `--api` switch -

```
rails new my_api --api
```

This new 'mode' is covered in much greater detail here -

[http://edgeguides.rubyonrails.org/api_app.html](http://edgeguides.rubyonrails.org/api_app.html)


