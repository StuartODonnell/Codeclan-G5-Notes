In case there aren't enough shows in your db, copy and past this:

```
//seeds.rb
Show.destroy_all

Show.create!(
    {
        title: "The Great British Bake Off",
        series: 7,
        description: "Master bakers Mary & Paul and the incomparable presenting duo of Mel & Sue are back for the sixth series of The Great British Bake Off.",
        image: "placeholder.jpg",
        programmeID: "b013pqnm"
    }
)

Show.create!(
     {
        title: "Storyville",
        series: 9,
        description: "Series showcasing the best in international documentaries",
        image: "placeholder.jpg",
        programmeID: "b006mfx6"
    }
)

Show.create!(
     {
        title: "EastEnders",
        series: 99,
        description: "
Sonia shares her exciting news, but how will her friends and family react? Carmel gets a knock back. Jane forces Ian to discuss what he is hiding.",
        image: "placeholder.jpg",
        programmeID: "b07w15cg"
    }
)
Show.create!(
     {
        title: "Our Girl",
        series: 2,
        description: "Our Girl -
Series 2: Episode 1
Drama series. Lance Corporal Georgie Lane, an experienced army medic, goes on a humanitarian tour to Kenya, where she quickly lands herself in great danger.",
        image: "placeholder.jpg",
        programmeID: "p0452t2n"
    }
)
Show.create!(
     {
        title: "People Just Do Nothing",
        series: 3,
        description: "Chabuddy organizes for Kurupt FM to perform at a club in Ipswich. With emotions running high and groupie action on the cards, only Steves' travel sickness can kill the vibe now.",
        image: "placeholder.jpg",
        programmeID: "p046y4x6"
    }
)
Show.create!(
     {
        title: "New York: America's Busiest City",
        series: 1,
        description: "Anita Rani, Ade Adepitan, Ant Anstead and Dan Snow reveal the hidden systems and organisational miracles that keep the Central Park clean and green.",
        image: "placeholder.jpg",
        programmeID: "b07w10gz"
    }
)
Show.create!(
     {
        title: "The Ides of March",
        series: 1,
        description: "Political drama. As the campaign to choose the Democratic presidential candidate reaches Ohio, enthusiastic young staffer Stephen Meyers learns a harsh lesson.",
        image: "placeholder.jpg",
        programmeID: "b01mk3xp"
    }
)
Show.create!(
     {
        title: "Hinterland",
        series: 1,
        description: "Detective drama. Following the death of Mari Davies in his last case, Mathias is under investigation by the IPCC. But the murder of a bus driver proves a welcome escape for him.",
        image: "placeholder.jpg",
        programmeID: "b006mfx7"
    }
)
Show.create!(
     {
        title: "Ripper Street",
        series: 3,
        description: "Thriller set during the Victorian era. Three years have passed. Reid is drawn back to Whitechapel to investigate a case made by Jackson and Drake.",
        image: "placeholder.jpg",
        programmeID: "b0790shb"
    }
)
Show.create!(
     {
        title: "The Rack Pack",
        series: 1,
        description: "Snooker, but not as you know it. Let the music, scandal and rivalry of the 80s transport you to the glory days of a very British sport, in this comedy drama.",
        image: "placeholder.jpg",
        programmeID: "p03bv0t5"
    }
)
```

Then run ....

```
//shows_api_3

bundle exec rake db:seed
```


## React frontend to the API

Ok, so we have an API in Rails that serves up Shows and allows us to sign up and login. 

> Hand out notflix_startpoint 

Let's setup our React front end. Let's do our usual npm installs

```
//react
//client
npm install
npm start

//top level
npm install
npm start


http://localhost:3000
```

And let's migrate our db and start our Rails server on port 5000

```
//shows_api_3
bundle exec rake db:migrate
bundle exec rake db:seed

rails s -p 5000
```

> Browse to both URLs to prove there are 2 different servers not coupled to each other.

