(ns animal-reservation-system.core
  (:require [clojure.set :as set]))

(def procedure-rules {"uterine biopsy (mares only)" #(= :mare (:gender %))})

(defn take-random-n [animals n]
  (take n (shuffle animals)))

(defn reserved-for-a-day [day periods]
  (let [overlapping (select-keys day periods)]
    (apply set/union (vals overlapping))))

(defn apply-procedure-rules [animals procedures rules]
  (if-let [rule-fns (vals (select-keys rules procedures))]
    (filter (apply every-pred rule-fns) animals)
    animals))

(defn make-reservation [reservations all-horses {:keys [number date periods procedures] :as request}]
  (let [already-reserved (reserved-for-a-day (get @reservations date) periods)
        available-for-reservation (set/difference all-horses already-reserved)
        newly-reserved (-> available-for-reservation
                           (apply-procedure-rules procedures procedure-rules)
                           (take-random-n number))]
    (doseq [period periods]
      (swap! reservations update-in [date period] set/union (set newly-reserved)))
    newly-reserved))
