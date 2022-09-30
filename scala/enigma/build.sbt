ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"
val catsVersion = "2.8.0"

libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion
libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "scala"
  )
