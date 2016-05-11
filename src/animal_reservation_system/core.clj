(ns animal-reservation-system.core)

(def horses #{"All Star"
              "Genesis"
              "Pumpkin"
              "Misty"
              "Good Morning Sunshine"
              "Boombird"
              "Sunny"
              "Brooke"})

(defn take-random-n [animals n]
  (take n (shuffle animals)))

(defn make-reservation [{:keys [number date period] :as reservation-request}]
  (take-random-n horses number))
