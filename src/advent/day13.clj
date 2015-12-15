(ns advent.day13
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combi]))

(def pattern #"(\w+) would (gain|lose) (\d+).* (\w+)\.")

(def sign {"gain" 1 "lose" -1})

(defn parse-line [line]
  (let [[_ a op val b] (re-matches pattern line)]
    [[a b] (* (get sign op) (read-string val))]))


(defn parse [data]
  (->> data
       (str/split-lines)
       (map parse-line)
       (into {})))

(defn- get-names
  [table]
  (set (apply concat (keys table))))

(defn add-me [table]
  (->>  table
        get-names
        (mapcat (fn [a] [[[a :self] 0] [[:self a] 0]]))
        (into {})
        (merge table)))

(defn score [coll table]
  (let [coll2 (concat coll [(first coll)])
        pairs (partition 2 (interleave coll2 (drop 1 coll2)))]
    (->> pairs
         (map reverse)
         (interleave pairs)
         (map #(get table %))
         (apply +))))


(defn solve1 [table]
  (let [names (get-names table)]
    (->> (combi/permutations names)
         (map #(score % table))
         (apply max))))

(defn solve2 [table]
  (solve1 (add-me table)))


#_ (solve1 (parse input))
#_ (solve2 (parse input))

(def input (slurp "src/advent/day13.txt"))

(def small-input
  "Alice would gain 54 happiness units by sitting next to Bob.
Alice would lose 79 happiness units by sitting next to Carol.
Alice would lose 2 happiness units by sitting next to David.
Bob would gain 83 happiness units by sitting next to Alice.
Bob would lose 7 happiness units by sitting next to Carol.
Bob would lose 63 happiness units by sitting next to David.
Carol would lose 62 happiness units by sitting next to Alice.
Carol would gain 60 happiness units by sitting next to Bob.
Carol would gain 55 happiness units by sitting next to David.
David would gain 46 happiness units by sitting next to Alice.
David would lose 7 happiness units by sitting next to Bob.
David would gain 41 happiness units by sitting next to Carol.")
