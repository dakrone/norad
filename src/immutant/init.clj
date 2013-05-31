(ns immutant.init
  (:require [norad.core :refer :all]
            [norad.sqs :refer [consume-and-notify]]
            [norad.queues :refer :all]
            [immutant.messaging :as msg]
            [immutant.web :as web]
            [immutant.jobs :as jobs]))

;; Create notification queue if needed
(msg/start notify-queue)
(msg/start metube-queue)

;; Set up HTTP notification handler
(web/start "/notify"
           notify-handler
           :init #(msg/publish notify-queue "Started Norad MCP")
           :destroy #(msg/publish notify-queue "Stopped Norad MCP"))

;; Set up metube HTTP notification handler
(web/start "/metube"
           metube-handler
           :init #(msg/publish notify-queue "Initialized metube")
           :destroy #(msg/publish notify-queue "Destroyed metube"))

;; Begin SQS consumption, every 10 seconds
(jobs/schedule "sqs-notification" consume-and-notify :every 10000)
