(ns animal-reservation-system.core-test
  (:require [clojure.test :refer :all]
            [animal-reservation-system.core :refer :all]
            [animal-reservation-system.data :as data]))

(deftest take-random-n-test
  (testing "Happy path"
    (is (= 3 (count (take-random-n data/horses 3)))))

  (testing "Horses are picked at random"
    (let [picks (repeatedly 10 #(first (take-random-n data/horses 1)))]
      (is (not= 1 (count (frequencies picks)))))))

(deftest make-reservation-test
  (testing "Single new reservation"
    (let [reservations (atom {})
          test-horses #{"Some" "Fake" "Horses"}
          reservation-request {:number 3 :date "2016-06-01" :periods #{"morning"}}
          reserved-horses (make-reservation reservations test-horses reservation-request)]
      (is (= test-horses (set reserved-horses)))
      (is (= {"2016-06-01" {"morning" (set reserved-horses)}} @reservations)))))
