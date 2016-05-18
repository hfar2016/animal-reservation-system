(ns animal-reservation-system.server
  (:require [animal-reservation-system.core :as core]
            [animal-reservation-system.data :as data]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [immutant.web :as web]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [content-type response]]))

(defroutes app-routes
  (GET "/" []
    (-> (response "OK")
        (content-type "text/plain")))

  (GET "/reservations" []
    (response @data/reservations))

  (POST "/reservations" {request :body}
    (if (map? request)
      (response (map :name (core/make-reservation! data/reservations data/horses request)))
      {:status 400
       :body "I didn't quite get that. Please send a valid reservation request as application/json."}))

  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)
      (wrap-defaults api-defaults)))

(defn -main [& args]
  (web/run app {:host "localhost" :port 3000}))

(defn run-dev-server [& args]
  (web/run-dmc app {:host "localhost" :port 3000}))
