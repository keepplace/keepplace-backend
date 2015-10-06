package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import akka.event.Logging
import by.sideproject.instavideo.filestorage.base.FileStorageService
import org.slf4j.LoggerFactory
import spray.http.HttpData
import spray.http.HttpHeaders.RawHeader


class DownloadRequestHandler(binaryStorageService: FileStorageService)(implicit context: ActorContext) extends BaseService {

  val log = LoggerFactory.getLogger(this.getClass)


  def actorRefFactory = context

  def route =
    pathPrefix("data" / entityIdParameter / "download") { binaryFileId =>
      log.debug("Downloading file by id: " + binaryFileId)
      pathEnd {
        get {
          binaryStorageService.getData(binaryFileId) match {
            case Some(binaryData) => {
              respondWithMediaType(mp4) {
                respondWithHeader(RawHeader("Content-Disposition", "attachment; filename='" + binaryData.meta.name + "'")) {
                  log.debug("Sending binary information by fileId: " + binaryData.meta)
                  complete(HttpData(binaryData.data))
                }
              }
            }

          }
        }

      }

    }


}
