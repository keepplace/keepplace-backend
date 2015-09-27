package by.sideproject.videocaster.app.rest

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.instavideo.filestorage.local.LocalFileStorageService
import by.sideproject.videocaster.app.rest.actors.SprayApp
import by.sideproject.videocaster.services.downloader.async.{DownloadActor, AsynchronousDownloadService}
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.downloader.sync.SynchronusDownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.inmemory.InmemoryStorageService
import by.sideproject.videocaster.services.youtubedl.YoutubeDL
import spray.can.Http

import scala.concurrent.duration._


class ApplicationKernel extends akka.kernel.Bootable {

  implicit val actorSystem = ActorSystem("instacaster")
  implicit val metaStorageService: StorageService = InmemoryStorageService
  implicit val binaryStorageService: FileStorageService = new LocalFileStorageService(metaStorageService.fileMetaDAO)

  implicit val youtubeDl = new YoutubeDL

  implicit val synchDownloadService : DownloadService = new SynchronusDownloadService(youtubeDl, metaStorageService, binaryStorageService)
  implicit val downloadActor : ActorRef = actorSystem.actorOf(Props(new DownloadActor(synchDownloadService)))
  implicit val downloadService : DownloadService = new AsynchronousDownloadService(downloadActor)

  implicit val timeout = Timeout(5.seconds)


  override def startup(): Unit = {
    val service = actorSystem.actorOf(Props(new SprayApp(metaStorageService, downloadService, binaryStorageService)))
    IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
  }

  override def shutdown(): Unit = {
    /*nothing to do*/
  }
}

object Boot extends App {
  private val kernel = new ApplicationKernel
  kernel.startup()
}
