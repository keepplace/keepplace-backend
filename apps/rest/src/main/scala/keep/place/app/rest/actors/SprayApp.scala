package keep.place.app.rest.actors

import akka.actor.Actor
import akka.util.Timeout
import keep.place.app.rest.oauth.base.utils.OauthConfig
import keep.place.app.rest.rejections.LoginRedirectionRejection
import keep.place.app.rest.routes.video.VideoAPI
import keep.place.app.rest.routes.{LoginHandler, RssAPI}
import keep.place.filestorage.base.FileStorageService
import keep.place.services.downloader.base.DownloadService
import keep.place.services.storage.base.StorageService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes._
import spray.routing.{HttpService, RejectionHandler}

class SprayApp(
                storageService: StorageService,
                downloadService: DownloadService,
                binaryStorageService: FileStorageService,
                domain: String)(implicit timeout: Timeout)
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

    pathPrefix("api") {
      new LoginHandler(storageService).route ~
        new VideoAPI(storageService, downloadService, binaryStorageService).route ~
        new RssAPI(storageService, domain).route
    }

  )

}
