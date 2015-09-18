# NBA Prediction

NBA Prediction is Clojure application that helps you see NBA statistics and based on those statistics it gives you prediction witch team should win in match of your choosing. 

## Main features

There are two types of users in this app, users and admins.

Users can:

* Login
* Registrate
* Logout
* Choose teams and see apps prediction for that match

Admins can:

* Login
* Logout
* Add new team
* Edit existing team
* Delete team
* Choose teams and see apps prediction for that match

## Setup

Download and install [Leiningen] [1] 1.7.0 or above

Download and install [MongoDB] [2] 

[1]: https://github.com/technomancy/leiningen
[2]: https://www.mongodb.org/

## Running

To start a web server for the application, run:

    lein ring server
    
User:
* username: user
* password: user

Admin:
* username: admin
* password: admin

## Libraries used

* [Compojure 1.1.6] (https://github.com/weavejester/compojure)
* [Hiccup 1.0.5] (https://github.com/weavejester/hiccup)
* [Ring-server 0.3.1] (https://github.com/weavejester/ring-server)
* [Lib-noir 0.7.0] (https://github.com/noir-clojure/lib-noir)
* [Congomongo 0.4.1] (https://github.com/aboekhoff/congomongo)
* [Lein-ring 0.8.12] (https://github.com/weavejester/lein-ring)
* [Lein2-eclipse 2.0.0] (https://github.com/netmelody/lein2-eclipse)

## License

Copyright © 2015 Bogdan Lončar

Distributed under the Eclipse Public License, the same as Clojure.
