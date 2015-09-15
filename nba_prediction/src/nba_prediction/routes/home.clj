(ns nba_prediction.routes.home
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]))

(defn home []
  (layout/common [:h1 "Welcome"]
                 [:table
                  [:tbody
                   [:tr
                    [:th
                     [:h5 "Team"]]
                    [:th
                     [:h5 "Win"]]
                    [:th
                     [:h5 "Lost"]]
                    [:th
                     [:h5 "Last 10"]]]]]))

(defroutes home-routes
  (GET "/home" [] (home)))
