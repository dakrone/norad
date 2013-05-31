(ns immutant.init
  (:require [norad.core :refer [ring-handler]]
            [norad.sqs :refer [consume-and-notify]]
            [immutant.messaging :as msg]
            [immutant.web :as web]
            [immutant.jobs :as jobs]))

;; Create notification queue if needed
(msg/start "queue.notifications")

;; Set up HTTP notification handler
(web/start "/notify"
           notify-handler
           :init #(msg/publish "queue.notifications" "Started Norad MCP")
           :destroy #(msg/publish "queue.notifications" "Stopped Norad MCP"))

;; Begin SQS consumption, every 10 seconds
(jobs/schedule "sqs-notification" consume-and-notify :every 10000)
