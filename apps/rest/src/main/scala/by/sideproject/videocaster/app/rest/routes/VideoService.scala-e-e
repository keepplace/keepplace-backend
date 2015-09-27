package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import akka.event.Logging
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.video.AddVideoRequest
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import org.slf4j.LoggerFactory

import scala.util.Random


class VideoService(storageService: StorageService, downloadService: DownloadService, binaryStorageService: FileStorageService)(implicit context: ActorContext) extends BaseService {

  protected val log = LoggerFactory.getLogger(this.getClass)

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

            log.debug("Video download request: " + AddVideoRequest)

            respondWithMediaType(json) {
              complete {

                val newId: Some[String] = Some(Math.abs(idGenerator.nextLong()).toString)
                val videoItemDetails: VideoItemDetails = VideoItemDetails(newId, "video1", "video1", None, addVideoRequest.baseUrl, "2015.09.20", "added")

                log.debug("Saving newly created video item: " + videoItemDetails)
                storageService.videoItemDetailsDAO.insert(
                  videoItemDetails
                ).map { id =>

                  val addedVideoItemEntry = storageService.videoItemDetailsDAO.findOneById(id)

                  log.debug("Initiating download of video file for: " + videoItemDetails)
                  addedVideoItemEntry.map(videoItem => downloadService.download(videoItem))


                  addedVideoItemEntry

                }
              }
            }
          }
        }
      } ~
        pathPrefix(entityIdParameter) { videoId =>

          pathEnd {
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


}
