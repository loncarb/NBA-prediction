(ns nba-prediction.routes.new_team
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]
            [ring.util.response :as response])
  (:use [hiccup.form :only [form-to label text-field password-field submit-button hidden-field]]
        [database.mongoDB :only [insert-team update-team]]))

(defn new-team []
  (layout/common [:h1 "New Team"]
                 [:div.main
                  (form-to [:post "/newTeam"]
                           [:div.newTeam
                            [:div
                             (label :teamName "Name: ")
                             (text-field :teamName )]
                            [:div
                             (label :win "Win: ")
                             (text-field :win )]
                            [:div
                             (label :lost "Lost: ")
                             (text-field :lost)]
                            [:div
                             (label :l10 "Score in last 10: ")
                             (text-field :l10)]
                            [:div
                             (submit-button "Create")]])]))

(defn update-team-html [teamName win lost id l10]
  (layout/common [:h1 "Update team"]
                 [:div.main    
                    (form-to [:post "/updateTeam"]
                             [:div.newTeam                                  
                              [:div
                               (label :teamName "Name: ")
                               (text-field :teamName teamName)]
                              [:div
                               (label :win "Win: ")
                               (text-field :win win)]
                              [:div
                               (label :lost "Lost: ")
                               (text-field :lost lost)]
                              [:div
                               (label :l10 "Score in last 10: ")
                               (text-field :l10 l10)]
                              [:div
                                   (hidden-field :id id)
                                   (submit-button "Update")]])]))

(defn create-new-team
  "Add new team to database"
  [teamName win lost l10]
  (do 
    (insert-team teamName win lost l10)
    (response/redirect "/home")))

(defn edit-team
  "Updates team from database"
  [id teamName win lost l10]
  (do
    (println "saljem da se snimi")
    (update-team id teamName win lost l10)
    (response/redirect "/home")))  

(defroutes new-routes
  (GET "/newTeam" [] (new-team))
  (POST "/updateTeam1" [teamName win lost id l10] (update-team-html teamName win lost id l10))
  (POST "/newTeam" [teamName win lost l10] (create-new-team teamName win lost l10))
  (POST "/updateTeam" [id teamName win lost l10] (edit-team id teamName win lost l10)))

