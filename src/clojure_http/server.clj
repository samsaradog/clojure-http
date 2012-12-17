(ns clojure-http.server)

(import '(java.net ServerSocket SocketException)
        '(java.io OutputStreamWriter BufferedReader InputStreamReader))

(defn create-reader [socket] 
    (new BufferedReader 
        ( new InputStreamReader (. socket getInputStream))))

(defn create-writer [socket]
	(. socket getOutputStream))
	
(defn create-socket [port]
    (new ServerSocket port))

(defn connect-input-and-output [system-socket connector root]
    (let [socket (. system-socket accept)
          input  (create-reader socket)
          output (create-writer socket)]
         (connector input output root)))

(defn start-server [port connector root]
    (let [system-socket (create-socket port)]
        (while (not (. system-socket isClosed))
            (connect-input-and-output system-socket connector root))))

