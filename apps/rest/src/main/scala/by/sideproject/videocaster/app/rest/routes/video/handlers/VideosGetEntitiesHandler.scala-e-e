package by.sideproject.videocaster.app.rest.routes.video.handlers

import akka.actor.Actor
import by.sideproject.videocaster.app.rest.routes.video.requests.VideosGetEntitiesRequest
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

class VideosGetEntitiesHandler(videoDetailsDAO: VideoItemDetailsDAO) extends Actor {

  import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  private implicit val executionContext: ExecutionContext = context.dispatcher
  val log = LoggerFactory.getLogger(this.getClass)


  override def receive = {
    case VideosGetEntitiesRequest(ctx) => {
      log.debug(s"Processing get all videos request")
      ctx.complete(videoDetailsDAO.findAll())
    }
  }
}
