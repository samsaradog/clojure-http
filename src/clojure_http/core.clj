(ns clojure-http.core
	(:gen-class)
    (:use [clojure-http.server])
	(:use [clojure-http.connect]))

(defn -main [ & args]
	(let [arg-map (apply hash-map args)
	      port-string (get arg-map "-p")
	      root (get arg-map "-d")]
		(if (or (nil? port-string)(nil? root))
		(println "usage: -p <port number> -d <root directory>")
		(start-server (read-string port-string) filter-content root))))
