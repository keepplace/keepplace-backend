package keep.place.app.rest.routes.video.handlers

import akka.actor.Actor
import keep.place.app.rest.routes.video.requests.VideosGetEntitiesRequest
import keep.place.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

class VideosGetEntitiesHandler(videoDetailsDAO: VideoItemDetailsDAO) extends Actor {

  import keep.place.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

  private implicit val executionContext: ExecutionContext = context.dispatcher
  val log = LoggerFactory.getLogger(this.getClass)


  override def receive = {
    case VideosGetEntitiesRequest(ctx, profileId) => {
      log.debug(s"Processing get all videos request")
      videoDetailsDAO.findAllByProfileId(profileId).map(ctx.complete(_))
    }
  }
}
