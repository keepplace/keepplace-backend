package keep.place.app.rest.actors

import akka.actor.Actor
import akka.util.Timeout
import keep.place.filestorage.base.FileStorageService
import keep.place.app.rest.oauth.base.utils.OauthConfig
import keep.place.app.rest.rejections.LoginRedirectionRejection
import keep.place.app.rest.routes.download.DownloadAPI
import keep.place.app.rest.routes.video.VideoAPI
import keep.place.app.rest.routes.{LoginHandler, RssRequestHandler}
import keep.place.services.downloader.base.DownloadService
import keep.place.services.storage.base.StorageService
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
