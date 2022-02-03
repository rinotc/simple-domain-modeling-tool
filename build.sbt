ThisBuild / scalaVersion := "2.13.6"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `domain-modeling` = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "domain-modeling-web-app",
    version := "1.0",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "net.codingwell"         %% "scala-guice"        % "5.0.2",
      "org.postgresql"          % "postgresql"         % "42.3.1"
    )
  )
