ThisBuild / scalaVersion := "2.13.8"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `domain-modeling` = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(`infra-domain-modeling-scalikejdbc`)
  .settings(
    name := "domain-modeling-web-app",
    version := "0.1",
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

lazy val `infra-domain-modeling-scalikejdbc` = (project in file("infra-domain-modeling-scalikejdbc"))
  .enablePlugins(ScalikejdbcPlugin)
  .settings(
    name := "infra-domain-modeling-scalikejdbc",
    version := "0.1",
    libraryDependencies ++= Seq(
      "org.scalatest"   %% "scalatest"        % "3.2.11" % Test,
      "org.scalikejdbc" %% "scalikejdbc"      % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0"  % Test
    )
  )
