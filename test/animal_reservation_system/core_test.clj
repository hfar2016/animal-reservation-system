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
          request {:number 3 :date "2016-06-01" :periods #{"morning"}}
          reserved-horses (make-reservation reservations test-horses request)]
      (is (= test-horses (set reserved-horses)))
      (is (= {"2016-06-01" {"morning" (set reserved-horses)}} @reservations))))

  (testing "All are booked"
    (let [reservations (atom {"2016-06-01" {"morning" #{"Alpha"}
                                            "afternoon" #{"Beta"}
                                            "evening" #{"Gamma"}}})
          test-horses #{"Alpha" "Beta" "Gamma"}
          request {:number 3 :date "2016-06-01" :periods #{"morning" "afternoon" "evening"}}
          reserved-horses (make-reservation reservations test-horses request)]
      (is (= 0 (count reserved-horses)))))

  (testing "1 slot is available"
    (let [reservations (atom {"2016-06-01" {"morning" #{"Alpha"}
                                            "evening" #{"Gamma"}}})
          test-horses #{"Alpha" "Beta" "Gamma"}
          request {:number 3 :date "2016-06-01" :periods #{"morning" "afternoon" "evening"}}
          reserved-horses (make-reservation reservations test-horses request)]
      (is (= 1 (count reserved-horses)))
      (is (= "Beta" (first reserved-horses)))
      (is (= {"2016-06-01" {"morning" #{"Alpha" "Beta"}
                            "afternoon" #{"Beta"}
                            "evening" #{"Beta" "Gamma"}}}
             @reservations)))))
