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
   * @see [[https://www.scalatest.org/]]
   */
  object ScalaTest {
    val `scalatest`             = "org.scalatest"          %% "scalatest"          % "3.2.12"
    val `scalatestplus-play`    = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0"
    val `scalatestplus-mockito` = "org.scalatestplus"      %% "mockito-4-5"        % "3.2.12.0"
  }

  object CodingWell {
    val `scala-guice` = "net.codingwell" %% "scala-guice" % "5.1.0"
  }

  object Postgresql {
    val `postgresql` = "org.postgresql" % "postgresql" % "42.3.6"
  }

  object Planet42 {
    val `laika-core` = "org.planet42" %% "laika-core" % "0.18.2"
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

    private val Version = "0.14.2"

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

    private val Version = "0.9.29"

    val `refined`            = "eu.timepit" %% "refined"            % Version
    val `refined-cats`       = "eu.timepit" %% "refined-cats"       % Version // optional
    val `refined-eval`       = "eu.timepit" %% "refined-eval"       % Version // optional, JVM-only
    val `refined-jsonpath`   = "eu.timepit" %% "refined-jsonpath"   % Version // optional, JVM-only
    val `refined-pureconfig` = "eu.timepit" %% "refined-pureconfig" % Version // optional, JVM-only
    val `refined-scalacheck` = "eu.timepit" %% "refined-scalacheck" % Version // optional
    val `refined-scalaz`     = "eu.timepit" %% "refined-scalaz"     % Version // optional
    val `refined-scodec`     = "eu.timepit" %% "refined-scodec"     % Version // optional
    val `refined-scopt`      = "eu.timepit" %% "refined-scopt"      % Version // optional
    val `refined-shapeless`  = "eu.timepit" %% "refined-shapeless"  % Version // optional
  }

  /**
   * @see [[https://github.com/Password4j/password4j]]
   */
  object Password4j {
    val `password4j` = "com.password4j" % "password4j" % "1.5.4"
  }

  object ScalaCache {
    val `scalacache-memcached` = "com.github.cb372" %% "scalacache-memcached" % "0.28.0"
  }
}
