(ns clojure-http.system)

(defn directory-files [directory-name]
	(map (fn [x] (.getName x))(.listFiles(java.io.File. directory-name))))
	
(defn read-binary-file [filename]
	(with-open [input (new java.io.FileInputStream filename)
	            output (new java.io.ByteArrayOutputStream)]
				(clojure.java.io/copy input output)
				(.toByteArray output)))

(defn is-file? [uri root]
    (.isFile (java.io.File. (str root "/" uri))))

(defn is-directory? [uri root]
    (.isDirectory (java.io.File. (str root uri))))

