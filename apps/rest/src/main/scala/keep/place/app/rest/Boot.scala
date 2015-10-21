package keep.place.app.rest

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import keep.place.filestorage.base.FileStorageService
import keep.place.filestorage.dropbox.DropboxFileStorageService
import keep.place.app.rest.actors.SprayApp
import keep.place.services.downloader.async.{AsynchronousDownloadService, DownloadActor}
import keep.place.services.downloader.base.DownloadService
import keep.place.services.downloader.sync.SynchronusDownloadService
import keep.place.services.storage.base.StorageService
import keep.place.services.storage.h2.H2StorageService
import keep.place.services.youtubedl.YoutubeDL
import spray.can.Http

import scala.concurrent.duration._


class ApplicationKernel extends akka.kernel.Bootable {

  import keep.place.app.rest.config.Config._
  implicit val actorSystem = ActorSystem("instacaster")

  implicit lazy val executionContext = actorSystem.dispatcher
  implicit val metaStorageService: StorageService = new H2StorageService

  implicit val binaryStorageService: FileStorageService = new DropboxFileStorageService(metaStorageService.fileMetaDAO, domain)

  implicit val youtubeDl = new YoutubeDL

  implicit val synchDownloadService : DownloadService = new SynchronusDownloadService(youtubeDl, metaStorageService, binaryStorageService)
  implicit val downloadActor : ActorRef = actorSystem.actorOf(Props(new DownloadActor(synchDownloadService)).withDispatcher("binary-download-dispatcher"))
  implicit val downloadService : DownloadService = new AsynchronousDownloadService(downloadActor, synchDownloadService)


  implicit lazy val timeout = Timeout(60.seconds)

  override def startup(): Unit = {
    val service = actorSystem.actorOf(Props(new SprayApp(metaStorageService, downloadService, binaryStorageService, domain)))
    IO(Http) ? Http.Bind(service, interface = bindInterface, port = bindPort)
  }

  override def shutdown(): Unit = {
    metaStorageService.shoutdown
  }
}

object Boot extends App {
  private val kernel = new ApplicationKernel
  kernel.startup()


}
