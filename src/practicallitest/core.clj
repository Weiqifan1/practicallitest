(ns practicallitest.core
  (:gen-class)
  (:require [org.httpkit.server :as server]))

;; Request handling
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn handler
  "A function that handles all requests from the server.
  `req` is a ring request map"
  [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello Clojure Server world!</h1>"})


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
  (reset! server (server/run-server #'handler {:port port})))




(def server-config
  {:ip-address "0.0.0.0"
   :port       8080})

(defn optional-keys [& {:keys [ip-address port]
                        :or   {port (:port server-config) ip-address (:ip-address server-config)}}]
  (str "Port: " port ", address " ip-address))