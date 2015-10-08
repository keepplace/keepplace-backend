package by.sideproject.videocaster.app.rest.routes.video.handlers

import akka.actor.Actor
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.routes.video.requests.{VideosEntityRequest, VideosGetEntitiesRequest}
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory
import spray.http.StatusCodes

class VideosDeleteEntityHandler(videoDetailsDAO: VideoItemDetailsDAO, binaryStorageService: FileStorageService) extends Actor {

  import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case VideosEntityRequest(ctx, id, identity) => {
      log.debug(s"Processing delete video request: $id")

      val videoItemForRemoval: Option[VideoItemDetails] = videoDetailsDAO.findOneById(id)
      videoItemForRemoval.flatMap {
        videoDetailsDAO.removeById(id)
        _.fileMetaId
      }.map(meta => binaryStorageService.remove(meta, identity))

      ctx.complete(videoItemForRemoval)
    }
  }
}
