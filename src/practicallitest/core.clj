(ns practicallitest.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [not-found]]
            [ring.util.response :refer [response]]
            [ring.handler.dump :refer [handle-dump]]
            [clojure.data.json :as json]))

;; Request handling
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def http-response-code
  {:OK 200
   :BAD 404
   :MEH 444})

(defn handler
  "A function that handles all requests from the server.
  `req` is a ring request map"
  [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello Clojure Server world!</h1>"})

(defn hello-html
  "A function that handles all requests from the server.
  Arguments: `req` is a ring request hash-map
  Return: ring response hash-map including :status :headers
  and :body"
  [request]
  {:status (:OK http-response-code)
   :headers {"Content-Type" "text/html"}
   :body "<h1>Hello Clojure Server world!</h1>"})

(defn hello-world
  "A simple hello world handler,
  using ring.util.response"
  [_]
  (response "Hello Clojure World, from ring response"))

(defn scoreboard
  "Returns the current scoreboard as JSON"
  [_]
  (println "Calling the scoreboard handler...")
  {:headers {"Content-type" "application/json"}
   :status (:OK http-response-code)
   :body (json/write-str {:players
                          [{:name "johnny-be-doomed" :high-score 1000001}
                           {:name "jenny-jetpack" :high-score 23452345}]})})

(defroutes webapp
           (GET "/"               [] hello-html)
           (GET "/hello-response" [] hello-world)
           (GET "/scores"               [] scoreboard)
           (GET "/request-info" [] handle-dump)
           (not-found "<h1>Page not found</h1>"))


;; System
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Define a name to represent our server
;; This holds the state of our application,
;; server is running and can receive further commands
;; or our server is not running and the value is nil
(def server (atom nil))



(defn stop-server
  "Gracefully shutdown the server, waiting 100ms"
  []
  (when-not (nil? @server)
    (println "INFO: Gracefully shutting down server...")
    (@server :timeout 100)
    (reset! server nil)))

#_(defn -main [& args]
    ;; The #' is useful when you want to hot-reload code
    ;; You may want to take a look: https://github.com/clojure/tools.namespace
    ;; and http://http-kit.org/migration.html#reload
    (reset! server (server/run-server #'handler {:port (or (first args) 8080)})))


(defn -main
  "Start a httpkit server with a specific port
  #' enables hot-reload of the handler function and anything that code calls"
  [& {:keys [ip port]
      :or   {ip   "0.0.0.0"
             port 8000}}]
  (println "INFO: Starting httpkit server - listening on: " (str "http://" ip ":" port))
  (reset! server (server/run-server #'webapp {:port port})))




(def server-config
  {:ip-address "0.0.0.0"
   :port       8080})

(defn optional-keys [& {:keys [ip-address port]
                        :or   {port (:port server-config) ip-address (:ip-address server-config)}}]
  (str "Port: " port ", address " ip-address))