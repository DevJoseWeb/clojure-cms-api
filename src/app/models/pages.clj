(ns app.models.pages
  (:require [app.framework.model-spec :refer [generate-spec]]
            [app.models-shared.content-base-model :refer [content-base-spec]]))

(def model-type :pages)

(def spec (generate-spec
  (content-base-spec model-type)
  {
  :type model-type
  :schema {
    :type "object"
    :properties {
      :title {:type "string"}
      :body {:type "string"}
      :widget_ids {
        :type "array"
        :items {
          :type "integer"
        }
      }
    }
    :additionalProperties false
    :required [:title]
  }
  :indexes [
    {:fields [:title] :unique true}
  ]
}))
