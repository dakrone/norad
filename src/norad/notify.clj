(ns norad.notify
  (:require [clojure.java.io :refer [file resource]]
            [clojure.java.shell :as sh]
            [clojure.tools.logging :as log]
            [immutant.messaging :as msg]))

(def notify-queue (msg/queue "queue.notifications"))

(def notify-send-cmd "notify-send")
(def terminal-notifier-cmd "terminal-notifier")
(def growlnotify-cmd "growlnotify")

(defonce notify-enabled?
  (= 0 (:exit (sh/sh "which" "notify-send"))))

(defonce growl-enabled?
  (= 0 (:exit (sh/sh "which" "growlnotify"))))

(defonce terminal-notifier-enabled?
  (= 0 (:exit (sh/sh "which" "terminal-notifier"))))

(def icon "immutant.png")

(defn notify-send
  [body & [summary]]
  (when notify-enabled?
    (sh/sh notify-send-cmd
           "--urgency=normal"
           (str "--icon=" (.getAbsolutePath (file (resource icon))))
           "--app-name=Screamy"
           "MCP"
           (str body))))

(defn terminal-notifier
  [body & [summary]]
  (when terminal-notifier-enabled?
    (sh/sh terminal-notifier-cmd
           "-title"
           "Norad"
           "-message"
           (str body))))

(defn growlnotify
  [body & [summary]]
  (when growl-enabled?
    (sh/sh growlnotify-cmd
           (str body))))

(defn notify
  "Basic notify method"
  [msg & [opts]]
  (log/info "Notify message:" msg)
  (cond
   terminal-notifier-enabled? (terminal-notifier msg)
   notify-enabled? (notify-send msg)
   growl-enabled? (growlnotify msg)
   :else
   (log/warn "Unable to notify (no sending method found)")))

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
