name := """play_swagger_module"""

version := "1.0-SNAPSHOT"

organization := "com.luiscamilo"

lazy val play_swagger_module = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

publishMavenStyle := true

libraryDependencies += "pl.matisoft" %% "swagger-play24" % "1.4"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


publishTo <<= version { (v: String) =>
  if (v.trim.endsWith("SNAPSHOT"))
    Some(Resolver.file("file",  new File( "../../../maven-repo/snapshots" )) )
  else
    Some(Resolver.file("file",  new File( "../../../maven-repo/releases" )) )
}
