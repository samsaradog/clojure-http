(ns clojure-http.decode-spec
  (:use [clojure-http.decode]
        [speclj.core]))

(describe "Decode"

	(it "should convert characters"
		(should (= " !\"#$%&()" (decode "%20%21%22%23%24%25%26%28%29"))))
		
	(it "should convert more characters"
		(should (= "*+,/:;<=>" (decode "%2A%2B%2C%2F%3A%3B%3C%3D%3E"))))
		
	(it "should convert even more characters"
		(should (= "?@[]" (decode "%3F%40%5B%5D"))))
)