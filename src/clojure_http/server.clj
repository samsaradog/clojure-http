(ns clojure-http.server)

(import '(java.net ServerSocket Socket SocketException)
        '(java.io OutputStreamWriter BufferedReader InputStreamReader))

(defn create-reader [#^Socket socket] 
    (new BufferedReader 
        ( new InputStreamReader (. socket getInputStream))))

(defn create-writer [#^Socket socket]
	(. socket getOutputStream))
	
(defn create-socket [port]
    (new ServerSocket port))

(defn connect-input-and-output [#^Socket system-socket connector root]
    (let [socket (. system-socket accept)
          input  (create-reader socket)
          output (create-writer socket)]
         (.start (Thread. (connector input output root)))))

(defn start-server [port connector root]
    (let [system-socket (create-socket port)]
        (while (not (. system-socket isClosed))
            (connect-input-and-output system-socket connector root))))

