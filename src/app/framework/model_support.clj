(ns app.framework.model-support
  (:require [app.util.core :as u]
            [app.util.db :as db-util]
            [app.framework.db-api :as db]
            [app.framework.model-schema :refer [schema-attributes]]))

(defn coll [model-spec]
  (:type model-spec))

(defn id-attribute [model-spec]
  (let [attribute-keys (keys (schema-attributes (:schema model-spec)))]
    (or (some #{:id} attribute-keys)
        :_id)))

(defn id-value [id]
  (or (u/safe-parse-int id) id))

(defn id-query [model-spec id]
  (hash-map (id-attribute model-spec)
            (id-value id)))

(defn valid-id? [model-spec id]
  (let [attribute (id-attribute model-spec)]
    (cond
      (= attribute :id) (u/valid-int? id)
      (= attribute :_id) (db-util/valid-object-id? id)
      :else true)))
