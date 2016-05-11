(ns animal-reservation-system.core)

(defn take-random-n [animals n]
  (take n (shuffle animals)))

(defn make-reservation [reservations all-horses {:keys [number date periods] :as reservation-request}]
  (let [reserved-horses (take-random-n all-horses number)]
    (doseq [period periods]
      (swap! reservations assoc-in [date period] (set reserved-horses)))
    reserved-horses))
