(ns immutant.init
  (:require [norad.core :refer :all]
            [norad.sqs :refer [consume-and-enqueue]]
            [norad.queues :refer :all]
            [immutant.messaging :as msg]
            [immutant.web :as web]
            [immutant.jobs :as jobs]))

;; Create notification queue if needed
(msg/start notify-queue)

;; Begin SQS consumption, every 10 seconds
(jobs/schedule "sqs-notification" consume-and-enqueue :every 5000)
(msg/publish notify-queue "Initialized norad SQS pulling")
