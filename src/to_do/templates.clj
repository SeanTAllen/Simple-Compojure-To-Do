(ns to-do.templates
  (:require [net.cgrand.enlive-html :as html]))

(def *todo-selector* [:.todo])

(html/defsnippet todo-model "templates/list.html" *todo-selector*
  [{:keys [title id]}]
  [:.title] (html/content title)
  [[:input (html/attr= :name "id")]] (html/set-attr :value id))

(html/deftemplate todo-list "templates/list.html"
  [todos]
  *todo-selector* (html/substitute (map #(todo-model %) todos)))

(html/deftemplate todo-new "templates/new.html" [])
