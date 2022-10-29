ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.8.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.5.5"
libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)

lazy val root = (project in file("."))
  .settings(
    name := "scala"
  )
