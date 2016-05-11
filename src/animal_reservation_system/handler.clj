(ns animal-reservation-system.handler
  (:require [animal-reservation-system.core :as core]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] (response "OK"))
  (POST "/reservations" {reservation-request :body}
        (response (core/make-reservation reservation-request)))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-defaults api-defaults)))
