(ns nba_prediction.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [noir.session :as session]))
(defn admin-menu
  []
  [:header
   [:div.header
    [:div.logo
     [:img {:src "/img/logo.jpg" :alt "NBA"}]]
    [:div.navigation
     [:ul 
      [:li 
       [:a {:href "/home"} "Home"]]
      [:li 
       [:a {:href "/newTeam"} "New team"]]
      [:li 
       [:a {:href "/editTeams"} "Edit teams"]]
      [:li
       [:a {:href "/"} "Logout"]]]]]])

(defn user-menu
  []
  [:header
   [:div.header
    [:div.logo 
     [:img {:src "/img/logo.jpg" :alt "NBA"}]]
    [:div.navigation
     [:ul 
      [:li 
       [:a {:href "/home"} "Home"]]
      [:li
       [:a {:href "/"} "Logout"]]]]]])

(defn login [& body]
  (html5
    [:head
     [:title "Welcome to NBA Prediction"]
     (include-css "/css/screen.css")]
    [:body body]))

(defn common [& content]
  (html5
    [:head
     [:title "NBA Prediction"]
     (include-css "/css/screen.css")]
    [:body 
     (let [admin (session/get :admin), user (session/get :user)]
       (if admin (admin-menu) (user-menu)))
     [:div.content content]]))




