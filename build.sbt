name := "akka-http-tapir"

version := "0.1"

scalaVersion := "2.13.6"

val akkaVersion     = "2.6.8"
val akkaHttpVersion = "10.2.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
)
