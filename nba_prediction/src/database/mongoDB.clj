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

(defn get-all-teams
  "Return all teams from database"
  []
  (fetch :teams))

(defn get-all-users
  "Return all users from database"
  []
  (fetch :users))

(defn delete-team [id]
  "Delete team from database."
  (let [id1 (Integer/valueOf id)]
  (destroy! :teams {:_id id1})))

(defn delete-user [id]
  "Delete team from database."
  (let [id1 (Integer/valueOf id)]
  (destroy! :users {:_id id1})))

(defn update-team
  [id teamName win lost l10]
  "Update team from database."
   (fetch-and-modify :teams {:_id (Integer/valueOf id)} {:teamName teamName :win win :lost lost :l10 l10}))

(defn get-team-ids
  "Gets all id from teams"
  []
  (fetch :teams :only {:teamName nil :win nil :lost nil :l10 nil}))

(defn get-team-names
  "Gets all names from teams"
  []
  (fetch :teams :only {:_id nil :win nil :lost nil :l10 nil}))

