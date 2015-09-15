(ns nba-prediction.routes.login
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout])
  (:use [hiccup.form :only [form-to label text-field password-field submit-button]]))

(defn login []
  (layout/common [:h1 "Please login"]
                 (form-to [:post "/login"]
                          [:div.loginform
                           [:div 
                            (label :username "Username: ")
                            (text-field :username )]
                           [:div
                            (label :password "Password: ")
                            (password-field :password)]
                           [:div
                            (submit-button "Log In")]])
                 (form-to [:get "/register"]
                          [:div
                            (submit-button "Register")])))

(defroutes login-routes
  (GET "/" [] (login))
  (POST "/" [username password] (login)))