(ns clojure-http.system)

(defn full-path [name root]
	(str root "/" name))

(defn directory-files [name root]
	(map (fn [x] (.getName x))(.listFiles(java.io.File. (full-path name root)))))
	
(defn read-binary-file [filename]
	(with-open [input (clojure.java.io/file filename)
	            output (new java.io.ByteArrayOutputStream)]
				(clojure.java.io/copy input output)
				(.toByteArray output)))

(defn is-file? [uri root]
    (.isFile (java.io.File. (str root uri))))

(defn is-directory? [uri root]
    (.isDirectory (java.io.File. (str root uri))))

