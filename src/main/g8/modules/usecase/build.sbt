name := """usecase"""

resolvers ++= $name$Build.resolvers

scalaVersion := $name$Build.scalaV

libraryDependencies ++= $name$Build.dependency

updateOptions := updateOptions.value.withCachedResolution(true)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls", "-language:implicitConversions")

sources in (Compile, doc) := Seq.empty

scalaSource in Compile := baseDirectory.value / "app"
scalaSource in Test := baseDirectory.value / "test"

publishArtifact in (Compile, packageDoc) := false

crossPaths := false

logLevel in assembly := Level.Error
