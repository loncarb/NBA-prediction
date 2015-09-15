(ns nba-prediction.routes.register

(:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]))

(defn register []
  (layout/common [:h1 "Register"]))

(defroutes register-routes
  (GET "/register" [] (register)))