(ns animal-reservation-system.handler-test
  (:require [animal-reservation-system.core :as core]
            [animal-reservation-system.handler :refer :all]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]))

(deftest app-test
  (testing "/"
    (let [response (app (mock/request :get "/"))]
      (is (= 200 (:status response)))
      (is (= "OK" (:body response)))))

  (testing "/not-found"
    (let [response (app (mock/request :get "/not-found"))]
      (is (= 404 (:status response)))
      (is (= "Not Found" (:body response)))))

  (testing "/reservations"
    (with-redefs [core/make-reservation (constantly ["Some" "Fake" "Horses"])]
      (let [response (app (-> (mock/request :post "/reservations" "{}")
                              (mock/content-type "application/json")))]
        (is (= 200 (:status response)))
        (is (= "application/json; charset=utf-8" (get-in response [:headers "Content-Type"])))
        (is (= "[\"Some\",\"Fake\",\"Horses\"]" (:body response)))))))
