(ns advent.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-input
  [n]
  (->
    (io/resource n)
    (slurp)
    (str/split-lines)))
