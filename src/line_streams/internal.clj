(ns line-streams.internal
  (:import (java.nio.file Paths)
           (java.io File)
           (java.net URL URI)))

(defprotocol LocalPath
  (local-path [this]))

(extend-protocol LocalPath
  String
  (local-path [s]
    (Paths/get s (make-array String 0)))

  File
  (local-path [file]
    (.toPath file))
  URL
  (local-path [url]
    (if (= "file" (.getProtocol url))
      (Paths/get (.toURI url))
      (throw (IllegalArgumentException. "Non-local URL!"))))
  URI
  (local-path [uri]
    (if (= "file" (.getScheme uri))
      (Paths/get uri)
      (throw (IllegalArgumentException. "Non-local URI!"))))
  )