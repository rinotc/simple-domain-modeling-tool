import Dependencies._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `web` = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(`sdmt-core`, `sdmt-usecase`, `sdmt-query`, `sdmt-application`, `sdmt-infra`)
  .settings(
    name := "sdmt-web",
    libraryDependencies ++= Seq(
      guice,
      Google.`guice`,
      ScalaTest.`scalatestplus-play` % Test,
      CodingWell.`scala-guice`,
      Postgresql.`postgresql`,
      Planet42.`laika-core`,
      ScalikeJDBC.`scalikejdbc`,
      ScalikeJDBC.`scalikejdbc-config`,
      ScalikeJDBC.`scalikejdbc-play-initializer`,
      ScalikeJDBC.`scalikejdbc-play-dbapi-adapter`
    )
  )

lazy val `sdmt-core` = (project in file("sdmt-core"))
  .settings(
    name := "sdmt-core",
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      Planet42.`laika-core`
    )
  )

lazy val `sdmt-usecase` = (project in file("sdmt-usecase"))
  .dependsOn(`sdmt-core`)
  .settings(
    name := "sdmt-usecase",
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test
    )
  )

lazy val `sdmt-application` = (project in file("sdmt-application"))
  .dependsOn(`sdmt-core`, `sdmt-usecase`)
  .settings(
    name := "sdmt-application",
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      Google.`guice`
    )
  )

lazy val `sdmt-query` = (project in file("sdmt-query"))
  .dependsOn(`sdmt-core`)
  .settings(
    name := "sdmt-query",
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test
    )
  )

lazy val `sdmt-infra` = (project in file("sdmt-infra"))
  .dependsOn(`sdmt-core`, `sdmt-query`, `sdmt-infra-scalikejdbc`)
  .settings(
    name := "sdmt-infra",
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      Postgresql.`postgresql`,
      ScalikeJDBC.`scalikejdbc`,
      ScalikeJDBC.`scalikejdbc-config`
    )
  )

lazy val `sdmt-infra-scalikejdbc` = (project in file("sdmt-infra-scalikejdbc"))
  .enablePlugins(ScalikejdbcPlugin)
  .settings(
    name := "infra-domain-modeling-scalikejdbc",
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      ScalikeJDBC.`scalikejdbc`,
      ScalikeJDBC.`scalikejdbc-test` % Test
    )
  )
