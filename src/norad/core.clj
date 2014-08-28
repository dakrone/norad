(ns norad.core
  (:require [clojure.tools.logging :as log]
            [norad.notify :refer [notify notify-handler notify-queue]]
            [norad.sqs :refer [consume-and-enqueue]]
            [immutant.messaging :as msg]
            [immutant.scheduling :as schedule]
            [immutant.web :as web]))


(defn -main [& args]
  (let [notify-listener (msg/listen notify-queue notify :concurrency 4)]
    (log/info "starting up web handler...")
    (web/run notify-handler
      {:host "localhost"
       :port 8080
       :path "/"})
    (log/info "starting up sqs consumer...")
    (schedule/schedule consume-and-enqueue (schedule/every 5 :seconds))
    (log/info "started.")
    (notify "Norad Initialized")))
