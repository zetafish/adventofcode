(ns advent.day4
  (:require [clojure.string :as str]
            [digest :as d]))

(def data "ckczppom")

(defn dig [data n]
  (d/md5 (str data n)))

(defn good? [prefix data n]
  (.startsWith (dig data n) prefix))

(defn find-number [prefix data]
  (first (drop-while #(not (good? prefix data %))
                     (iterate inc 0))))

(find-number "00000"  "abcdef")
(find-number "00000"  data)
(find-number "000000" data)

(dig "abcdef" 609043)
(good? "abcdef" 0)

(.startsWith (dig "abcdef" 609043) "0000000")
