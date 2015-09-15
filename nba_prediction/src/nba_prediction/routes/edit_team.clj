(ns nba-prediction.routes.edit_team
  (:require [compojure.core :refer :all]
            [nba_prediction.views.layout :as layout]
            [noir.session :as session]))

(defn edit-team []
  (layout/common [:h1 "Edit Team"]
                 ))

(defroutes edit-routes
  (GET "/editTeams" [] (edit-team)))

