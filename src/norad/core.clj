(ns norad.core
  (:require [immutant.messaging :as msg]))

(defn ring-handler [request]
  (try
    (msg/publish "queue.notifications" (slurp (:body request)))
    {:status 200
     :body (str {:success true} "\n")
     :headers {"Content-Type" "application/edn"}}
    (catch Throwable e
      {:status 500
       :body (str {:success false :exception (str e)} "\n")
       :headers {"Content-Type" "application/edn"}})))
