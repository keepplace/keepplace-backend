package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.video.AddVideoRequest


class VideoService(implicit context: ActorContext) extends BaseService {

  def actorRefFactory = context

  def route =
    pathPrefix("videos") {
      import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
      import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

      pathEnd {
        get {
          respondWithMediaType(json) {
            complete {
              Seq(
                VideoItemDetails("1", "video1", "video1", None, "http://url.com", "2015.09.20"),
                VideoItemDetails("2", "video2", "video2", Some("http://url.com/video.mpg"), "http://url.com", "2015.09.20")
              )
            }
          }
        } ~ post {
          entity(as[AddVideoRequest]) { addVideoRequest =>
            respondWithMediaType(json) {
              complete {
                VideoItemDetails("123", "video1", "video1", None, addVideoRequest.baseUrl, "2015.09.20")
              }
            }
          }
        }
      } ~
        path(entityIdParameter) { videoId =>
          get {
            respondWithMediaType(json) {
              complete {
                VideoItemDetails(videoId, "video1", "video1", None, "http://url.com", "2015.09.20")
              }
            }
          }

        }

    }


}
