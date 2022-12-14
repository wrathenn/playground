ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

val versions = new {
  val circe = "0.14.1"
  val cats = "2.8.0"
  val catsEffect = "2.5.5"
  val scopt = "4.1.0"
}

libraryDependencies += "org.typelevel" %% "cats-core" % versions.cats
libraryDependencies += "org.typelevel" %% "cats-effect" % versions.catsEffect
libraryDependencies += "com.github.scopt" %% "scopt" % versions.scopt

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-generic-extras",
  "io.circe" %% "circe-parser"
).map(_ % versions.circe)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

lazy val root = (project in file("."))
  .settings(
    name := "scala"
  )
