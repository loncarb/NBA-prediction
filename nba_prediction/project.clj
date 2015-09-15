(defproject nba_prediction "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [lib-noir "0.7.0"]
                 [congomongo "0.4.1"]]
  :plugins [[lein-ring "0.8.12"]
            [lein2-eclipse "2.0.0"]]
  :ring {:handler nba_prediction.handler/app
         :init nba_prediction.handler/init
         :destroy nba_prediction.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}})
