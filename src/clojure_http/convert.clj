(ns clojure-http.convert
	(:use [clojure-http.define])	
	(:use [clojure-http.system])	
	(:use [clojure-http.determine])
	(:use [clojure.string :only (split join replace)]))
	
(defn concat-byte-arrays [a1 a2]
	(byte-array (into (seq a2) (reverse (seq a1)))))
	
;-----------------------------------
; Help with extracting parameters from the command line or headers

(defn pad-equal [s]
    (replace s "=" " = "))

(defn params [s] (split (last (split s #"\?")) #"&"))

(defn format-params [s]
	(let [result (join "\n" (map (fn [x] (pad-equal x)) (params s)))]
	(.getBytes result)))
	
;-----------------------------------

(defn file-response [uri root]
	(let [result (concat-byte-arrays success-header default-response-header)]
	(concat-byte-arrays result default-content)))

;-----------------------------------

(defn files-to-links [filelist]
	(map #(str "<a href=\"/" % "\">" % "</a><br />") filelist))
		
(defn directory-content [uri root]
	(let [listing (apply str (files-to-links (directory-files uri root)))]
	(.getBytes listing)))

(defn directory-response [content root]
	(let [uri (extract-uri (first content))
          result (concat-byte-arrays success-header default-response-header)]
 	(concat-byte-arrays result (directory-content uri root))))

;-------------------------------------

(defn not-found-response []
	(let [result (concat-byte-arrays not-found-header default-response-header)]
    (concat-byte-arrays result not-found-content)))

(defn redirect-response [content root]
	(let [result (concat-byte-arrays redirect-header redirect-response-header)]
    (concat-byte-arrays result default-content)))

(defn parameter-response [content root]
	(let [first-line (first content)
	      result (concat-byte-arrays not-found-header default-response-header)]
    (concat-byte-arrays result (format-params (extract-uri first-line)))))

;------------------------------------

(def get-controller (hash-map is-redirect?    redirect-response,
                              has-parameters? parameter-response,
                              is-file?        file-response,
                              is-directory?   directory-response))

(defn response-to-get [content root]
    (let [first-line (first content)
		  uri (extract-uri first-line)]

         (or (first (for [f (keys get-controller) :when (f uri root)] 
                         ((get get-controller f) content root)))
                  (not-found-response))))

;-----------------------------------

(defn response-to-post [content root]
	(let [result (concat-byte-arrays success-header default-response-header)]
	(concat-byte-arrays result (format-params (last content)))))

;------------------------------------

(defn response-to-put [content root]
	(let [result (concat-byte-arrays success-header default-response-header)]
    (concat-byte-arrays result default-content)))
	
;--------------------------------------

(def controller (hash-map is-get?  response-to-get,
                          is-put?  response-to-put,
                          is-post? response-to-post))

(defn convert [content root] 
    (let [first-line (first content)]

         (first (for [f (keys controller) :when (f first-line)] 
              ((get controller f) content root)))))
