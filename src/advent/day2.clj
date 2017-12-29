(ns advent.day2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-line
  [s]
  (map read-string (str/split s #"x")))

(defn read-data
  []
  (->> (io/resource "input2.txt")
       (slurp)
       (str/split-lines)
       (map parse-line)))

(defn paper [[a b c]]
  (let [s (* a b)
        t (* a c)
        u (* b c)
        slack (min s t u)
        wrap (* 2 (+ s t u))]
    (+ wrap slack)))


(defn ribbon [[a b c]]
  (let [m (take 2 (sort [a b c]))]
    (+ (* 2 (apply + m))
       (apply * [a b c]))))

(->> (read-data) (map paper) (reduce +))
(->> (read-data) (map ribbon) (reduce +))
