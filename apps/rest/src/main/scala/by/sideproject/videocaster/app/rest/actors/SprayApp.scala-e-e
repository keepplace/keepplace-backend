package by.sideproject.videocaster.app.rest.actors

import akka.actor.Actor
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.routes.{DownloadRequestHandler, RssRequestHandler, VideoRequestHandler}
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.routing.HttpService

class SprayApp(
                storageService: StorageService,
                downloadService: DownloadService,
                binaryStorageService: FileStorageService,
                domain: String)
  extends Actor
  with HttpService {

  def actorRefFactory = context

  def receive = runRoute(
    new RssRequestHandler(storageService, domain).route
    ~ new VideoRequestHandler(storageService, downloadService, binaryStorageService).route
    ~ new DownloadRequestHandler(binaryStorageService).route
  )

}
