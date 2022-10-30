(defproject restful-crud "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]

                 ; Web
                 [prismatic/schema "1.4.1"]
                 [metosin/compojure-api "2.0.0-alpha26"]
                 [ring/ring-jetty-adapter "1.9.6"]

                 ; Database
                 [toucan "1.18.0"]
                 [org.postgresql/postgresql "42.5.0"]

                 ; Password Hashing
                 [buddy/buddy-hashers "1.8.1"]]

  :main ^:skip-aot restful-crud.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
