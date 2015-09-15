import sbt._

object VideoPodcastDownloader extends Build {

  import App.BuildSettings._

  lazy val root = baseProject("video-podcast-downloader-cmd", "apps/cmd") aggregate(storageService, models)

  lazy val storageService = baseProject("storage", "services/storage") dependsOn (models)

  lazy val models = baseProject("model", "model")

  def baseProject(name: String, filePath: String) = Project(name, file(filePath)).settings(moduleSettings: _*)


}
