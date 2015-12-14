(ns advent.day8
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [reduce-fsm :as fsm]))

(defn inc-val [val & _] (inc val))

(fsm/defsm-inc mem-count
  [[:start
    \" -> :ready]
   [:ready
    \" -> :stop
    \\ -> :escape-found
    _  -> {:action inc-val} :ready]
   [:escape-found
    \" -> {:action inc-val} :ready
    \\ -> {:action inc-val} :ready
    \x -> :x-found]
   [:x-found
    _ -> :x1]
   [:x1
    _ -> {:action inc-val} :ready]
   [:stop]])

(defn storage-size [s]
  (:value
   (reduce fsm/fsm-event (mem-count 0) s)))

(defn encode-size [s]
  (let [cc (seq s)
        a (count (filter #(= \" %) cc))
        b (count (filter #(= \\ %) cc))]
    (+ 2
       (count cc)
       a b)))

(def data
  (str/split-lines
   (slurp "./src/advent/day8.txt")))


(defn part1 []
  (- (reduce + (map count data))
     (reduce + (map storage-size data))))

(defn part2 []
  (- (reduce + (map encode-size data))
     (reduce + (map count data))))

(part2)
