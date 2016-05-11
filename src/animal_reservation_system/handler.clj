(ns animal-reservation-system.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello, world!")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes api-defaults))
