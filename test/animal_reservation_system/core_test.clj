(ns animal-reservation-system.core-test
  (:require [clojure.test :refer :all]
            [animal-reservation-system.core :refer :all]
            [animal-reservation-system.data :as data]))

(def alpha {:name "Alpha" :gender :stallion})
(def beta {:name "Beta" :gender :gelding})
(def gamma {:name "Gamma" :gender :mare})
(def test-horses #{alpha beta gamma})

(deftest apply-procedure-rules-test
  (testing "No rules"
    (is (= test-horses (apply-procedure-rules test-horses ["some procedure"] {}))))

  (testing "No procedures"
    (is (= test-horses (apply-procedure-rules test-horses nil {"some procedure" (constantly false)}))))

  (testing "Single rule"
    (is (= [] (apply-procedure-rules
               test-horses
               ["exclusive procedure"]
               {"exclusive procedure" (constantly false)}))))

  (testing "Single procedure and multiple rules"
    (is (= [gamma] (apply-procedure-rules
                    test-horses
                    ["mare-only procedure"]
                    {"exclusive procedure" (constantly false)
                     "mare-only procedure" #(= :mare (:gender %))}))))

  (testing "Multiple procedure and multiple rules"
    (is (= [alpha] (apply-procedure-rules
                    test-horses
                    ["procedure-1" "procedure-2" "procedure-3"]
                    {"procedure-1" #(#{:mare :stallion} (:gender %))
                     "procedure-3" #(= "Alpha" (:name %))})))))

(deftest take-random-n-test
  (testing "Happy path"
    (is (= 3 (count (take-random-n data/horses 3)))))

  (testing "Horses are picked at random"
    (let [picks (repeatedly 10 #(first (take-random-n data/horses 1)))]
      (is (not= 1 (count (frequencies picks)))))))

(deftest make-reservation!-test
  (testing "Blackout rules"
    (testing "Single new reservation"
      (let [reservations (atom {})
            request {:number 3 :date "2016-06-01" :periods #{"morning"}}
            reserved-horses (make-reservation! reservations test-horses request)]
        (is (= test-horses (set reserved-horses)))
        (is (= {"2016-06-01" {"morning" (set reserved-horses)}} @reservations))))

    (testing "All are booked"
      (let [reservations (atom {"2016-06-01" {"morning" #{alpha}
                                              "afternoon" #{beta}
                                              "evening" #{gamma}}})
            request {:number 3 :date "2016-06-01" :periods #{"morning" "afternoon" "evening"}}
            reserved-horses (make-reservation! reservations test-horses request)]
        (is (empty? reserved-horses))))

    (testing "1 slot is available"
      (let [reservations (atom {"2016-06-01" {"morning" #{alpha}
                                              "evening" #{gamma}}})
            request {:number 3 :date "2016-06-01" :periods #{"morning" "afternoon" "evening"}}
            reserved-horses (make-reservation! reservations test-horses request)]
        (is (= 1 (count reserved-horses)))
        (is (= beta (first reserved-horses)))
        (is (= {"2016-06-01" {"morning" #{alpha beta}
                              "afternoon" #{beta}
                              "evening" #{beta gamma}}}
               @reservations)))))

  (testing "Gender specific procedures"
    (testing "Reservation for physical exam"
      (let [reservations (atom {})
            request {:number 3 :date "2016-06-01" :periods #{"morning"} :procedures ["physical exam"]}
            reserved-horses (make-reservation! reservations test-horses request)]
        (is (= test-horses (set reserved-horses)))
        (is (= {"2016-06-01" {"morning" (set reserved-horses)}} @reservations))))

    (testing "Reservation for uterine biopsy"
      (let [reservations (atom {})
            request {:number 3 :date "2016-06-01" :periods #{"morning"} :procedures ["uterine biopsy (mares only)"]}
            reserved-horses (make-reservation! reservations test-horses request)]
        (is (= [gamma] reserved-horses))
        (is (= {"2016-06-01" {"morning" #{gamma}}} @reservations))))

    (testing "Reservation for uterine biopsy when no mares available"
      (let [reservations (atom {"2016-06-01" {"morning" #{gamma}}})
            request {:number 3 :date "2016-06-01" :periods #{"morning"} :procedures ["uterine biopsy (mares only)"]}
            reserved-horses (make-reservation! reservations test-horses request)]
        (is (empty? reserved-horses))))))
