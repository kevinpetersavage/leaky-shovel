name := "leaky-shovel"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.firebase" % "firebase-client-jvm" % "2.2.4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.mockito" % "mockito-core" % "1.10.19" % "test",
  "com.xebialabs.restito" % "restito" % "0.5" % "test",
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.10.20",
  "io.spray" %% "spray-json" % "1.3.2",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14"
)