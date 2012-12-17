(ns clojure-http.core
	(:gen-class)
    (:use [clojure-http.server])
	(:use [clojure-http.connect]))

(defn -main [ & args]
	(let [ arg-map (apply hash-map args)
	       port (read-string (get arg-map "-p"))
	       root (get arg-map "-d")]
		(start-server port filter-content root)))
