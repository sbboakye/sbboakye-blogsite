ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.0"
ThisBuild / organization := "com.sbboakye"

lazy val scalatestVersion = "3.2.19"
lazy val http4sVersion = "0.23.27"
lazy val circeVersion = "0.14.8"

lazy val root = (project in file("."))
  .settings(
    name := "sbboakye-blogsite",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl"          % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s"      %% "http4s-scalatags"    % "0.25.2",
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-literal" % circeVersion,
      "com.lihaoyi" % "scalatags_3" % "0.13.1",
      "org.typelevel" %% "log4cats-slf4j"   % "2.7.0",
      "ch.qos.logback" % "logback-classic" % "1.5.7",
      "org.scalactic" %% "scalactic" % scalatestVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  )
