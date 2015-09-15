(ns nba-prediction.routes.users
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]))

(defn users []
  (layout/common [:h1 "Users"]
                 ))

(defroutes users-routes
  (GET "/users" [] (users)))

