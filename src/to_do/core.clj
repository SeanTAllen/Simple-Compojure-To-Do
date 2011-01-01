(ns to-do.core
  (:require [to-do.templates :as templates])
  [:use compojure.core
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.file :only [wrap-file]]
        [ring.util.response :only [redirect]]])

(def *todo* (ref []))

(def *todo-index* (ref 0))

(defn next-todo-index []
  (dosync (alter *todo-index* inc)))

(defn add-todo [todo]
  (dosync (alter *todo* conj 
    (assoc todo :id (next-todo-index)))))
  
(defn create-todo [title]
  (add-todo {:title title}))
  
(defn complete-todo [id]
  (dosync (ref-set *todo* (vec (remove #(= (get % :id) id) @*todo*)))))
            
(defn todo-list []
  (apply str(templates/todo-list @*todo*)))

(defn todo-new []
  (apply str(templates/todo-new)))
  
(defn todo-add [title]
  (create-todo title)
  (redirect "/"))
  
(defn todo-finished [id]
  (complete-todo id)
  (redirect "/"))

(defroutes myroutes
  (GET "/" [] (todo-list))
  (GET "/new" [] (todo-new))
  (POST "/add" [title] (todo-add title))
  (POST "/finished" [id] (todo-finished (Integer. id))))

(def app 
  (-> #'myroutes
      (wrap-file "public")))

(defonce server 
  (run-jetty #'app { :join? false :port 8080}))