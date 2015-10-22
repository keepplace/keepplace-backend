
import sbt.{ExclusionRule, ModuleID, _}

trait AppDependencies {

  import App.BuildSettings._
  import App.Versions._

  val json = "org.json" % "json" % "20141113"
  val dropbox = "com.dropbox.core" % "dropbox-core-sdk" % dropboxVer


  val scalaReflect = "org.scala-lang" % "scala-reflect" % buildScalaVersion
  val scalaParserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"


  val scalazCore = "org.scalaz" %% "scalaz-core" % scalazVersion
  val scalazConcurrent = "org.scalaz" %% "scalaz-concurrent" % scalazVersion
  val scalazEffect = "org.scalaz" %% "scalaz-effect" % scalazVersion
  val scalazIteratee = "org.scalaz" %% "scalaz-iteratee" % scalazVersion
  val scalaz = Seq(scalazCore, scalazConcurrent, scalazEffect, scalazIteratee)

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVer
  val akkaKernel = "com.typesafe.akka" %% "akka-kernel" % akkaVer
  val akka = Seq(
    akkaActor,
    akkaKernel,
    "com.typesafe.akka" %% "akka-remote" % akkaVer,
    "com.typesafe.akka" %% "akka-testkit" % akkaVer
  )

  val config = "com.typesafe" % "config" % "1.3.0"


  val h2 = Seq(
    "org.liquibase" % "liquibase-core" % liquibaseVer,
    "com.h2database" % "h2" % h2Ver,
    "com.typesafe.slick" %% "slick" % "3.1.0",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.1.0"
  )

  val logs = Seq(
    "org.slf4j" % "slf4j-log4j12" % sl4jVer,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVer
  )

  val nscalaTime = "com.github.nscala-time" %% "nscala-time" % "1.8.0"

  val oauth = Seq("com.google.oauth-client" % "google-oauth-client" % oauthVer,
    "com.google.oauth-client" % "google-oauth-client-java6" % oauthVer,
    "com.google.http-client" % "google-http-client-jackson2" % oauthVer)

  val sprayRouting = "io.spray" %% "spray-routing-shapeless2" % sprayVer
  val sprayCan = "io.spray" %% "spray-can" % sprayVer
  val spray = Seq(
    sprayCan,
    sprayRouting,
    "io.spray" %% "spray-json" % sprayJsonVer

  )
  val sprayClient = "io.spray" %% "spray-client" % sprayVer

  val sprayTestKit = "io.spray" %% "spray-testkit" % sprayVer excludeAll (ExclusionRule(organization = "org.specs2"))

  val specs2TestsRaw = Seq(
    "org.specs2" %% "specs2-core" % specs2Ver,
    "org.specs2" %% "specs2-scalacheck" % specs2Ver,
    "org.specs2" %% "specs2-matcher" % specs2Ver,
    "org.specs2" %% "specs2-mock" % specs2Ver
  )
  private val specs2Tests = specs2TestsRaw.map(_ % "test")

  val metrics = Seq(
    "com.yammer.metrics" % "metrics-core" % metricsVer
  )

  def logging(allDependencies: Seq[ModuleID]): Seq[ModuleID] = {
    Seq(
      "org.slf4j" % "slf4j-api" % "1.7.7"
      , "org.slf4j" % "jul-to-slf4j" % "1.7.7"
      , "ch.qos.logback" % "logback-classic" % logbackVer % Runtime exclude("org.slf4j", "slf4j-api")
      , "ch.qos.logback" % "logback-core" % logbackVer % Runtime exclude("org.slf4j", "slf4j-api")
      , "net.logstash.logback" % "logstash-logback-encoder" % "4.0"
      , "org.slf4j" % "jcl-over-slf4j" % "1.7.7" % Runtime
      , "org.slf4j" % "log4j-over-slf4j" % "1.7.7" % Runtime
    ) ++
      allDependencies.map(
        _.exclude("commons-logging", "commons-logging")
          .exclude("log4j", "log4j")
          .exclude("org.slf4j", "slf4j-log4j12")
          .exclude("org.slf4j", "slf4j-jcl")
          .exclude("org.slf4j", "slf4j-jdk14")
          .excludeAll(ExclusionRule("org.slf4j", "jcl-over-slf4j", configurations = Compile.name :: Nil))
          .excludeAll(ExclusionRule("ch.qos.logback", "logback-classic", configurations = Compile.name :: Nil))
          .excludeAll(ExclusionRule("ch.qos.logback", "logback-core", configurations = Compile.name :: Nil))
      )
  }

  def tests(allDependencies: Seq[ModuleID]): Seq[ModuleID] =
    specs2Tests ++ allDependencies

}
