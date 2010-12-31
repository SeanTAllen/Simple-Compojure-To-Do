(ns to-do.core
  (:require [to-do.templates :as templates])
  [:use compojure.core
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.file :only [wrap-file]]
        [ring.util.response :only [redirect]]])

(def *todo* (ref 
  [{:id 1 :title "A To Do" :completed false}
   {:id 2 :title "Not Done" :completed false}]))

(defn uncompleted-todos []
  (filter #(= (get % :completed) false) @*todo*))

(defn add-todo [todo]
  (dosync (alter *todo* conj todo)))
  
(defn create-todo [title]
  (add-todo {:id 1 :title title :completed false}))
            
(defn todo-list []
  (apply str(templates/todo-list (uncompleted-todos))))

(defn todo-new []
  (apply str(templates/todo-new)))
  
(defn todo-add [title]
  (create-todo title)
  (redirect "/"))

(defroutes myroutes
  (GET "/" [] (todo-list))
  (GET "/new" [] (todo-new))
  (POST "/add" [title] (todo-add title))
  (POST "/finished" [] (todo-list)))

(def app 
  (-> #'myroutes
      (wrap-file "public")))

(defonce server 
  (run-jetty #'app { :join? false :port 8080}))