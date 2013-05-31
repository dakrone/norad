(ns norad.core
  (:require [immutant.messaging :as msg]
            [norad.queues :refer :all]))

(defn notify-handler
  "Handler for enqueuing notification messages"
  [request]
  (try
    (msg/publish notify-queue (slurp (:body request)))
    {:status 200
     :body (str {:success true} "\n")
     :headers {"Content-Type" "application/edn"}}
    (catch Throwable e
      {:status 500
       :body (str {:success false :exception (str e)} "\n")
       :headers {"Content-Type" "application/edn"}})))

(defn metube-handler
  "Handler for enqueuing youtube download requests"
  [request]
  (msg/publish notify-queue (slurp (:body request)))
  (try
    (let [url (slurp (:body request))]
      (if (and (string? url) (not (empty? url)))
        (do
          (msg/publish notify-queue (str "Enqueuing download of " url))
          (msg/publish metube-queue url)
          {:status 200
           :body (str {:success true} "\n")
           :headers {"Content-Type" "application/edn"}})
        {:status 500
         :body (str {:success false :exception "No URL specified"} "\n")
         :headers {"Content-Type" "application/edn"}}))
    (catch Throwable e
      {:status 500
       :body (str {:success false :exception (str e)} "\n")
       :headers {"Content-Type" "application/edn"}})))
