ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.23.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"

libraryDependencies += "org.typelevel" %% "log4cats-slf4j" % "2.6.0"
libraryDependencies += "net.sf.jung" % "jung-visualization" % "2.0.1"
libraryDependencies += "net.sf.jung" % "jung-graph-impl" % "2.0.1"

addCompilerPlugin("org.typelevel" % "kind-projector_2.13.13" % "0.13.3")

lazy val root = (project in file("."))
  .settings(
    name := "expSystems",
    idePackagePrefix := Some("com.wrathenn.exp")
  )
