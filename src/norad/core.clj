(ns norad.core
  (:require [norad.notify :refer [notify notify-handler notify-queue]]
            [norad.sqs :refer [consume-and-enqueue]]
            [immutant.messaging :as msg]
            [immutant.scheduling :as schedule]
            [immutant.web :as web]))


(defn -main [& args]
  (let [notify-listener (msg/listen notify-queue notify :concurrency 4)]
    (web/run notify-handler
      {:host "localhost"
       :port 8080
       :path "/"})
    (schedule/schedule consume-and-enqueue :every 5000)
    (notify "Initialized norad SQS polling")))
