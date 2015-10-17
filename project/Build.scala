import com.typesafe.sbt.packager.archetypes.JavaServerAppPackaging
import sbt.Keys._
import sbt._

object VideoPodcastDownloader extends Build {

  import App.BuildSettings._
  import App.Dependencies._
  import com.typesafe.sbt.SbtNativePackager._
  import com.typesafe.sbt.packager.Keys._


  lazy val root = baseProject("video-podcast-downloader-rest", "apps/rest")
    .aggregate(storageService, models, youtubeDlWrapper, downloader, downloaderSynch, downloaderAsynch, dropboxFileStorageService, fileStorageService, h2StorageService)
    .dependsOn(storageService, models, youtubeDlWrapper, downloader, downloaderSynch, downloaderAsynch, dropboxFileStorageService, fileStorageService, h2StorageService)
    .settings(libraryDependencies ++= Seq(config, scalazCore) ++ spray ++ metrics ++ logs ++ akka ++ Seq(dropbox) ++ oauth,
      maintainer in Linux := "Denis Karpenko <denis@karpenko.me>",
      packageSummary in Linux := "keep.place application",
      packageDescription := "keep.place application"
    ).enablePlugins(JavaServerAppPackaging)

  lazy val storageService = baseProject("storage-base", "services/storage/base") dependsOn (models)
    .settings(libraryDependencies ++= scalaz)

  lazy val h2StorageService = baseProject("storage-h2", "services/storage/h2")
    .dependsOn(storageService, models)
    .settings(libraryDependencies ++= h2)

  lazy val fileStorageService = baseProject("file-storage-base", "services/filestorage/base") dependsOn (models)
    .settings(libraryDependencies ++= scalaz)

  lazy val localFileStorageService = baseProject("file-storage-local", "services/filestorage/local")
    .dependsOn(models, fileStorageService, storageService)
    .settings(libraryDependencies ++= scalaz)

  lazy val dropboxFileStorageService = baseProject("file-storage-dropbox", "services/filestorage/dropbox")
    .dependsOn(models, fileStorageService, localFileStorageService, storageService)
    .settings(libraryDependencies ++= Seq(dropbox) ++ akka ++ scalaz)

  lazy val models = baseProject("model", "model")

  lazy val youtubeDlWrapper = baseProject("youtube-dl-wrapper", "services/youtube-dl-wrapper")
    .settings(libraryDependencies ++= Seq(json) ++ scalaz)
    .dependsOn(models)

  lazy val downloader = baseProject("downloader", "services/downloader/base")
    .dependsOn(models, youtubeDlWrapper, fileStorageService, storageService)
    .settings(libraryDependencies ++= scalaz)

  lazy val downloaderSynch = baseProject("downloader-synchronous", "services/downloader/synchronous")
    .dependsOn(models, youtubeDlWrapper, fileStorageService, storageService, downloader)
    .settings(libraryDependencies ++= scalaz)

  lazy val downloaderAsynch = baseProject("downloader-asynchronous", "services/downloader/asynch")
    .dependsOn(models, youtubeDlWrapper, fileStorageService, storageService, downloader, downloaderSynch)
    .settings(libraryDependencies ++= logs ++ akka ++ scalaz)


  def baseProject(name: String, filePath: String) = Project(name, file(filePath)).settings(moduleSettings: _*)
}
