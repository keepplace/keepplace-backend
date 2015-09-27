import sbt.Keys._
import sbt._

object VideoPodcastDownloader extends Build {

  import App.BuildSettings._
  import App.Dependencies._

  lazy val root = baseProject("video-podcast-downloader-rest", "apps/rest")
    .aggregate(storageService, inMemoryStorageService, models, youtubeDlWrapper, downloader, downloaderSynch, downloaderAsynch, localFileStorageService, fileStorageService)
    .dependsOn(storageService, inMemoryStorageService, models, youtubeDlWrapper, downloader, downloaderSynch, downloaderAsynch, localFileStorageService, fileStorageService)
    .settings(libraryDependencies ++= Seq(config, scalazCore, playJson) ++ spray ++ metrics ++ logs ++ akka)


  lazy val storageService = baseProject("storage-base", "services/storage/base") dependsOn (models)
  lazy val inMemoryStorageService = baseProject("storage-in-memory", "services/storage/in-memory") dependsOn(storageService, models)

  lazy val fileStorageService = baseProject("file-storage-base", "services/filestorage/base") dependsOn (models)

  lazy val localFileStorageService = baseProject("file-storage-local", "services/filestorage/local")
    .dependsOn(models, fileStorageService, storageService)

  lazy val models = baseProject("model", "model")

  lazy val youtubeDlWrapper = baseProject("youtube-dl-wrapper", "services/youtube-dl-wrapper")
    .settings(libraryDependencies ++= Seq(json))
    .dependsOn(models)

  lazy val downloader = baseProject("downloader", "services/downloader/base")
    .dependsOn(models, youtubeDlWrapper, fileStorageService, storageService)

  lazy val downloaderSynch = baseProject("downloader-synchronous", "services/downloader/synchronous")
    .dependsOn(models, youtubeDlWrapper, fileStorageService, storageService, downloader)

  lazy val downloaderAsynch = baseProject("downloader-asynchronous", "services/downloader/asynch")
    .dependsOn(models, youtubeDlWrapper, fileStorageService, storageService, downloader, downloaderSynch)
    .settings(libraryDependencies ++= logs ++ akka)
  //  lazy val dropboxFileStorageService = baseProject("file-storage-dropbox", "services/filestorage/dropbox").dependsOn(models,baseFileStorageService)
  //    .settings(libraryDependencies ++= Seq(dropbox))


  def baseProject(name: String, filePath: String) = Project(name, file(filePath)).settings(moduleSettings: _*)


}
