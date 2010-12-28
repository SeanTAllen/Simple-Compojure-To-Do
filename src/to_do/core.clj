(ns to-do.core
  [:use compojure.core
        ring.adapter.jetty])

(defn welcome []
  "hello")

(defroutes myroutes
  (GET "/" [] (welcome))
  (GET "/new" [] (welcome)
  (POST "/add" [] (welcome)
  (POST "/finished" [] (welcome)))))


(defonce server 
  (run-jetty #'myroutes
    { :join? true
    :port 8080}))