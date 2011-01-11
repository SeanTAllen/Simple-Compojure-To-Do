(defproject to-do "1.0.1"
  :description "A simple to do list using compojure"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.5.3"]
                 [ring/ring-core "0.3.5"]
                 [ring/ring-jetty-adapter "0.3.5"]
                 [enlive "1.0.0-SNAPSHOT"]]
  :dev-dependencies [[lein-ring "0.2.4"]]
  :ring {:handler to-do.core/myroutes})
