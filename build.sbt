ThisBuild / scalaVersion := "2.13.6"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `domain-modeling` = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "DomainModeling",
    version := "1.0",
    libraryDependencies ++= Seq(
      guice,
      "net.codingwell"         %% "scala-guice"        % "5.0.2",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )
