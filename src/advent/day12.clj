(ns advent.day12
  (:require [clojure.data.json :as json]))

(def data (json/read-str (slurp "src/advent/day12.json")))


(defn sum-nums [obj skip?]
  (letfn [(f [obj]
            (cond (skip? obj) 0
                  (integer? obj) obj
                  (sequential? obj) (apply + (map f obj))
                  (map? obj) (apply + (map f (vals obj)))
                  :else 0))]
    (f obj)))


(defn solve1 [data]
  (sum-nums data (constantly false)))

(defn solve2 [data]
  (sum-nums data (fn [obj] (and (map? obj)
                                (some #(= % "red") (vals obj))))))
