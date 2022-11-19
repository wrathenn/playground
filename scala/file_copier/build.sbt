ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.5.3"

lazy val root = (project in file("."))
  .settings(
    name := "file_copier"
  )
