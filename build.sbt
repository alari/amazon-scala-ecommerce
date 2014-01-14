organization := "gipsetter"

name := """amazon-api"""

version := "1.0"

scalaVersion := "2.10.3"

sbtVersion := "0.13.1"

libraryDependencies ++= {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.0.13",
    "joda-time" % "joda-time" % "2.3",
    "org.joda" % "joda-convert" % "1.2",
    "commons-codec" % "commons-codec" % "1.9",
    "org.specs2" %% "specs2" % "2.2.3" % "test"
  )
}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

publishTo := {
  val artifactory = "http://mvn.quonb.org/artifactory/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("Artifactory Realm" at artifactory + "plugins-snapshot-local/")
  else
    Some("Artifactory Realm" at artifactory + "plugins-release-local/")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

testOptions in Test += Tests.Argument("junitxml")