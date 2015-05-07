organization := "com.carryx"

name := """amazon-api"""

version := "1.4-SNAPSHOT"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.10.4", "2.11.6")

sbtVersion := "0.13.8"

libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    "joda-time" % "joda-time" % "2.3",
    "org.joda" % "joda-convert" % "1.2",
    "commons-codec" % "commons-codec" % "1.9",
    "org.specs2" %% "specs2" % "2.3.12" % "test"
  )
}

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.2")
    case _ =>
      Seq()
  }
}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

publishTo := Some(Resolver.file("file",  new File( "/mvn-repo" )) )

testOptions in Test += Tests.Argument("junitxml")
