import sbt._

object Dependencies {
  lazy val shapeless = "com.chuusai" %% "shapeless" % "2.3.3"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0-M1"
  lazy val osLib = "com.lihaoyi" %% "os-lib" % "0.2.7"
  lazy val logBack = "ch.qos.logback" % "logback-classic" % "1.2.3"
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0"
}
