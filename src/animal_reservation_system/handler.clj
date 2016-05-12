(ns animal-reservation-system.handler
  (:require [animal-reservation-system.core :as core]
            [animal-reservation-system.data :as data]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]))

(defroutes app-routes
  (GET "/" [] (response "OK"))

  (GET "/reservations" []
    (response @data/reservations))

  (POST "/reservations" {request :body}
    (if (map? request)
      (response (core/make-reservation data/reservations data/horses request))
      {:status 400
       :body "I didn't quite get that. Please send a valid reservation request as application/json."}))

  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-defaults api-defaults)))
