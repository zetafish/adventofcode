(ns advent.day9
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combi]))

(defn parse-line [s]
  (let [m (re-find #"(\w+) to (\w+) = (\d+)" s)]
    [(set [(m 1) (m 2)]) (read-string (m 3))]))

(defn parse [data]
  (->> data
       str/split-lines
       (map parse-line)
       (into {})))

(defn cost [route table]
  (->> (interleave route (drop 1 route))
       (partition 2)
       (map set)
       (map #(get table %))
       (reduce +)))

(defn solve [data f]
  (let [table (parse data)
        places (apply clojure.set/union #{} (map first table))]
    (->> places
         (combi/permutations)
         (map #(cost % table))
         (apply f))))

(defn solve1 [data]
  (solve data min))

(defn solve2 [data]
  (solve data max))

(def small-data
  "London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141")

(def data
  "AlphaCentauri to Snowdin = 66
AlphaCentauri to Tambi = 28
AlphaCentauri to Faerun = 60
AlphaCentauri to Norrath = 34
AlphaCentauri to Straylight = 34
AlphaCentauri to Tristram = 3
AlphaCentauri to Arbre = 108
Snowdin to Tambi = 22
Snowdin to Faerun = 12
Snowdin to Norrath = 91
Snowdin to Straylight = 121
Snowdin to Tristram = 111
Snowdin to Arbre = 71
Tambi to Faerun = 39
Tambi to Norrath = 113
Tambi to Straylight = 130
Tambi to Tristram = 35
Tambi to Arbre = 40
Faerun to Norrath = 63
Faerun to Straylight = 21
Faerun to Tristram = 57
Faerun to Arbre = 83
Norrath to Straylight = 9
Norrath to Tristram = 50
Norrath to Arbre = 60
Straylight to Tristram = 27
Straylight to Arbre = 81
Tristram to Arbre = 90")
