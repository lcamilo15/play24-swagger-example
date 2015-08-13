name := """play24-swagger-example"""

version := "1.0-SNAPSHOT"

lazy val play_swagger_module = (project in file("module/play_swagger_module")).enablePlugins(PlayJava)

lazy val root = (project in file(".")).enablePlugins(PlayJava)
  .aggregate(play_swagger_module)
  .dependsOn(play_swagger_module)
  .settings(
    aggregate in update := true
  )

scalaVersion := "2.11.6"

publishMavenStyle := true


libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.webjars" % "bootstrap-slider" % "4.8.3",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.3.4",
  "org.webjars" %% "webjars-play" % "2.4.0-1"
)

libraryDependencies += "pl.matisoft" %% "swagger-play24" % "1.4"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

publishMavenStyle := true


publishTo <<= version { (v: String) =>
  if (v.trim.endsWith("SNAPSHOT"))
    Some(Resolver.file("file",  new File( "../../maven-repo/snapshots" )) )
  else
    Some(Resolver.file("file",  new File( "../../maven-repo/releases" )) )
}
