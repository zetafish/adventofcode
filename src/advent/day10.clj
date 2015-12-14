(ns advent.day10
  (:require [clojure.string :as str]))

;; (def data
;;   (map #(- (int %) (int \0))
;;        "1321131112"))

(def data [1 3 2 1 1 3 1 1 1 2])
(def small [1])

(defn- look-and-say
  [data]
  (->> data
       (partition-by identity)
       (mapcat (fn [c] [(count c) (first c)]))))

(defn solve1 [data n]
  (->> data
       (iterate look-and-say)
       (drop n)
       (first)
       (count)))

(defn solve2 [data])
