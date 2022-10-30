(ns restful-crud.book
  (:require [schema.core :as s]
            [restful-crud.string-util :as str]
            [restful-crud.models.book :refer [Book]]
            [restful-crud.restful :as restful]
            [toucan.db :as db]
            [ring.util.http-response :refer [ok not-found created]]
            [compojure.api.sweet :refer [GET POST PUT DELETE]]))

(defn valid-book-title? [title]
  (str/non-blank-with-max-length? 100 title))

(defn valid-year-published? [year]
  (<= 2000 year 2018))

(s/defschema BookRequestSchema
  {:title          (s/constrained s/Str valid-book-title?)
   :year_published (s/constrained s/Int valid-year-published?)})

;; Create
(defn id->created [id]
  (created (str "/books/" id) {:id id}))

(defn create-book-handler [create-book-req]
  (-> (db/insert! Book create-book-req)
      :id
      id->created))

;; Get All
(defn get-books-handler []
  (ok (db/select Book)))

;; Get By Id
(defn book->response [book]
  (if book
    (ok book)
    (not-found)))

(defn get-book-handler [book-id]
  (-> (Book book-id)
      book->response))

;; Update
(defn update-book-handler [id update-book-req]
  (db/update! Book id update-book-req)
  (ok))

;; Delete
(defn delete-book-handler [book-id]
  (db/delete! Book :id book-id)
  (ok))

;; Routes
(def book-routes
  [(POST "/books" []
     :body [create-book-req BookRequestSchema]
     (create-book-handler create-book-req))
   (GET "/books" []
     (get-books-handler))
   (GET "/books/:id" []
     :path-params [id :- s/Int]
     (get-book-handler id))
   (PUT "/books/:id" []
     :path-params [id :- s/Int]
     :body [update-book-req BookRequestSchema]
     (update-book-handler id update-book-req))
   (DELETE "/books/:id" []
     :path-params [id :- s/Int]
     (delete-book-handler id))])

(def book-entity-route
  (restful/resource {:model Book
                     :name "books"
                     :req-schema BookRequestSchema}))