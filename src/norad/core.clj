(ns norad.core
  (:require [immutant.messaging :as msg]))

(defn ring-handler [request]
  (msg/publish "queue.notifications" {:thing 1})
  {:status 200
   :body "Greetings from Norad"
   :headers {"Content-Type" "text/html"}})
