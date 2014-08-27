(defproject norad "0.1.0-SNAPSHOT"
  :description "Dakrone's immutant control center"
  :url "https://github.com/dakrone/norad"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.immutant/immutant "2.0.0-alpha1"]
                 [com.cemerick/bandalore "0.0.5"]
                 [org.clojure/tools.logging "0.3.0"]]
  :plugins [[lein-immutant "2.0.0-alpha1"]]
  :main norad.core
  :resource-paths ["etc"])
