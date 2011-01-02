(ns to-do.server
  [:use [to-do.core :only [app]]
        [ring.adapter.jetty :only [run-jetty]]])

(defn -main []
  (run-jetty #'app {:port 8080}))
  
  