ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.7"
libraryDependencies += "org.scalafx" %% "scalafx" % "15.0.1-R21"
libraryDependencies += "org.sameersingh.scalaplot" % "scalaplot" % "0.1"

lazy val root = (project in file("."))
  .settings(
    name := "distribs"
  )
