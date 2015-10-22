import App.Dependencies
import sbt.Keys._
import sbt._

trait AppBuildSettings {
  import App.Resolvers._

  Keys.fork in Test := false
  val buildOrganization = "by.sideproject.videcaster"
  val buildScalaVersion = "2.11.7"
  val jdkVersion = settingKey[String]("")

  val buildSettings = Seq(
    organization := buildOrganization,
    jdkVersion := "1.8",
    javacOptions ++=
      Seq("-source", jdkVersion.value, "-target", jdkVersion.value),
    javaOptions += "-Xmx256M",
    javaOptions += "-XX:MaxMetaspaceSize=128M",
    scalaVersion := buildScalaVersion ,
    crossPaths := false,
    crossScalaVersions := Seq(buildScalaVersion),
    parallelExecution in Test := false,
    checksums in update := Nil,
    resolvers := depResolvers,
    allDependencies ~= Dependencies.logging,
    allDependencies ~= Dependencies.tests
  )

  val moduleSettings = buildSettings

}
