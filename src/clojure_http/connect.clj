(ns clojure-http.connect
	(:use [clojure-http.convert]))

(defn filter-content [input output root]
    (def line nil)
    (def lines '[])
    (while (not (= line ""))
        (def line (. input readLine))
        (def lines (conj lines line)))
    (def result (convert lines root))
    (. output write result 0 (alength result))
    (. output close))
