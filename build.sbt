name := "akka-http-tapir"

version := "0.1"

scalaVersion := "2.13.6"

val akkaVersion          = "2.6.8"
val akkaHttpVersion      = "10.2.4"
val tapirVersion         = "0.18.0-M18"
val circeVersion         = "0.14.1"
val akkaHttpCirceVersion = "1.35.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-actor"             % akkaVersion,
  "com.typesafe.akka"           %% "akka-http"              % akkaHttpVersion,
  "com.typesafe.akka"           %% "akka-stream"            % akkaVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-core"             % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % tapirVersion,
  "de.heikoseeberger"           %% "akka-http-circe"        % akkaHttpCirceVersion,
)

// Dependencies for using circe
libraryDependencies ++= Seq(
  "io.circe"                    %% "circe-core"       % circeVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
)

// Dependencies for generating documentation
libraryDependencies ++= Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs"       % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion,
)
//scalacOptions += "-Ypartial-unification"
