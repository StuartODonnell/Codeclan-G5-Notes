# Deploying Spark App To Heroku

## Objectives

* be able to deploy a Java Spark application to Heroku
* be able to add a PostgreSQL database to a Heroku app

## Initial Setup

Before we get started, there are a few things we need to do:
 * if you haven't already done so, create a free Heroku account [here](https://signup.heroku.com/)
 * install the heroku command-line tools
 * install the maven command-line tools
 
### Install Heroku Command-line Tools

To install the Heroku command-line tools type the following in the terminal:

```bash

$ brew install heroku/brew/heroku
```


### Install Maven Command-line Tools

To install the Maven command-line tools type the following in the terminal:

```bash

$ brew install maven
```

## Configuring Maven

Most of the work in deploying a Spark application to Heroku involves configuring Maven. In order to deploy a Java application anywhere, we have to create a jar file containing our application and all of its dependencies. Open the pom.xml of your Spark Maven project and add the following configuration inside the `<plugins>...</plugins>` tags (above your dependencies tag):

> Maybe send this out via slack

```xml
<!-- pom.xml -->

  <plugins>
  <!-- AS BEFORE -->
    <plugin>
      <artifactId>maven-assembly-plugin</artifactId>
      <executions>
        <execution>
        <phase>package</phase>
        <goals>
          <goal>single</goal>
        </goals>
        </execution>
      </executions>
      <configuration>
        <descriptorRefs>
          <!-- This tells Maven to include all dependencies -->
          <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        <archive>
          <manifest>
            <!-- the name of the file where your main method is -->
            <mainClass>controllers.MainController</mainClass>
          </manifest>
        </archive>
      </configuration>
    </plugin>
  <plugins>
```

## Configuring Heroku

Before we do anything else, we actually have to create a Heroku application. This can be done by using the heroku `create` command. Open a terminal and navigate to your project root. First of all we need to create a git repository for the application:

## Create Git Repository

```bash

$ git init
$ git add .
$ git commit -m "initial commit"
```

Next you need to log in to heroku (You will be asked for your username and password):

```bash

$ heroku login
```

The next step is to create the actual application which will be on Heroku. We can do this by using the `heroku create` command. We can also pass it the name we want our application to be called on heroku e.g.

```bash
$ heroku create spark-heroku-example #choose your own application name
```

If we don't supply a name for the heroku application, then heroku will generate a name by itself.
Now that you have a Heroku application, we have to configure how to deploy it using Maven. 

We have to make sure our application name matches that of the Heroku application. So the first thing we need to change is the `<artifactId>` to match that of the Heroku application

```xml
    <artifactId>spark-heroku-example</artifactId> <!-- this should match Heroku appliacation name -->
    <version>1.0-SNAPSHOT</version>
```

This is pretty straightfoward using the Heroku Maven plugin which we can add to our `pom.xml`.

```xml
<!-- pom.xml -->
  
  <plugins>
  <!-- AS BEFORE -->
    <plugin>
      <groupId>com.heroku.sdk</groupId>
      <artifactId>heroku-maven-plugin</artifactId>
      <version>2.0.3</version>
      <configuration>
        <jdkVersion>1.8</jdkVersion>
        <!-- Use your own application name -->
        <appName>spark-heroku-example</appName> 
        <processTypes>
            <!-- Tell Heroku how to launch your application -->
            <!-- You might have to remove the ./ in front   -->
            <!-- Use your own app name for the jar file -->
          <web>java -jar ./target/spark-heroku-example-1.0-SNAPSHOT-jar-with-dependencies.jar</web>
        </processTypes>
      </configuration>
    </plugin>
  </plugins>
```

**NOTE** - the <appName> and the name of the jar file depends on the `artifactId` and `version` at the top of the `pom.xml file`. So if you have the following: 

```xml
    <artifactId>spark-heroku-example</artifactId>
    <version>1.0-SNAPSHOT</version>
```

Then the `<appName>` will be

```xml
  <appName>spark-heroku-example</appName> 
```

And the name of the jar file file be `spark-heroku-example-1.0-SNAPSHOT-jar-with-dependencies.jar`

And your `web` tag will look like:

```xml
  <web>java -jar ./target/spark-heroku-example-1.0-SNAPSHOT-jar-with-dependencies.jar</web>
```

Our project has dependencies so we need to use the maven dependency plugin to make sure all the project dependencies are copied:

