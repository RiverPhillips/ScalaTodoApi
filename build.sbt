name := "ScalaTodoApi"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "io.spray" %% "spray-json" % "1.3.5",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8"
)