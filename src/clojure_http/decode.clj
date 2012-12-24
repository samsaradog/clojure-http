(ns clojure-http.decode
	(:use [clojure.string :only (replace)]))
	
(def url-decode-map (hash-map "%20" " "
                              "%21" "!"
                              "%22" "\""
                              "%23" "#"
                              "%24" "$"
                              "%25" "%"
                              "%26" "&"
                              "%28" "("
                              "%29" ")"
                              "%2A" "*"
                              "%2B" "+"
                              "%2C" ","
                              "%2F" "/"
                              "%3A" ":"
                              "%3B" ";"
                              "%3C" "<"
                              "%3D" "="
                              "%3E" ">"
                              "%3F" "?"
                              "%40" "@"
                              "%5B" "["
                              "%5D" "]"
))

(defn decode [string]
	(loop [result string 
	       map-keys (keys url-decode-map)]
		(if (empty? map-keys)
			result
			(recur (replace result (first map-keys) 
			                      (get url-decode-map (first map-keys))) 
			       (rest map-keys)))))