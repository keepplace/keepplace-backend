package keep.place.app.rest.routes.download

import akka.actor.{Props, ActorContext}
import keep.place.filestorage.base.FileStorageService
import keep.place.app.rest.routes.base.BaseAPI
import keep.place.app.rest.routes.base.requests.EntityRequest
import keep.place.app.rest.routes.download.handlers.DownloadGetFileHandler
import keep.place.app.rest.routes.download.requests.DownloadGetFileRequest
import keep.place.app.rest.routes.video.handlers.VideosGetEntitiesHandler
import keep.place.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory
import spray.http.HttpHeaders.RawHeader
import spray.http.{HttpData, StatusCodes}

import scala.concurrent.ExecutionContext


class DownloadAPI(binaryStorageService: FileStorageService, fileMetaDAO: FileMetaDAO)(implicit context: ActorContext) extends BaseAPI {

  val log = LoggerFactory.getLogger(this.getClass)

  private implicit val executionContext = context.dispatcher
  private val filesDownloadHandler = context.actorOf(Props(new DownloadGetFileHandler(binaryStorageService, fileMetaDAO)))


  def actorRefFactory = context

  def route =
    pathPrefix("data" / Segment / "download") { downloadId =>
      log.debug("Downloading file by id: " + downloadId)
      pathEnd {
        get {
          ctx => filesDownloadHandler ! DownloadGetFileRequest(ctx,downloadId)
        }
      }
    }


}
