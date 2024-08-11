val scala3Version = "3.3.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Page-Replacement",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    concurrentRestrictions in Global += Tags.limit(Tags.Test, 1),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.10" % "test",
      "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
    ),
    scalacOptions += "-Wunused:all"
  )
