(ns norad.core
  (:require [immutant.messaging :as msg]
            [norad.queues :refer :all]))

(defn notify-handler
  "Handler for enqueuing notification messages"
  [request]
  (try
    (msg/publish notify-queue (slurp (:body request)))
    {:status 200
     :body (str {:success true} "\n")
     :headers {"Content-Type" "application/edn"}}
    (catch Throwable e
      {:status 500
       :body (str {:success false :exception (str e)} "\n")
       :headers {"Content-Type" "application/edn"}})))
