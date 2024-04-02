ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.23.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"
libraryDependencies += "org.typelevel" %% "log4cats-slf4j"   % "2.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "cc-lab1",
    idePackagePrefix := Some("com.wrathenn.compilers")
  )
