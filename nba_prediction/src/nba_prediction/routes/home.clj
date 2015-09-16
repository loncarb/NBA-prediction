(ns nba_prediction.routes.home
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session])
  (use [database.mongoDB :only [get-all-teams]]))

(defn write-team
  "Generates HTML for one team in html table"
  [team]
  [:tr
   [:td
    [:p (:teamName team)]] 
   [:td
    [:p (:win team)]] 
   [:td
    [:p (:lost team)]]
   [:td
    [:p (:l10 team)]]])

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
                     [:h5 "Last 10"]]]
                  (let [teams (get-all-teams)]
                    (for [team teams]
                      (write-team team)))]]))

(defroutes home-routes
  (GET "/home" [] (home)))
