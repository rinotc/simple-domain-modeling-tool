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
    val `scalikejdbc`                  = "org.scalikejdbc" %% "scalikejdbc"                  % "3.5.0"
    val `scalikejdbc-config`           = "org.scalikejdbc" %% "scalikejdbc-config"           % "3.5.0"
    val `scalikejdbc-play-initializer` = "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5"
    val `scalikejdbc-play-dbapi-adapter` =
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5"
    val `scalikejdbc-test` = "org.scalikejdbc" %% "scalikejdbc-test" % "3.5.0"

  }
}
