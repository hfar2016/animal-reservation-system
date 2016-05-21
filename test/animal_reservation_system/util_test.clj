(ns animal-reservation-system.util-test
  (:require [clojure.test :refer :all]
            [animal-reservation-system.util :refer :all]
            [animal-reservation-system.data :as data]))

(deftest take-shuffled-test
  (testing "Happy path"
    (is (= 3 (count (take-shuffled 3 [:a :b :c :d :e :f :g :h])))))

  (testing "Elements are picked at random"
    (let [picks (repeatedly 10 #(first (take-shuffled 1 [:a :b :c :d :e :f :g :h])))]
      (is (not= 1 (count (frequencies picks)))))))
