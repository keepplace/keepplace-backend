package keep.place.app.rest.routes.video.handlers

import akka.actor.Actor
import keep.place.filestorage.base.FileStorageService
import keep.place.app.rest.routes.base.requests.EntityRequest
import keep.place.services.storage.base.dao.{FileMetaDAO, VideoItemDetailsDAO}
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.routing.AuthorizationFailedRejection

import scala.concurrent.ExecutionContext

class VideosDeleteEntityHandler(videoDetailsDAO: VideoItemDetailsDAO, fileMetaDAO: FileMetaDAO, binaryStorageService: FileStorageService)
                               (implicit executionContext: ExecutionContext) extends Actor {

  import keep.place.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case EntityRequest(ctx, id, identity) => {
      log.debug(s"Processing delete video request: $id")
      for {
        videoItemForRemoval <- videoDetailsDAO.findOneById(id)
      } yield {
        videoItemForRemoval match {
          case Some(item) => {
            if (item.profileId == identity.profileId) {
              videoDetailsDAO.removeById(id)
              videoDetailsDAO.removeById(id)
              item.fileMetaId.map { fileMetaId =>
                binaryStorageService.remove(fileMetaId, identity)
                fileMetaDAO.removeById(fileMetaId)
              }
              ctx.complete(item)
            } else {
              ctx.reject(AuthorizationFailedRejection)
            }
          }
          case None => ctx.complete(StatusCodes.NotFound)
        }
      }
    }
  }
}
