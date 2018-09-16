name := "geneticAlgorithm"

version := "1.0"

scalaVersion := "2.12.6"
triggeredMessage in ThisBuild := Watched.clearWhenTriggered
cancelable in Global := true
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.+" % "test"