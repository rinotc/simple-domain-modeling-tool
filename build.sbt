import Dependencies._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1"
addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.5.6" cross CrossVersion.full)
ThisBuild / scalacOptions += "-Yrangepos"

lazy val ScalacOptions = Seq(
  "-deprecation",
  "-feature",
  "-Xlint",
  "-Ywarn-dead-code"
)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

lazy val `web` = (project in file("."))
  .enablePlugins(PlayScala)
  .aggregate(`sdmt-core`, `sdmt-usecase`, `sdmt-application`, `sdmt-infra`)
  .dependsOn(`sdmt-core`, `sdmt-usecase`, `sdmt-application`, `sdmt-infra`)
  .settings(
    name := "sdmt-web",
    scalacOptions := ScalacOptions ++ Seq(
      // Suggested here https://github.com/playframework/twirl/issues/105#issuecomment-782985171
      "-Wconf:src=routes/.*:is,src=twirl/.*:is"
    ),
    libraryDependencies ++= Seq(
      guice,
      Google.`guice`,
      Google.`guice-assistedinject`,
      ScalaTest.`scalatestplus-play` % Test,
      ScalaMock.`scalamock`          % Test,
      CodingWell.`scala-guice`,
      Circe.`circe-core`,
      Circe.`circe-parser`,
      Circe.`circe-generic`,
      Circe.`circe-generic-extras`,
      Circe.`play-circe`,
      Pac4j.`play-pac4j`,
      Pac4j.`pac4j-http`
    )
  )

lazy val `sdmt-core` = (project in file("sdmt-core"))
  .dependsOn(sdmtTestDependency)
  .settings(
    name := "sdmt-core",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq(
      Planet42.`laika-core`,
      Refined.`refined`
    )
  )

lazy val `sdmt-usecase` = (project in file("sdmt-usecase"))
  .dependsOn(`sdmt-core`)
  .settings(
    name := "sdmt-usecase",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test
    )
  )

lazy val `sdmt-application` = (project in file("sdmt-application"))
  .dependsOn(`sdmt-core`, `sdmt-usecase`, sdmtTestDependency)
  .settings(
    name := "sdmt-application",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq(
      Google.`guice`
    )
  )

lazy val `sdmt-infra` = (project in file("sdmt-infra"))
  .dependsOn(`sdmt-core`, `sdmt-application`, `sdmt-infra-scalikejdbc`)
  .settings(
    name := "sdmt-infra",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      Postgresql.`postgresql`,
      ScalikeJDBC.`scalikejdbc`,
      ScalikeJDBC.`scalikejdbc-config`,
      ScalikeJDBC.`scalikejdbc-play-initializer`,
      ScalikeJDBC.`scalikejdbc-play-dbapi-adapter`
    )
  )

lazy val `sdmt-infra-scalikejdbc` = (project in file("sdmt-infra-scalikejdbc"))
  .enablePlugins(ScalikejdbcPlugin)
  .settings(
    name := "sdmt-infra-scalikejdbc",
    scalacOptions := Seq(
      "-deprecation",
      "-feature",
      "-Ywarn-dead-code"
    ),
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      ScalikeJDBC.`scalikejdbc`,
      ScalikeJDBC.`scalikejdbc-test` % Test
    )
  )

lazy val `sdmt-test` = (project in file("sdmt-test"))
  .settings(
    name := "sdmt-test",
    scalacOptions := ScalacOptions,
    libraryDependencies ++= Seq(
      ScalaTest.`scalatest` % Test,
      ScalaMock.`scalamock` % Test
    )
  )

lazy val sdmtTestDependency: ClasspathDependency = `sdmt-test` % "test->test"
