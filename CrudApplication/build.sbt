name := """CrudApplication"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "com.typesafe.play" %% "play-streams-experimental" % "2.4.2",
  "com.typesafe.akka" %% "akka-cluster" % "2.3.12",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.typesafe.slick" %% "slick-codegen" % "3.0.0",
  cache,
  "be.cafeba" %% "play-cors" % "1.0",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test",
  "com.typesafe.play" %% "play-mailer" % "4.0.0",
  "net.logstash.logback" % "logstash-logback-encoder" % "4.6",
  ws
)

