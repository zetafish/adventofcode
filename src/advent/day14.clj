(ns advent.day14
  (:require [clojure.string :as str]))

(def input
  "Vixen can fly 19 km/s for 7 seconds, but then must rest for 124 seconds.
Rudolph can fly 3 km/s for 15 seconds, but then must rest for 28 seconds.
Donner can fly 19 km/s for 9 seconds, but then must rest for 164 seconds.
Blitzen can fly 19 km/s for 9 seconds, but then must rest for 158 seconds.
Comet can fly 13 km/s for 7 seconds, but then must rest for 82 seconds.
Cupid can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.
Dasher can fly 14 km/s for 3 seconds, but then must rest for 38 seconds.
Dancer can fly 3 km/s for 16 seconds, but then must rest for 37 seconds.
Prancer can fly 25 km/s for 6 seconds, but then must rest for 143 seconds.")

(def small-input
  "Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.")

(defn parse-line [line]
  (let [pat #"(\w+).*?(\d+).*?(\d+).*?(\d+).*"
        m (re-matches pat line)]
    {:name (m 1)
     :speed (read-string (m 2))
     :fly-time (read-string (m 3))
     :rest-time (read-string (m 4))
     :tick 0
     :loc 0
     :score 0}))


(defn parse [input]
  (map parse-line (str/split-lines input)))


(defn step [{:keys [loc speed fly-time rest-time tick] :as reindeer}]
  (let [next-loc (if (< tick fly-time)
                   (+ loc speed)
                   loc)
        next-tick (mod (inc tick)
                       (+ fly-time rest-time))]
    (assoc reindeer
           :loc next-loc
           :tick next-tick)))


(defn step-all [group]
  (let [updated-group (map step group)
        best (->> updated-group
                  (sort-by :loc)
                  reverse
                  first
                  :loc)]

    (map (fn [r] (if (= best (:loc r))
                   (update r :score inc)
                   r))
         updated-group)))

(defn solve1 [input]
  (->> (parse input)
       (iterate step-all)
       (drop 2503)
       first
       (sort-by :loc)
       last
       :loc))


(defn solve2 [input]
  (->> (parse input)
       (iterate step-all)
       (drop 2503)
       first
       (sort-by :score)
       last
       :score))
