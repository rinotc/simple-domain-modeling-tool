logLevel := Level.Warn

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.15")

// https://github.com/todokr/zugen
addSbtPlugin("io.github.todokr" % "sbt-zugen" % "2021.12.0")
