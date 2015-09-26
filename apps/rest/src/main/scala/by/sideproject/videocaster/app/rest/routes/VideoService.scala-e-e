package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.video.AddVideoRequest
import by.sideproject.videocaster.services.storage.base.StorageService

import scala.util.Random


class VideoService(storageService: StorageService)(implicit context: ActorContext) extends BaseService {

  def actorRefFactory = context

  val idGenerator = new Random()


  def route =
    pathPrefix("videos") {
      import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
      import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

      pathEnd {
        get {
          respondWithMediaType(json) {
            complete {
              storageService.videoItemDetailsDAO.findAll()
            }
          }
        } ~ post {
          entity(as[AddVideoRequest]) { addVideoRequest =>
            respondWithMediaType(json) {
              complete {
                storageService.videoItemDetailsDAO.insert(
                  VideoItemDetails(Some(idGenerator.nextLong().toString), "video1", "video1", None, addVideoRequest.baseUrl, "2015.09.20")
                ).map{
                  storageService.videoItemDetailsDAO.findOneById(_)
                }

              }
            }
          }
        }
      } ~
        path(entityIdParameter) { videoId =>
          get {
            respondWithMediaType(json) {
              complete {
                storageService.videoItemDetailsDAO.findOneById(videoId)
              }
            }
          }

        }

    }


}
