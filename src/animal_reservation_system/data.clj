(ns animal-reservation-system.data)

(def horses #{{:name "All Star" :gender :stallion}
              {:name "Genesis" :gender :gelding}
              {:name "Pumpkin" :gender :mare}
              {:name "Misty" :gender :mare}
              {:name "Good Morning Sunshine" :gender :mare}
              {:name "Boombird" :gender :mare}
              {:name "Sunny" :gender :mare}
              {:name "Brooke" :gender :mare}})

(def reservations (atom {}))
