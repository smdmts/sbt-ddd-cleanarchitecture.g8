import sbt._
import sbtassembly.AssemblyPlugin.autoImport.MergeStrategy

import sbtassembly._

object $name$Build {

  val scalaV= "2.12.1"

  val scalazV = "7.2.10"

  val shapelessV = "2.3.2"

  val skinnyV = "2.3.6"

  val scalikejdbcV = "2.5.+"

  val monocleV = "1.4.0"

  val akkaV = "2.4.17"

  val akkaHttpV = "10.0.5"

  val circeV = "0.7.1"

  val dependency = akkaDependency ++ monocleDependency ++ scalazDependency ++ skinnyDependency ++ otherDependency

  def akkaDependency = Seq(
    "com.typesafe.akka"   %% "akka-actor"                        % akkaV,
    "com.typesafe.akka"   %% "akka-stream"                       % akkaV,
    "com.typesafe.akka"   %% "akka-slf4j"                        % akkaV,
    "com.typesafe.akka"   %% "akka-testkit"                      % akkaV % "test",
    "com.typesafe.akka"   %% "akka-http-core"                    % akkaHttpV,
    "com.typesafe.akka"   %% "akka-http"                         % akkaHttpV,
    "com.typesafe.akka"   %% "akka-http-testkit"                 % akkaHttpV,
    "com.typesafe.akka"   %% "akka-http-spray-json"              % akkaHttpV,
    "com.typesafe.akka"   %% "akka-http-jackson"                 % akkaHttpV,
    "com.typesafe.akka"   %% "akka-http-xml"                     % akkaHttpV
  )

  def monocleDependency = Seq(
    "com.github.julien-truffaut"  %%  "monocle-core"    % monocleV,
    "com.github.julien-truffaut"  %%  "monocle-generic" % monocleV,
    "com.github.julien-truffaut"  %%  "monocle-macro"   % monocleV,
    "com.github.julien-truffaut"  %%  "monocle-state"   % monocleV,
    "com.github.julien-truffaut"  %%  "monocle-refined" % monocleV
  )

  def scalazDependency = Seq(
    "org.scalaz" %% "scalaz-core"       % scalazV,
    "org.scalaz" %% "scalaz-concurrent" % scalazV
  )

  def skinnyDependency = Seq(
    "org.skinny-framework"   %% "skinny-orm"            % skinnyV,
    "org.skinny-framework"   %% "skinny-validator"      % skinnyV,
    "org.scalikejdbc" %% "scalikejdbc"                  % scalikejdbcV,
    "org.scalikejdbc" %% "scalikejdbc-config"           % scalikejdbcV,
    "com.zaxxer" % "HikariCP"                           % "2.4.5",
    "org.scalikejdbc"   %% "scalikejdbc-test"           % scalikejdbcV   % "test",
    "org.skinny-framework" %% "skinny-factory-girl"     % skinnyV        % "test"
  )

  def otherDependency = Seq(
    "io.circe" %% "circe-core"    % circeV,
    "io.circe" %% "circe-generic" % circeV,
    "io.circe" %% "circe-parser"  % circeV,
    "org.scaldi" %% "scaldi" % "0.5.8",
    "mysql" % "mysql-connector-java" % "5.1.31",
    "org.postgresql" %  "postgresql" % "9.4-1204-jdbc41",
    "com.iheart" %% "ficus" % "1.4.0",
    "com.github.seratch"   %% "awscala" % "0.5.+",
    "com.github.cb372" %% "scalacache-guava" % "0.9.3",
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.amazonaws.scala" % "aws-scala-sdk-kinesis" % "1.10.7",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
  )

  val resolvers = Seq(
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" ,
    "Atlassian Releases" at "https://maven.atlassian.com/public/",
    Resolver.jcenterRepo,
    Resolver.bintrayRepo("pathikrit", "maven")
  )

}
