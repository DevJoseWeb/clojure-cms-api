(ns app.framework.crud-api-audit
  (:require [app.framework.model-validations :refer [model-errors]]
            [app.util.date :as d]
            [app.framework.model-changes :refer [model-changes]]
            [app.framework.db-api :as db]))

(defn- get-user [request]
  (get-in request [:user :email]))

(defn updated-by [request]
  {:updated_by (get-user request)})

(defn created-by [request]
  {:created_by (get-user request)})

(def changelog-coll "changelog")

(defn save-changelog [database request model-spec action doc]
  (let [errors (not-empty (model-errors doc))
        changes (if (= :update action) (model-changes model-spec doc) nil)
        user (get-user request)
        changelog-doc {
          :action action
          :errors errors
          :doc doc
          :changes changes
          :created_by user
          :created_at (d/now)}]
    (if-not errors
      (db/create database changelog-coll changelog-doc))))
