ThisBuild / scalaVersion := "2.13.6"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `domain-modeling` = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "domain-modeling-web-app",
    version := "1.0",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play"             % "5.1.0" % Test,
      "net.codingwell"         %% "scala-guice"                    % "5.0.2",
      "org.postgresql"          % "postgresql"                     % "42.3.1",
      "org.planet42"           %% "laika-core"                     % "0.18.1",
      "org.scalikejdbc"        %% "scalikejdbc"                    % "3.5.0",
      "org.scalikejdbc"        %% "scalikejdbc-config"             % "3.5.0",
      "org.scalikejdbc"        %% "scalikejdbc-play-initializer"   % "2.8.0-scalikejdbc-3.5",
      "org.scalikejdbc"        %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5"
    )
  )