We've created a React app in ES6. Now as a exercise, I want you to group into pairs and investigate the react app for 10-15 minutes. I want you to draw out a diagram of the various components / states and props in the application.

> See if any students want to draw out a diagram of the react app. Draw out the components of the react app.
  
Our task now is to implement the authentication.

Let's try to browse to the shows page.

```
http://localhost:3000/?#/shows
```

Uh oh! We're getting a 401 (Unauthorized) from our server. This is good. 

We have set up authentication on our server so it makes sense that we aren't allowed the data. Not yet anyway.

So we already have a login box component. When it mounts, it will call fetchUser. 

What we want to do in fetchUser is to do a XMLHTTPRequest get request to http://localhost:5000/users.json. 

If we are already logged in (if we have cookies), the browser will send our cookies and our user details come back, if the browser has no cookies, nothing gets sent and the server will return a 401 (Not auth). 

Lets implement fetchUser now.

```js
//react/client/src/auth/LoginBox.jsx

  fetchUser(){
    console.log("fetching user")
    const request = new XMLHttpRequest()
    request.open("GET", this.props.url + "users.json")
    request.setRequestHeader("Content-Type", "application/json")
    request.withCredentials = true

    request.onload = () => {

      if(request.status === 200){

        console.log('request.responseText', request.responseText)
        const receivedUser = JSON.parse(request.responseText)
        this.setUser(receivedUser)
      }else if(request.status === 401){
        this.setUser(null)
      }
    }
    request.send(null)
  }
```

Here we're checking for a logged in user and setting the state of the component to either currentUser or null.

## Sign In

Let's think about our SignIn.jsx. If you remember from using insomnia to sign in. To generate a cookie / session we sent a POST request to http://localhost:5000/users/sign_in.json. 

The post request will send our username and email, devise will authenticate against that and then return a 201 (Created) if valid. 

If the details are correct, the server will automatically send a cookie which the browser will save for future requests. Easy peasy.

```js
//react/client/src/auth/SignIn.jsx

  signIn(event){
    event.preventDefault()
    const request = new XMLHttpRequest()
    request.open("POST", this.props.url)
    request.setRequestHeader("Content-Type", "application/json")
    request.withCredentials = true

    request.onload = () => { // FAT ARROW! TO RETAIN 'this'
      if(request.status === 201){
        let user = JSON.parse(request.responseText)

        this.props.onSignIn(user)
      }
    }
    const data = {
      user:{
        email:this.state.email,
        password:this.state.password
      }
    }
    request.send(JSON.stringify(data))
  }
```

Our SignUp box is similar except we will post to /users/sign_up.json and we will send a password_confirmation in the request. Again devise will send back a cookie if everything is okay.

```js
//react/client/src/auth/SignUp.jsx

  signUp(event){
    event.preventDefault()
    const request = new XMLHttpRequest()
    request.open("POST", this.props.url)
    request.setRequestHeader("Content-Type", "application/json")
    request.withCredentials = true
    request.onload = () => {
      if (request.status === 201){
        const user = JSON.parse(request.responseText)
        this.props.onSignUp(user)
      }
    }
    const data = {
      user:{
        email:this.state.email,
        password:this.state.password,
        password_confirmation: this.state.passwordConfirmation
      }
    }
    request.send(JSON.stringify(data))
  }
```

Sign out isn't quite as complex. We just send a delete request to our rails api. 

```js
//react/client/src/auth/SignOut.jsx

  signOut(event){
    event.preventDefault()
    const request = new XMLHttpRequest()
    request.open("DELETE", this.props.url)
    request.setRequestHeader("Content-Type", "application/json")
    request.withCredentials = true
    request.onload = () => {
      console.log('signed out', request.status)
      if (request.status === 204){
        this.props.onSignOut(null)
      }
    }
    request.send(null)
  }
```

We're able to sign in, sign up and sign out now! Let's check the Resources > Cookies tab in Chrome dev tools and see if we get a session cookie back from the server.
