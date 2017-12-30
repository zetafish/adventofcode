(ns advent.day18
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [advent.core :as c]))

(def sample [".#.#.#"
             "...##."
             "#....#"
             "..#..."
             "#.#..#"
             "####.."])

(def input
  (c/read-input "day18.txt"))

(def around
  (for [x [-1 0 1]
        y [-1 0 1]
        :when (or (not= 0 x)
                  (not= 0 y))]
    [x y]))

(defn make-grid
  [lines]
  (mapv vec lines))

(defn cell
  [grid [x y]]
  (get-in grid [y x] \.))

(defn cell!
  [grid [x y] v]
  (assoc-in grid [y x] v))

(defn draw-grid
  [grid]
  (doseq [r grid]
    (println (str/join r)))
  (println "=============="))

(defn count-around
  [grid pos]
  (->> (map #(mapv + % pos) around)
       (map #(cell grid %))
       (filter #(= \# %))
       (count)))

(defn evolve-cell
  [grid pos]
  (let [k (dec (count grid))]
    (if (#{[0 0] [0 k] [k 0] [k k]} pos)
      \#
      (let [n (count-around grid pos)
            on (condp = (cell grid pos)
                 \# (or (= 2 n) (= 3 n))
                 \. (= 3 n))
            v (if on \# \.)]
        v))))

(defn evolve-grid
  [grid]
  (let [n (count grid)]
    (->> (range n)
         (mapv (fn [y] (->> (range n)
                            (mapv (fn [x]
                                    (evolve-cell grid [x y])))))))))

(defn turn-on-corners
  [grid]
  (let [k (dec (count grid))]
    (-> grid
        (cell! [0 0] \#)
        (cell! [0 k] \#)
        (cell! [k 0] \#)
        (cell! [k k] \#))))

(->> (make-grid ["##.#.#"
                 "...##."
                 "#....#"
                 "..#..."
                 "#.#..#"
                 "####.#"])
     (iterate evolve-grid)
     (drop 5)
     (first)
     (draw-grid))


(->> (make-grid input)
     (turn-on-corners)
     (iterate evolve-grid)
     (drop 100)
     (first)
     (apply concat)
     (filter #(= % \#))
     (count))
