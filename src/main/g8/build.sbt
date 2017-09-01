// give the user a nice default project!
import sbtassembly.MergeStrategy

name := """$name$"""

version := "1.0"

lazy val root = (project in file("."))
  .aggregate(adaptor , domain , usecase , other)
  .dependsOn(adaptor , domain , usecase , other)

lazy val adaptor = (project in file("modules/adaptor"))
  .dependsOn(usecase , other)

lazy val domain = (project in file("modules/domain"))
  .dependsOn(other)

lazy val usecase = (project in file("modules/usecase"))
  .dependsOn(domain , other)

lazy val other = project in file("modules/other")

scalaVersion := $name$Build.scalaV

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint")

libraryDependencies ++= $name$Build.dependency

resolvers ++= $name$Build.resolvers

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls", "-language:implicitConversions")

scalacOptions in Test ++= Seq("-Yrangepos")

scalaSource in Compile := baseDirectory.value / "app"

resourceDirectory in Compile := baseDirectory.value / "conf"
