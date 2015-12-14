(ns advent.day11
  (:require [clojure.string :as str]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def triplets
  (map str/join
       (partition 3 (interleave alphabet
                                (drop 1 alphabet)
                                (drop 2 alphabet)))))

(defn ord [c]
  (- (int c) (int \a)))

(defn chr [i]
  (char (+ 97 i)))

(def data "hxbxwxba")

(defn rule1?
  "Passwords must include one increasing straight of at least three
  letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip
  letters; abd doesn't count."
  [password]
  (some #(not= -1 (.indexOf password %)) triplets))

(defn rule2?
  "Passwords may not contain the letters i, o, or l, as these
  letters can be mistaken for other characters and are therefore
  confusing."
  [password]
  (every? #(= -1 (.indexOf password %)) ["i" "o" "l"]))

(defn rule3?
  "Passwords must contain at least two different, non-overlapping
  pairs of letters, like aa, bb, or zz."
  [password]
  (let [pairs (->> (interleave password (drop 1 password))
                   (partition 2)
                   (filter (fn [[a b]] (= a b))))]
    (cond (< (count pairs) 2) false
          (every? #(= % (first pairs)) pairs) false
          :else true)))

(defn as-number [password]
  (loop [v 0 u 1 password (reverse password)]
    (if (empty? password)
      v
      (recur (+ v (* u (ord (first password))))
             (* 26 u)
             (rest password)))))

(defn as-string [number]
  (loop [cc [] number number]
    (if (zero? number)
      cc
      (recur (cons (chr (mod number 26)) cc)
             (quot number 26)))))

(defn next-char [c]
  (char (inc (int c))))

(defn next-string [s]
  (loop [s (reverse s)
         r ()]
    (cond (= \z (first s)) (recur (rest s) (cons \a r))
          :else (str/join (concat (reverse (rest s)) [(next-char (first s))] r)))))

(defn next-password
  [password]
  (->> (next-string password)
       (iterate next-string)
       (drop-while #(not (and (rule1? %)
                              (rule2? %)
                              (rule3? %))))
       first))

(defn solve1 [])

(defn solve2 [])