```xml
<!-- pom.xml -->

  <plugins>
    <!-- AS BEFORE -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-dependency-plugin</artifactId>
      <version>3.0.1</version>
      <executions>
        <execution>
          <id>copy-dependencies</id>
          <phase>package</phase>
          <goals><goal>copy-dependencies</goal></goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
```

## Making Spark Listen on the Correct Port

When we've been running our Spark applications on `localhost` we've been running on port 4567. Heroku however, assigns the application a new port every time we deploy it. Therefore, so that Spark can handle the requests, we have to get the port assigned by Heroku and tell Spark to use it. Let's create a method in our `MainController` called `getHerokuAssignedPort()` which will return a port number as an `int`.

```java
//MainController.java
public class MainController {
    public static void main(String[] args) {
      // ...
    }

    static int getHerokuAssignedPort() {
        
    }
}
```

The first thing we need to do in this method is create an instance of Java's `ProcessBuilder` class. This class is used to create processes on the operating system.

```java
//MainController.java
public class MainController {
    public static void main(String[] args) {
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
    }
}
```

The process created has an environment in which it is running. We want to get some information from this so we need to call the `environment()` method on our `ProcessBuilder` object. The environment is a map with strings for both the key and the value. We want to get which port the process is running on, by looking up the item with the key `PORT` in the environment. If this is null, then we'll return the default value of 4567 for the port, otherwise we'll return the value we get from looking up the value for `PORT` in the environment. As this is a string we will need to convert it to an `Integer`.

```java
//MainController.java
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
```

Finally, we need to tell our app which port to run on. Spark has a method called `port()` which takes an `int` parameter, to set the port to run on. We want to use whatever our `getHerokuAssignedPort()` method returns. We'll add the call to the `port()` method at the start of the `main` function in `MainController`.

```java
//MainController.java
public class MainController {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());  //NEW 
        //AS BEFORE
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
```


## Set Up PostgreSQL Database On Heroku

Since our app uses a PostgreSQL database, we need to set up a PostgreSQL database for our app on Heroku. Thankfully, Heroku has support for adding a PostgreSQL database to an app. We don't set this up from the command line, but from the Heroku web site itself.

 - Sign into Heroku at `www.heroku.com` and go to "Dashboard"

  - You should see your app in the list of apps
    - Click on the link to the app you are deploying..

  - Click on `Configure Add-ons`

  - Type postgres in the `Add-ons` box. It should find `Heroku Postgres`

  - Hit return and select `Hobby-Dev` from the dropdown and click `Provision`

  - Click on the link for the database and go to `Settings`

  - Click on `View Credentials`

This will show the details for the database on the heroku server. It should look something like:

```
Host :          ec2-50-19-105-188.compute-1.amazonaws.com
Database :      d1u684njfmn1vk
User :          pxceqzzobcoqmu
Port :          5432
Password:       eff4e6e9e99bacdfe87cbdb499fb961672df1acf2d2adee023deaff77c9a77be
```

This is the database our app will connect to so we'll need to update the details in our app. In the `hibernate.cfg.xml` file we need to update the `hibernate.connection.url` property so that it uses these credentials to connect to the database on heroku. This takes the format

```xml
<property name="hibernate.connection.url">jdbc:postgresql://<HOST>/<DATABASE>?user=<USER>&amp;password=<PASSWORD> </property>

```

eg:

```xml
<!-- hibernate.cfg.xml -->
<property name="hibernate.connection.url">jdbc:postgresql://ec2-50-19-105-188.compute-1.amazonaws.com:5432/d1u684njfmn1vk?user=pxceqzzobcoqmu&amp;password=eff4e6e9e99bacdfe87cbdb499fb961672df1acf2d2adee023deaff77c9a77be</property>

```

> Take time to make sure all the students are up to speed at this point. 

## Deploy App To Heroku

We should now be ready to finally deploy our app to Heroku. Before we do this we'll commit all our changes to git

```bash
git add .
git commit -m "<YOUR COMMENT>"
```

To deploy our app we are going to use the maven command-line tools. The command to use is:

```bash
mvn clean heroku:deploy
```

This will do a clean build of our application and deploy it to heroku.

We should now be able to open our application with 

```bash
heroku open
```

Our browser should now show the app running. Note the URL. If you make a note of the URL you should be able to go to your app at any time by typing this URL in your browser.

If you encounter a problem then you can view the log files with 

```bash
heroku logs
```




