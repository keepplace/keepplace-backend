import sbt.Keys._
import sbt._

object VideoPodcastDownloader extends Build {

  import App.BuildSettings._
  import App.Dependencies._

  lazy val root = baseProject("video-podcast-downloader-rest", "apps/rest")
    .aggregate(baseStorageService, inMemoryStorageService, models, youtubeDlWrapper)
    .dependsOn(baseStorageService, inMemoryStorageService, models, youtubeDlWrapper)
    .settings(libraryDependencies ++= Seq(config, scalazCore, playJson) ++ spray ++ metrics ++ logs ++ akka)


  lazy val baseStorageService = baseProject("storage-base", "services/storage/base") dependsOn (models)
  lazy val inMemoryStorageService = baseProject("storage-in-memory", "services/storage/in-memory") dependsOn (baseStorageService, models)

  lazy val models = baseProject("model", "model")

  lazy val youtubeDlWrapper = baseProject("youtube-dl-wrapper", "services/youtube-dl-wrapper")
    .settings(libraryDependencies ++= Seq(json))

  def baseProject(name: String, filePath: String) = Project(name, file(filePath)).settings(moduleSettings: _*)


}
