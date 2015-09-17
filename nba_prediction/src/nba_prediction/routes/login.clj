(ns nba-prediction.routes.login
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [ring.util.response :as response]
            [noir.session :as session])
  (:use [hiccup.form :only [form-to label text-field password-field submit-button]]
        [database.mongoDB :only [get-admin-by-username get-user-by-username delete-user]]))

(defn logout
  []
  (session/remove! :admin) 
  (session/remove! :user))

(defn login []
  (logout)
  (layout/login [:h1 "Please login"]
     (form-to [:post "/"]
                          [:div.loginform
                           [:p.loginerror (session/flash-get :login-error)]
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

(defn validate-admin
  "Check if username and password for admin are a match"
  [admin password]
  (cond
      (nil? admin) "There is no admin with this username."
      (not= password (:password admin)) "Password is not correct."
      :else true))

(defn validate-user
  "Check if username and password for user are a match"
  [user password]
  (cond
      (nil? user) "There is no user with this username."
      (not= password (:password user)) "Password is not correct."
      :else true))

(defn do-login
  "Checks username and password"
  [username password]
  (let [
    admin (get-admin-by-username username)
    error (validate-admin admin password)
    user (get-user-by-username username)
    error-user (validate-user user password)]
  (cond
    (= true error) (do (session/put! :admin admin) (response/redirect "/home"))
    (= true error-user) (do (session/put! :user user) (response/redirect "/home"))
    :else (do  (println error-user) (session/flash-put! :login-error "User with given username and password does not exist.")
            (response/redirect "/error")))))

(defroutes login-routes
  (GET "/" [] (login))
  (POST "/" [username password] (do-login username password)))