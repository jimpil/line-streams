(ns line-streams.core-test
  (:require [clojure.test :refer :all]
            [line-streams.core :refer :all]
            [clojure.string :as str]
            [clojure.java.io :as io])
  (:import (java.io StringReader)))

(defonce CSV-DATA
  (let [lines (repeatedly 20 #(str/join \, (shuffle ["foo" "bar" "baz"])))]
    (cons "a,b,c" lines)))

(deftest core-tests
  (testing "stream-lines"
    (->> CSV-DATA
         (str/join \newline)
         StringReader.
         io/reader
         lines-reducible
         (stream-lines identity)
         (into [])
         (= CSV-DATA)
         is))
  )
