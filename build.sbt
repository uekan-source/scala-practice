scalaVersion := "3.6.3"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.13.0"

// build.sbt
resolvers ++= Seq(
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/ivy-releases/",
  "Sonatype Release"  at "https://oss.sonatype.org/content/repositories/releases/",
  "IxiaS Releases"    at "https://s3-ap-northeast-1.amazonaws.com/maven.ixias.net/releases",
  "IxiaS Snapshots"   at "https://s3-ap-northeast-1.amazonaws.com/maven.ixias.net/snapshots"
)

libraryDependencies += "net.ixias" %% "ixias-core" % "3.1.3-SNAPSHOT"
