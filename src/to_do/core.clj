(ns to-do.core
  (:require [to-do.templates :as templates]
            [to-do.database :as database]
            [compojure.route :as route])
  [:use compojure.core
        [ring.util.response :only [redirect]]])

(defn create-todo [title]
  (database/add-todo {:title title}))
                      
(defn todo-list []
  (apply str(templates/todo-list @database/*todo*)))

(defn todo-new []
  (apply str(templates/todo-new)))
  
(defn todo-create [title]
  (create-todo title)
  (redirect "/"))
  
(defn todo-finished [id]
  (database/complete-todo id)
  (redirect "/"))

(defroutes myroutes
  (GET "/" [] (todo-list))
  (GET "/new" [] (todo-new))
  (POST "/" [title] (todo-create title))
  (DELETE "/" [id] (todo-finished (Integer. id)))    
  (route/resources "/")
  (route/not-found "Page not found"))

(def app myroutes)
