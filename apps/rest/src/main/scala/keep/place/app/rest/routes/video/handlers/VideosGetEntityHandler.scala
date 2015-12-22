package keep.place.app.rest.routes.video.handlers

import akka.actor.Actor
import keep.place.app.rest.routes.base.requests.EntityRequest
import keep.place.services.storage.base.dao.{VideoItemDtoDAO, VideoItemDetailsDAO}
import org.slf4j.LoggerFactory
import spray.http.{HttpResponse, StatusCodes}
import spray.httpx.marshalling.ToResponseMarshaller
import spray.routing.AuthorizationFailedRejection

import scala.concurrent.ExecutionContext
import scala.util.Success

class VideosGetEntityHandler(videoDetailsDAO: VideoItemDtoDAO)
                            (implicit executionContext: ExecutionContext) extends Actor {

  import keep.place.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}


  implicit def statusCodeToResponseMarshaller = ToResponseMarshaller[spray.http.StatusCode] { (value, ctx) =>
      ctx.marshalTo(HttpResponse(value))
  }

  val log = LoggerFactory.getLogger(this.getClass)

  override def receive = {
    case EntityRequest(ctx, id, identity) => {
      log.debug(s"Processing get video request: $id")
      videoDetailsDAO.findOneById(identity.profileId, id).onComplete {
        case Success(Some(item)) => ctx.complete(item)
        case _ => ctx.complete(StatusCodes.NotFound)
      }
    }
  }
}
