(ns immutant.init
  (:require [norad.sqs :refer [consume-and-enqueue]]
            [immutant.jobs :as jobs]
            [immutant.messaging :as msg]))

(def notify-queue "queue.notifications")

;; Create notification queue if needed
(msg/start notify-queue)

;; Begin SQS consumption, every 10 seconds
(jobs/schedule "sqs-notification" consume-and-enqueue :every 5000)
(msg/publish notify-queue "Initialized norad SQS polling")
