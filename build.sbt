lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scala-slick-example""",
    version := "2.8.x",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
        "org.sangria-graphql" %% "sangria" % "2.0.0",
        "org.sangria-graphql" %% "sangria-play-json" % "2.0.1",
        "org.sangria-graphql" %% "sangria-spray-json" % "1.0.2",
        "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "com.h2database" % "h2" % "1.4.199",
        "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
      specs2 % Test,
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
