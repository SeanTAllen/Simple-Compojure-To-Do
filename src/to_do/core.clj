(ns to-do.core
  (:require [net.cgrand.enlive-html :as html]
            [to-do.templates :as templates])
  [:use compojure.core
        ring.adapter.jetty
        ring.middleware.file])

(def *todo* [{:id 1 :title "A To Do" :completed false}
             {:id 2 :title "Not Done" :completed false}])

(defn completed-todos []
  (filter #(= (get % :completed) false) *todo*))
            
(defn welcome []
  (apply str(templates/todo-list (completed-todos))))

(defn todo-new []
  (apply str(templates/todo-new)))

(defroutes myroutes
  (GET "/" [] (welcome))
  (GET "/new" [] (todo-new))
  (POST "/add" [] (welcome))
  (POST "/finished" [] (welcome)))

(def app 
  (-> #'myroutes
      (wrap-file "public")))

(defonce server 
  (run-jetty #'app { :join? true :port 8080}))