(ns nba_prediction.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [nba_prediction.routes.home :refer [home-routes]]
            [nba-prediction.routes.login :refer [login-routes]]
            [nba-prediction.routes.register :refer [register-routes]]))

(defn init []
  (println "nba_prediction is starting"))

(defn destroy []
  (println "nba_prediction is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes register-routes login-routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))
