ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.0"
ThisBuild / organization := "com.sbboakye"

lazy val root = (project in file("."))
  .settings(
    name := "sbboakye-blogsite"
  )
