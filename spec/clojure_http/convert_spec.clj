(ns clojure-http.convert-spec
  (:use [clojure-http.convert]
        [clojure-http.determine]
        [clojure-http.define]
        [speclj.core]))

(defn should-contain [pattern string]
    (should (string-contains? pattern string)))

(defn convert-to-string [bytes]
	(apply str (map char bytes)))

(def success-code #"200")
(def not-found-code #"404")
(def redirect-code #"302")

(describe "Convert"

	(it "should have 200 for success"
	 	(should-contain success-code (convert-to-string success-header)))
;
	(it "should have 404 for not-found"
	 	(should-contain not-found-code (convert-to-string not-found-header)))
	
	(it "should have 301 for redirect"
	 	(should-contain redirect-code (convert-to-string redirect-header)))
	
    (it "should convert nil to success"
        (should-contain success-code 
			(convert-to-string (convert nil ""))))

    (it "should convert a bad string to success"
        (should-contain success-code 
			(convert-to-string (convert '["bad string"] ""))))

    (it "should convert a simple get to success"
        (should-contain success-code 
			(convert-to-string (convert '["GET / HTTP/1.1\n"] ""))))

    (it "should convert an invalid get to error"
        (should-contain not-found-code 
			(convert-to-string (convert '["GET /foobar HTTP/1.1\n"] ""))))

    (it "should convert a simple put to success"
        (should-contain success-code 
			(convert-to-string (convert '["PUT /form HTTP/1.1\n"] ""))))

    (it "should convert a simple post to success"
        (should-contain success-code 
			(convert-to-string (convert '["POST /form HTTP/1.1\n"] ""))))

    (it "should post data properly"
        (should-contain #"My = Data" 
			(convert-to-string (convert '["POST /form HTTP/1.1\n" "My=Data"] ""))))

    (it "should return redirect for the hardcoded redirect path"
        (should-contain redirect-code 
			(convert-to-string (convert '["GET /redirect HTTP/1.1\n"
			                              "Host: /localhost:3456"] ""))))

    (it "should return location for the hardcoded redirect path"
        (should-contain #"Location: " 
			(convert-to-string (convert '["GET /redirect HTTP/1.1\n"
			                              "Host: /localhost:3456"] "")))) 

)