(ns norad.core
  (:require [norad.notify]
            [norad.sqs :refer [consume-and-enqueue]]
            [immutant.messaging :as msg]
            [immutant.scheduling :as schedule]
            [immutant.web :as web]))


(defn -main [& args]
  (let [notify-queue (msg/queue "queue.notifications")
        notify-listener (msg/listen notify-queue norad.notify/notify :concurrency 4)]
    (web/run norad.notify/notify-handler
      {:host "localhost"
       :port 8080
       :path "/"})
    (schedule/schedule consume-and-enqueue :every 5000)
    (msg/publish notify-queue "Initialized norad SQS polling")))
