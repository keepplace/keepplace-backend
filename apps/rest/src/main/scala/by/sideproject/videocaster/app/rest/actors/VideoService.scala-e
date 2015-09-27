package by.sideproject.videocaster.app.rest.actors

import akka.actor.Actor
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.routes.{DownloadsService, RssService, VideoService}
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.routing.HttpService

class AppActor(storageService: StorageService, downloadService: DownloadService, binaryStorageService: FileStorageService) extends Actor with HttpService {

  def actorRefFactory = context

  def receive = runRoute(new RssService(storageService).route
    ~ new VideoService(storageService, downloadService, binaryStorageService).route
    ~ new DownloadsService(binaryStorageService).route
  )

}
