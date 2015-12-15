(defproject advent "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [digest "1.4.4"]
                 [org.clojure/core.logic "0.8.10"]
                 [loco "0.3.1"]
                 [rhizome "0.2.6-SNAPSHOT"]
                 [rhizome "0.2.5"]
                 [automat "0.2.0-alpha2"]
                 [reduce-fsm "0.1.4"]
                 [org.clojure/math.combinatorics "0.1.1"]
                 [incanter "1.9.0"]]
  :main ^:skip-aot advent.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
