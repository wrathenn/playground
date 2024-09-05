ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"


val versions = new {
  val cats = "3.5.4"
  val circe = "0.14.1"
  val betterMonadicFor = "0.3.1"
}

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % versions.betterMonadicFor)

libraryDependencies ++= Seq(
  "circe-core",
  "circe-generic",
  "circe-parser",
).map("io.circe" %% _ % versions.circe)
libraryDependencies += "tf.tofu" %% "derevo-core" % "0.13.0"
libraryDependencies += "tf.tofu" %% "derevo-circe" % "0.13.0"

libraryDependencies += "com.lihaoyi" %% "pprint" % "0.9.0"

libraryDependencies += "org.typelevel" %% "cats-effect" % versions.cats

libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.23.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"
libraryDependencies += "org.typelevel" %% "log4cats-slf4j" % "2.6.0"
scalacOptions += "-Ymacro-annotations"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test
libraryDependencies += "org.typelevel" %% "cats-effect-testing-scalatest" % "1.5.0" % Test
libraryDependencies += "org.antlr" % "antlr4" % "4.13.2"

lazy val root = (project in file("."))
  .settings(
    name := "tiny_scala",
    idePackagePrefix := Some("com.wrathenn.compilers")
  )