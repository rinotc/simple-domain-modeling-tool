import sbt._

object Dependencies {

  object Google {
    val `guice` = "com.google.inject" % "guice" % "5.1.0"
  }
  object Logback {
    val `logback-classic` = "ch.qos.logback" % "logback-classic" % "1.2.11"
  }

  object ScalaTest {
    val `scalatest`          = "org.scalatest"          %% "scalatest"          % "3.2.11"
    val `scalatestplus-play` = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0"
  }

  object CodingWell {
    val `scala-guice` = "net.codingwell" %% "scala-guice" % "5.0.2"
  }

  object Postgresql {
    val `postgresql` = "org.postgresql" % "postgresql" % "42.3.3"
  }

  object Planet42 {
    val `laika-core` = "org.planet42" %% "laika-core" % "0.18.1"
  }

  object ScalikeJDBC {

    private val Version = "3.5.0"

    val `scalikejdbc`                  = "org.scalikejdbc" %% "scalikejdbc"                  % Version
    val `scalikejdbc-config`           = "org.scalikejdbc" %% "scalikejdbc-config"           % Version
    val `scalikejdbc-test`             = "org.scalikejdbc" %% "scalikejdbc-test"             % Version
    val `scalikejdbc-play-initializer` = "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5"
    val `scalikejdbc-play-dbapi-adapter` =
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5"
  }

  object Circe {

    private val Version = "0.14.1"

    val `circe-core`           = "io.circe"     %% "circe-core"           % Version
    val `circe-generic`        = "io.circe"     %% "circe-generic"        % Version
    val `circe-generic-extras` = "io.circe"     %% "circe-generic-extras" % Version
    val `circe-parser`         = "io.circe"     %% "circe-parser"         % Version
    val `play-circe`           = "com.dripower" %% "play-circe"           % "2814.2"
  }
}
