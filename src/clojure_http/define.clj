(ns clojure-http.define)

(def success-header   (.getBytes "HTTP/1.1 200 Success\n"))
(def not-found-header (.getBytes "HTTP/1.1 404 Not Found\n"))
(def redirect-header  (.getBytes "HTTP/1.1 302 Found\n"))

(def default-response-string "Content-Type: text/html; charset=utf-8\n\n")
(def default-response-header (.getBytes default-response-string))

(def gif-response-header  (.getBytes "Content-type: image/gif\n\n"))
(def png-response-header  (.getBytes "Content-type: image/png\n\n"))
(def jpeg-response-header (.getBytes "Content-type: image/jpeg\n\n"))

(def default-content (.getBytes "Hello World\n"))
(def not-found-content (.getBytes "<h1>404 File not found</h1>"))
