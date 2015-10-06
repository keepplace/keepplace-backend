package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import akka.event.Logging
import by.sideproject.instavideo.filestorage.base.FileStorageService
import org.slf4j.LoggerFactory
import spray.http.{StatusCodes, HttpData}
import spray.http.HttpHeaders.RawHeader


class DownloadRequestHandler(binaryStorageService: FileStorageService)(implicit context: ActorContext) extends BaseService {

  val log = LoggerFactory.getLogger(this.getClass)


  def actorRefFactory = context

  def route =
    pathPrefix("data" / entityIdParameter / "download") { binaryFileId =>
      log.debug("Downloading file by id: " + binaryFileId)
      pathEnd {
        get {
          binaryStorageService.getData(binaryFileId).flatMap { binaryData =>
            log.debug(s"Processing file download request for: ${binaryData.meta}")
            binaryData.meta.placement match {
              case "local" =>
                binaryData.data.map { data =>
                  respondWithMediaType(mp4) {
                    respondWithHeader(RawHeader("Content-Disposition", s"attachment; filename='${binaryData.meta.name}'")) {
                      log.debug(s"Sending binary information by fileId: ${binaryData.meta}")
                      complete(HttpData(data))
                    }
                  }
                }
              case "remote" =>
                binaryData.meta.secondaryDownloadURL.map { reditectUrl =>
                  log.debug(s"Redirecting to actual file URL: ${binaryData.meta}")
                  redirect(reditectUrl, StatusCodes.Found)
                }
            }
          }.getOrElse(reject())
        }
      }
    }


}
