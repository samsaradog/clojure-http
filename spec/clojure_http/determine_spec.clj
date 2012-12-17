(ns clojure-http.determine-spec
  (:use [clojure-http.determine]
        [speclj.core]))

(describe "Determine"

	(it "should recognize a GET request"
		(should (is-get? "GET / HTTP/1.1\n")))
	
	(it "should recognize a PUT request"
		(should (is-put? "PUT / HTTP/1.1\n")))
		
	(it "should recognize a POST request"
		(should (is-post? "POST / HTTP/1.1\n")))
		
	(it "should recognize parameters in a request"
		(should (has-parameters? "GET /form?key=value HTTP/1.1\n")))
		
	(it "should recognize the hardcoded redirect request"
		(should (is-redirect? "GET /redirect HTTP/1.1\n")))
		
	(it "should recognize a header from a gif file"
		(should (is-gif-header? '(71 73 70 56 57 97 65 0 98 0))))

	(it "should recognize a header from a png file"
		(should (is-png-header? '(-119 80 78 71 13 10 26 10 0 0))))

	(it "should recognize a header from a jpeg file"
		(should (is-jpeg-header? '(-1 -40 -1 -32 0 16 74 70 73 70 0 1))))


)