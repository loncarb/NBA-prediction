(ns nba-prediction.routes.error

  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]))

(defn error []
  (layout/common [:h1 "error"]))

(defroutes error-routes
  (GET "/error" [] (error)))