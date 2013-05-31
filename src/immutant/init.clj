(ns immutant.init
  (:require [norad.core :refer :all]
            [immutant.messaging :as msg]
            [immutant.web :as web]
  ;; [immutant.util :as util])
  ))

;; This file will be loaded when the application is deployed to Immutant, and
;; can be used to start services your app needs. Examples:


;; Web endpoints need a context-path and ring handler function. The context
;; path given here is a sub-path to the global context-path for the app
;; if any.

(web/start "/" ring-handler
           :init #(msg/publish "queue.notifications" "Started Norad MCP")
           :destroy #(msg/publish "queue.notifications" "Stopped Norad MCP"))
;; (web/start "/foo" a-different-ring-handler)

;; To start a Noir app:
;; (server/load-views (util/app-relative "src/norad/core/views"))
;; (web/start "/" (server/gen-handler {:mode :dev :ns 'norad}))

;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

(msg/start "queue.notifications")
