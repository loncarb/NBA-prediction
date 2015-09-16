(ns nba-prediction.routes.edit_team
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]
            [ring.util.response :as response])
  (use [database.mongoDB :only [get-all-teams delete-team]]
       [hiccup.form :only [form-to label text-field password-field submit-button hidden-field]]))

(defn put-team-in-session
  "Puts selected team in session"
  [team]
  (do
    (session/put! :team team)
    (response/redirect "/updateTeam")))

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
    [:p (:l10 team)]]
   [:td
    (form-to [:delete "/editTeams"]
      [:div
       (hidden-field :id (team :_id))
       (submit-button "Delete")])]
   [:td
    (form-to [:post "/updateTeam1"]
      [:div
       (hidden-field :team team)
       (hidden-field :teamName (:teamName team))
       (hidden-field :win (:win team))
       (hidden-field :lost (:lost team))
       (hidden-field :id (team :_id))
       (hidden-field :l10 (:l10 team))
       (submit-button "Edit")])]])

(defn edit-team []
  (layout/common [:h1 "Edit Team"]
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
                     [:h5 "Last 10"]]
                    [:th
                     [:h5 " "]]
                    [:th
                     [:h5 " "]]]
                  (let [teams (get-all-teams)]
                    (for [team teams]
                      (write-team team)))]]))

(defn html-delete-team
  "Delete team"
  [id]
  (do
    (delete-team id)
    (response/redirect "/editTeams")))

(defroutes edit-routes
  (GET "/editTeams" [] (edit-team))
  (DELETE "/editTeams" [id] (html-delete-team id))
  (POST "/editTeam" [team] (put-team-in-session team)))

