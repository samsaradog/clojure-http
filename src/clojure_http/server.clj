(ns clojure-http.server)

(import '(java.net ServerSocket Socket SocketException)
        '(java.io OutputStreamWriter BufferedReader InputStreamReader))

(defn create-reader [socket] 
    (new BufferedReader 
        ( new InputStreamReader (. socket getInputStream))))

(defn create-writer [socket]
	(. socket getOutputStream))

(defn create-socket [port]
	(new ServerSocket port))
	
(defn accept-connection [server-socket]
	(. server-socket accept))
	
(defn socket-closed? [server-socket]
	(. server-socket isClosed))
	
(defn launch-connector [socket connector root]
	(let [input  (create-reader socket)
          output (create-writer socket)]
		(connector input output root)))

(defn connect-input-and-output [server-socket connector root]
    (let [socket (accept-connection server-socket)]
    	(.start (Thread. #(launch-connector socket connector root)))))

(defn start-server [port connector root]
    (let [server-socket (create-socket port)]
        (while (not (socket-closed? server-socket))
           (try
              (connect-input-and-output server-socket connector root)
            (catch SocketException e)))))

