(ns nba_prediction.routes.home
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]
            [ring.util.response :as response])
  (use [database.mongoDB :only [get-all-teams get-team-names get-admin-by-username get-team-ids get-team-by-name]]
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
  [away-teamName home-teamName]
  (let [away-team (get-team-by-name away-teamName)
        home-team (get-team-by-name home-teamName)]
    (let [away-win (- (Integer/valueOf (:win away-team)) (Integer/valueOf (:lost away-team)))
          home-win (- (Integer/valueOf (:win home-team)) (Integer/valueOf (:lost home-team)))
          away-l10 (Integer/valueOf (:l10 away-team))
          home-l10 (Integer/valueOf (:l10 home-team))
          win-score (- away-win home-win)]
      (session/put! :message "")
      (cond
        (< 3 win-score) (session/put! :message (concat "there is high probability that " (:teamName away-team) " will win"))
        (> -3 win-score) (session/put! :message (concat "there is high probability that " (:teamName home-team) " will win"))
        (and (> 3 win-score) (< -3 win-score)) (cond
                                                 (< away-l10 home-l10) (session/put! :message (concat (:teamName home-team) " has slight advatage over " (:teamName away-team)))
                                                 (< home-l10 away-l10) (session/put! :message (concat (:teamName away-team) " has slight advatage over " (:teamName home-team)))))))
  (response/redirect "/home"))

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
                    (submit-button "Compare"))
                  [:br]
                  [:p (session/get :message)]))

(defroutes home-routes
  (GET "/home" [] (home))
  (POST "/home" [away home] (compare away home)))
