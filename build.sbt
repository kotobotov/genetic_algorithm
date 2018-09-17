name := "geneticAlgorithm"

version := "1.0"

scalaVersion := "2.12.6"
triggeredMessage in ThisBuild := Watched.clearWhenTriggered
cancelable in Global := true
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.+" % "test"
import sbt.errorssummary.Plugin.autoImport._

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.8.2"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false
scapegoatVersion in ThisBuild := "1.3.8"
