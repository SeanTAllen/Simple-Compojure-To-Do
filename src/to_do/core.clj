(ns to-do.core
  (:require [net.cgrand.enlive-html :as html])
  [:use compojure.core
        ring.adapter.jetty
        ring.middleware.file])

(def *todo* [{:id 1 :title "A To Do" :completed false}
             {:id 2 :title "Not Done" :completed false}])

(defn completed-todos []
  (filter #(= (get % :completed) false) *todo*))
  
(def *todo-selector* [:.todo])

(html/defsnippet todo-model "templates/list.html" *todo-selector*
  [{:keys [title id]}]
  [:.title] (html/content title)
  [[:input (html/attr= :name "id")]] (html/set-attr :value id))
  
(html/deftemplate index "templates/list.html"
  [todos]
  *todo-selector* (html/substitute (map #(todo-model %) todos)))
          
(defn welcome []
  (apply str(index (completed-todos))))

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