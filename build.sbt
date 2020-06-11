name := "cats-playground"

version := "0.1"

scalaVersion := "2.13.2"


val catsVersion = "2.0.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-laws" % catsVersion,
  "joda-time" % "joda-time" % "2.10.6"
)