(ns norad.core
  (:require [immutant.messaging :as msg]))

(defn ring-handler [request]
  (msg/publish "queue.notifications" (slurp (:body request)))
  {:status 200
   :body (str {:success true})
   :headers {"Content-Type" "application/edn"}})
