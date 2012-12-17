(ns clojure-http.determine
	(:use [clojure.string :only (split)]))

(defn elements [line]
    (split line #"\s+"))

(defn extract-command [line]
    (first (elements line)))

(defn extract-uri [line]
    (second (elements line)))

(defn string-contains? [pattern string]
    (not (nil? (re-seq pattern string))))

(defn is-get? [line] 
	(= "GET" (extract-command line)))

(defn is-put? [line] 
	(= "PUT" (extract-command line)))

(defn is-post? [line] 
	(= "POST" (extract-command line)))

(defn has-parameters? [uri root]
    (string-contains? #"=" uri))

(defn is-redirect? [uri root]
    (string-contains? #"redirect" uri))

(defn is-gif-header? [bytesequence]
	(= '(71 73 70 56 57 97) (take 6 bytesequence)))

(defn is-png-header? [bytesequence]
	(= '(-119 80 78 71 13 10 26 10) (take 8 bytesequence)))

(defn is-jpeg-header? [bytesequence]
	(and (= '(-1 -40 -1 -32) (take 4 bytesequence))
		 (= '(74 70 73 70 0) (drop 6 (take 11 bytesequence)))))

