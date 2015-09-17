(ns nba_prediction.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [nba_prediction.routes.home :refer [home-routes]]
            [nba-prediction.routes.login :refer [login-routes]]
            [nba-prediction.routes.register :refer [register-routes]]
            [nba-prediction.routes.error :refer [error-routes]]
            [nba-prediction.routes.users :refer [users-routes]]
            [nba-prediction.routes.edit_team :refer [edit-routes]]
            [nba-prediction.routes.new_team :refer [new-routes]]
            [noir.session :as session])
  (:use [database.mongoDB :only[insert-admin insert-user insert-team get-admin-by-username get-all-users get-all-teams]]))

(defn insert-test-user
  []
  (insert-user "Bogdan" "Loncar" "bogdanl@gmail.com" "bogdan" "bogdan")
  (insert-user "Marko" "Markovic" "marko@gmail.com" "marko" "marko"))

(defn insert-test-admin
  []
  (insert-admin "Admin" "Admin" "bogdanl@gmail.com" "admin" "admin"))

(defn insert-test-teams
  []
  (insert-team "Hawks" "60" "22" "0")
  (insert-team "Cavaliers" "53" "29" "4")
  (insert-team "Bulls" "50" "32" "4"))

(defn init []
  (println "nba_prediction is starting")
  (let [admin (get-admin-by-username "admin")
          user (get-all-users)
          team (get-all-teams)]
      (println admin)
      (if-not admin (insert-test-admin))
      (if-not user (insert-test-user))
      (if-not team (insert-test-teams))))

(defn destroy []
  (println "nba_prediction is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes new-routes edit-routes users-routes error-routes register-routes login-routes home-routes app-routes)
      (handler/site)
      (session/wrap-noir-flash)
      (session/wrap-noir-session)
      (wrap-base-url)))
