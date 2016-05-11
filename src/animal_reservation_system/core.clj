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
