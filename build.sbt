ThisBuild / version := "1.0.0"

lazy val scala3Version = "3.5.0"
lazy val sbboakye      = "com.sbboakye"

lazy val scalatestVersion = "3.2.19"
lazy val http4sVersion    = "0.23.27"
lazy val circeVersion     = "0.14.9"

lazy val server = (project in file("server"))
  .settings(
    name         := "server",
    scalaVersion := scala3Version,
    organization := sbboakye,
    libraryDependencies ++= Seq(
      "org.http4s"    %% "http4s-ember-client" % http4sVersion,
      "org.http4s"    %% "http4s-ember-server" % http4sVersion,
      "org.http4s"    %% "http4s-dsl"          % http4sVersion,
      "org.http4s"    %% "http4s-circe"        % http4sVersion,
      "org.http4s"    %% "http4s-scalatags"    % "0.25.2",
      "io.circe"      %% "circe-generic"       % circeVersion,
      "io.circe"      %% "circe-literal"       % circeVersion,
      "com.lihaoyi"    % "scalatags_3"         % "0.13.1",
      "org.typelevel" %% "log4cats-slf4j"      % "2.7.0",
      "ch.qos.logback" % "logback-classic"     % "1.5.7",
      "org.scalactic" %% "scalactic"           % scalatestVersion,
      "org.scalatest" %% "scalatest"           % scalatestVersion % "test"
    ),
    Compile / mainClass := Some("com.sbboakye.blog.Application")
  )
