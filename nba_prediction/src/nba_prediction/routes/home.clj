(ns nba_prediction.routes.home
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]))

(defn home []
  (layout/common [:h1 "Hello World!"]
                 ))

(defroutes home-routes
  (GET "/home" [] (home)))
