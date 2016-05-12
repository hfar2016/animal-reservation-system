(ns animal-reservation-system.core
  (:require [clojure.set :as set]))

(defn take-random-n [animals n]
  (take n (shuffle animals)))

(defn reserved-for-a-day [day periods]
  (let [overlapping (select-keys day periods)]
    (apply set/union (vals overlapping))))

(defn make-reservation [reservations all-horses {:keys [number date periods] :as request}]
  (let [already-reserved (reserved-for-a-day (get @reservations date) periods)
        available-for-reservation (set/difference all-horses already-reserved)
        newly-reserved (take-random-n available-for-reservation number)]
    (doseq [period periods]
      (swap! reservations update-in [date period] set/union (set newly-reserved)))
    newly-reserved))
