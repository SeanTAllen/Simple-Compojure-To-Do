(ns to-do.core
  (:require [net.cgrand.enlive-html :as html])
  [:use compojure.core
        ring.adapter.jetty
        ring.middleware.file])

(def *todo* [])

(html/deftemplate index "templates/list.html"
  [context])
          
(defn welcome []
  (apply str(index {})))

(defroutes myroutes
  (GET "/" [] (welcome))
  (GET "/new" [] (welcome)
  (POST "/add" [] (welcome)
  (POST "/finished" [] (welcome)))))

(def app 
  (-> #'myroutes
      (wrap-file "public")))

(defonce server 
  (run-jetty #'app { :join? true :port 8080}))