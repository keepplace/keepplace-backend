import sbt._

trait AppResolvers {
  val typesafeReleaseRepo = "Typesafe Release Repository" at "http://repo.typesafe.com/typesafe/releases/"

  val scalaToolsRepo = "Scala Tools Repository" at "https://oss.sonatype.org/content/groups/scala-tools/"

  val sprayDevRepo = "Spray Dev Repository" at "http://nightlies.spray.io/"

  val sprayRepo = "Spray Repository" at "http://repo.spray.io/"

  val scalazStreamReleases = "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

  val depResolvers = Seq(typesafeReleaseRepo, scalaToolsRepo, sprayDevRepo, sprayRepo, scalazStreamReleases, Resolver.mavenLocal)

}
