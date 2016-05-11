(defproject animal-reservation-system "0.1.0-SNAPSHOT"
  :description "Animal reservation system"
  :dependencies [[compojure "1.5.0"]
                 [org.clojure/clojure "1.8.0"]
                 [ring/ring-defaults "0.2.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler animal-reservation-system.handler/app})
