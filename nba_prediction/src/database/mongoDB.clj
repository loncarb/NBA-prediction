(ns database.mongoDB
  (:require [noir.session :as session]
            [clj-time.format :as time-format]
            [clj-time.core :as time])
  (:use [somnium.congomongo]))

(def conn 
  (make-connection "nba"))

(set-connection! conn)

(defn- generate-id [collection]
  "Generate entity identifier." 
  (:seq (fetch-and-modify :sequences {:_id collection} {:$inc {:seq 1}}
                          :return-new? true :upsert? true)))

(defn- insert-entity [collection values]
   "Insert an entity into database."
  (insert! collection (assoc values :_id (generate-id collection))))

(defn insert-user
  [firstname lastname username password email]
  "Insert user into database." 
  (insert-entity :users 
                  {:firstname firstname
                   :lastname lastname
                   :email email
                   :username username
                   :password password}))

(defn insert-admin
  [firstname lastname email username password]
  "Insert admin into database." 
  (insert-entity :admin 
                  {:firstname firstname
                   :lastname lastname
                   :email email
                   :username username
                   :password password}))

(defn insert-team
  [teamName win lost l10]
  "Insert team into database." 
  (insert-entity :teams 
                  {:teamName teamName
                   :win win
                   :lost lost
                   :l10 l10}))

(defn get-admin-by-username
  "Find admin by username"
  [username]
  (fetch-one :admin :where {:username username}))

(defn get-user-by-username
  "Find user by username"
  [username]
  (fetch-one :users :where {:username username}))