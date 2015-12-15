(ns advent.day15
  (:require [clojure.string :as str]
            [loco.core :refer :all]
            [loco.constraints :refer :all]))

(def data
  "Sprinkles: capacity 2, durability 0, flavor -2, texture 0, calories 3
  Butterscotch: capacity 0, durability 5, flavor -3, texture 0, calories 3
  Chocolate: capacity 0, durability 0, flavor 5, texture -1, calories 8
  Candy: capacity 0, durability -1, flavor 0, texture 5, calories 8"
  {:a [2  0 -2  0  3]  ; a
   :b [0  5 -3  0  3]  ; b
   :c [0  0  5 -1  8]  ; c
   :d [0 -1  0  5  8]  ; d
   })


(defn score-dim [s i]
  (apply +
         (map (fn [k] (* (k s) (get-in data [k i])))
              (keys data))))

(defn- score-cookie [s]
  (map #(score-dim s %) (range 4)))


(defn score [s]
  (apply * (score-cookie s)))


(def model
  [($in :a 0 100)
   ($in :b 0 100)
   ($in :c 0 100)
   ($in :d 0 100)
   ($= 100 ($+ :a :b :c :d))
   ($< 0 :a)
   ($< ($* 1 :d) ($* 5 :b))
   ($< ($+ ($* 2 :a) ($* 3 :b)) ($* 5 :c))
   ($< ($* 1 :c) ($* 5 :d))])


(defn solve1 [m]
  (->> (solutions m)
       (map score)
       (apply max)))

(defn match-calories [n]
  ($= 500 ($+ ($* 3 :a)
              ($* 3 :b)
              ($* 8 :c)
              ($* 8 :d))))

(defn solve2 [m]
  (solve1 (conj m (match-calories 500))))
