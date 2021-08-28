import play.sbt.PlayImport

name := "play-fm-scala"
organization := "me.khazaddum"
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.8"

lazy val root = ( project in file( "." ) )
  .enablePlugins( PlayScala )


libraryDependencies ++= Seq(
  guice,
  ehcache,
  PlayImport.cacheApi,
  "org.scalatestplus.play"  %% "scalatestplus-play"         % "4.0.1" % Test,
  "com.typesafe.play"       %% "play-slick"                 % "3.0.1",
  "com.typesafe.play"       %% "play-slick-evolutions"      % "3.0.1",
  "com.typesafe.play"       %% "play-json"                  % "2.6.0",
  "com.h2database"          %  "h2"                         % "1.4.189",
  "org.postgresql"          %  "postgresql"                 % "42.1.4",
  "org.typelevel"           %% "cats-core"                  % "1.0.1",
  "com.github.karelcemus"   %% "play-redis"                 % "2.4.0",
  "io.monix"                %% "monix"                      % "3.3.0",
  "org.scalacheck"          %% "scalacheck"                 % "1.14.0"
)


// assembly
test in assembly := {}
target in assembly := file( "target" )
assemblyJarName in assembly := "play-fm-scala.jar"
assemblyMergeStrategy in assembly := {
  case PathList( "META-INF", xs @ _* ) => MergeStrategy.discard
  case x => MergeStrategy.first
}


// coverage
coverageExcludedPackages := ".*Filters.*;.*Routes.*;"
coverageMinimum := 90


// test
fork in test := true
baseDirectory in test := file( "/test/resources/" )