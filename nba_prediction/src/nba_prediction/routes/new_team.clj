(ns nba-prediction.routes.new_team
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]))

(defn new-team []
  (layout/common [:h1 "New Team"]
                 ))

(defroutes new-routes
  (GET "/newTeam" [] (new-team)))

