package by.sideproject.videocaster.app.rest.routes.video.handlers

import akka.actor.Actor
import by.sideproject.videocaster.app.rest.routes.video.requests.{VideosEntityRequest, VideosGetEntitiesRequest}
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

class VideosGetEntityHandler(videoDetailsDAO: VideoItemDetailsDAO) extends Actor {

  import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case VideosEntityRequest(ctx, id, identity) => {
      log.debug(s"Processing get video request: $id")
      ctx.complete(videoDetailsDAO.findOneById(id))
    }
  }
}
