(ns restful-crud.core
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.api.sweet :refer [api routes]]
            [restful-crud.book :refer [book-routes book-entity-route]]
            [restful-crud.user :refer [user-routes user-entity-route]])
  (:gen-class))

(def db-spec
  {:dbtype   "postgres"
   :dbname   "restful-crud"
   :user     "andreymiskov"
   :password ""})

(def swagger-config
  {:ui      "/swagger"
   :spec    "/swagger.json"
   :options {:ui   {:validatorUrl nil}
             :data {:info {:version "1.0.0", :title "Restful CRUD API"}}}})

(def app (api {:swagger swagger-config}
              (apply routes book-entity-route user-entity-route)))

(defn -main
  [& args]
  (db/set-default-db-connection! db-spec)
  (models/set-root-namespace! 'restful-crud.models)
  (run-jetty app {:port 3000}))

(comment

  (db/set-default-db-connection! db-spec)
  (models/set-root-namespace! 'restful-crud.models)
  (run-jetty app {:port 3000})

  #_())