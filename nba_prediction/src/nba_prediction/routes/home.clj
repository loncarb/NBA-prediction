(ns nba_prediction.routes.home
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session])
  (use [database.mongoDB :only [get-all-teams get-team-names get-admin-by-username get-team-ids ]]
       [hiccup.form :only [drop-down submit-button form-to radio-button]]))

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

(def all-teams [])

(defn write-in-drop-down
  "Writes teams in drop-down element"
  []
  (let [teams (get-team-names)]
    (for [x teams]     
        (let [f (x :teamName)]
          (conj all-teams f)))))

(defn compare
  "Compares two teams"
  []
  )

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
                      (write-team team)))]]
                 
                  (form-to [:post "/home"]
                   (drop-down :away (write-in-drop-down)) "  VS  " (drop-down :home (write-in-drop-down))
                    (submit-button "Compare"))))

(defroutes home-routes
  (GET "/home" [] (home))
  (POST "/home" [] (home)))
