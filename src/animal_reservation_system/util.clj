(ns animal-reservation-system.util)

(defn take-shuffled [n coll]
  (take n (shuffle coll)))

(defn select-vals [map keyseq]
  (vals (select-keys map keyseq)))
