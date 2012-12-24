(ns clojure-http.connect
	(:use [clojure-http.convert]))
	
(defn extract-input [input]
	(loop [line nil
	      lines '[]]
	(if (= line "")
	    lines
	    (recur (. input readLine)
	           (cond (seq line) (conj lines line) :else lines)))))

(defn write-output [output response]
	(let [response-length (alength response)]
		(. output write response 0 response-length)))
		
(defn filter-content [input output root]
	(let [request (extract-input input)
	      response (convert request root)]
	(write-output output response)
	(. output close)))