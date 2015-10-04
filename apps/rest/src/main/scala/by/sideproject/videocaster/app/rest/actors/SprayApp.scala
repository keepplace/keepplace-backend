package by.sideproject.videocaster.app.rest.actors

import akka.actor.Actor
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.oauth.base.SessionStore
import by.sideproject.videocaster.app.rest.routes.{LoginHandler, DownloadRequestHandler, RssRequestHandler, VideoRequestHandler}
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.routing.HttpService

class SprayApp(
                storageService: StorageService,
                downloadService: DownloadService,
                binaryStorageService: FileStorageService,
                domain: String,
                sessionStore: SessionStore)
  extends Actor
  with HttpService {

  def actorRefFactory = context

  def receive = runRoute(
    new RssRequestHandler(storageService, domain,sessionStore).route
    ~ new VideoRequestHandler(storageService, downloadService, binaryStorageService,sessionStore).route
    ~ new DownloadRequestHandler(binaryStorageService).route
    ~ new LoginHandler(storageService, domain, sessionStore).route
  )

}
