(ns to-do.core
  (:require [to-do.templates :as templates]
            [to-do.database :as database])
  [:use compojure.core
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.file :only [wrap-file]]
        [ring.util.response :only [redirect]]])

(defn create-todo [title]
  (database/add-todo {:title title}))
                      
(defn todo-list []
  (apply str(templates/todo-list @database/*todo*)))

(defn todo-new []
  (apply str(templates/todo-new)))
  
(defn todo-add [title]
  (create-todo title)
  (redirect "/"))
  
(defn todo-finished [id]
  (database/complete-todo id)
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