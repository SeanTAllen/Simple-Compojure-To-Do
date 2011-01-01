(ns to-do.database)

(def *todo* (ref []))

(def *todo-index* (ref 0))

(defn next-todo-index []
  (dosync (alter *todo-index* inc)))

(defn add-todo [todo]
  (dosync (alter *todo* conj 
    (assoc todo :id (next-todo-index)))))
  
(defn complete-todo [id]
  (dosync (ref-set *todo* (vec (remove #(= (get % :id) id) @*todo*)))))
