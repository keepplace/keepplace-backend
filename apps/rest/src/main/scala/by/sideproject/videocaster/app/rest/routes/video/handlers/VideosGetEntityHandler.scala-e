package by.sideproject.videocaster.app.rest.routes.video.handlers

import akka.actor.Actor
import by.sideproject.videocaster.app.rest.routes.base.requests.EntityRequest
import by.sideproject.videocaster.app.rest.routes.video.requests.VideosGetEntitiesRequest
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

class VideosGetEntityHandler(videoDetailsDAO: VideoItemDetailsDAO)
                            (implicit executionContext: ExecutionContext) extends Actor {

  import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case EntityRequest(ctx, id, identity) => {
      log.debug(s"Processing get video request: $id")
      videoDetailsDAO.findOneById(id).map(ctx.complete(_))
    }
  }
}
