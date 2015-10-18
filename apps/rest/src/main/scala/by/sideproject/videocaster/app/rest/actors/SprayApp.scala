package by.sideproject.videocaster.app.rest.actors

import akka.actor.Actor
import akka.util.Timeout
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import by.sideproject.videocaster.app.rest.rejections.LoginRedirectionRejection
import by.sideproject.videocaster.app.rest.routes.download.DownloadAPI
import by.sideproject.videocaster.app.rest.routes.video.VideoAPI
import by.sideproject.videocaster.app.rest.routes.{LoginHandler, RssRequestHandler}
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes._
import spray.routing.{RejectionHandler, HttpService}

class SprayApp(
                storageService: StorageService,
                downloadService: DownloadService,
                binaryStorageService: FileStorageService,
                domain: String)(implicit timeout : Timeout)
  extends Actor
  with HttpService {

  def actorRefFactory = context

  protected val log = LoggerFactory.getLogger(this.getClass)

  implicit val loginRedirectionRejectionHandler = RejectionHandler {
    case LoginRedirectionRejection(resolution) :: _ => {
      log.debug(s"Redirecting to login page with resolution: $resolution")
      redirect(OauthConfig.REDIRECT_ROUTE, Found)
    }
  }


  def receive = runRoute(
    new RssRequestHandler(storageService, domain).route
    ~ new VideoAPI(storageService, downloadService, binaryStorageService).route
    ~ new DownloadAPI(binaryStorageService, storageService.fileMetaDAO).route
    ~ new LoginHandler(storageService).route
  )

}
