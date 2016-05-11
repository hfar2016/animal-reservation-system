(ns animal-reservation-system.core-test
  (:require [clojure.test :refer :all]
            [animal-reservation-system.core :refer :all]))

(deftest take-random-n-test
  (testing "Happy path"
    (is (= 3 (count (take-random-n horses 3)))))

  (testing "Horses are picked at random"
    (let [picks (repeatedly 10 #(first (take-random-n horses 1)))]
      (is (not= 1 (count (frequencies picks)))))))
