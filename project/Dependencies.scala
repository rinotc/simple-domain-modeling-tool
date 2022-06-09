import sbt._

object Dependencies {

  object Google {
    val `guice`                = "com.google.inject"            % "guice"                % "5.1.0"
    val `guice-assistedinject` = "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
  }

  object Logback {
    val `logback-classic` = "ch.qos.logback" % "logback-classic" % "1.2.11"
  }

  object TypeSafe {
    val `config` = "com.typesafe" % "config" % "1.4.2"
  }

  /**
   * @see [[https://scalamock.org/]]
   */
  object ScalaMock {
    val `scalamock` = "org.scalamock" %% "scalamock" % "5.2.0"
  }

  /**
   * @see [[https://www.scalatest.org/]]
   */
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

  /**
   * @see [[http://scalikejdbc.org/]]
   */
  object ScalikeJDBC {

    private val Version = "3.5.0"

    val `scalikejdbc`                  = "org.scalikejdbc" %% "scalikejdbc"                  % Version
    val `scalikejdbc-config`           = "org.scalikejdbc" %% "scalikejdbc-config"           % Version
    val `scalikejdbc-test`             = "org.scalikejdbc" %% "scalikejdbc-test"             % Version
    val `scalikejdbc-play-initializer` = "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5"
    val `scalikejdbc-play-dbapi-adapter` =
      "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.8.0-scalikejdbc-3.5"
  }

  /**
   * @see [[https://github.com/circe/circe]]
   */
  object Circe {

    private val Version = "0.14.1"

    val `circe-core`           = "io.circe"     %% "circe-core"           % Version
    val `circe-generic`        = "io.circe"     %% "circe-generic"        % Version
    val `circe-generic-extras` = "io.circe"     %% "circe-generic-extras" % Version
    val `circe-parser`         = "io.circe"     %% "circe-parser"         % Version
    val `play-circe`           = "com.dripower" %% "play-circe"           % "2814.2"
  }

  /**
   * @see [[https://github.com/fthomas/refined#documentation]]
   */
  object Refined {
    val `refined`            = "eu.timepit" %% "refined"            % "0.9.28"
    val `refined-cats`       = "eu.timepit" %% "refined-cats"       % "0.9.28" // optional
    val `refined-eval`       = "eu.timepit" %% "refined-eval"       % "0.9.28" // optional, JVM-only
    val `refined-jsonpath`   = "eu.timepit" %% "refined-jsonpath"   % "0.9.28" // optional, JVM-only
    val `refined-pureconfig` = "eu.timepit" %% "refined-pureconfig" % "0.9.28" // optional, JVM-only
    val `refined-scalacheck` = "eu.timepit" %% "refined-scalacheck" % "0.9.28" // optional
    val `refined-scalaz`     = "eu.timepit" %% "refined-scalaz"     % "0.9.28" // optional
    val `refined-scodec`     = "eu.timepit" %% "refined-scodec"     % "0.9.28" // optional
    val `refined-scopt`      = "eu.timepit" %% "refined-scopt"      % "0.9.28" // optional
    val `refined-shapeless`  = "eu.timepit" %% "refined-shapeless"  % "0.9.28" // optional
  }

  /**
   * @see [[https://github.com/Password4j/password4j]]
   */
  object Password4j {
    val `password4j` = "com.password4j" % "password4j" % "1.5.4"
  }
}
