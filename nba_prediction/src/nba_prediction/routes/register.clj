(ns nba-prediction.routes.register
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [ring.util.response :as response])
  (:use [hiccup.form :only [form-to label text-field password-field submit-button]]
        [database.mongoDB :only [insert-user]]))

(defn register []
  (layout/common [:h1 "Register"]
                 (form-to [:post "/register"]
                          [:div.registerform
                           [:div 
                            (label :firstname "First name: ")
                            (text-field :firstname)]
                           [:div 
                            (label :lastname "Last name: ")
                            (text-field :lastname)]
                           [:div 
                            (label :username "Username: ")
                            (text-field :username )]
                           [:div
                            (label :password "Password: ")
                            (password-field :password)]
                           [:div 
                            (label :email "email: ")
                            (text-field :email)]
                           [:div
                            (submit-button "Register")]])))

(defn register-user
  "Add user to database"
  [firstname lastname username password email]
  (do
    (insert-user firstname lastname username password email)
    (response/redirect "/home")))

(defroutes register-routes
  (GET "/register" [] (register))
  (POST "/register" [firstname lastname username password email] (register-user firstname lastname username password email)))