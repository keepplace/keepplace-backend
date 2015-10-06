package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.oauth.dropbox.DropboxAuthService
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.video.AddVideoRequest
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory
import spray.http.StatusCodes

import scala.util.Random


class VideoRequestHandler(storageService: StorageService,
                          downloadService: DownloadService,
                          binaryStorageService: FileStorageService)(implicit context: ActorContext)
  extends DropboxAuthService(storageService.identityDAO)
  with BaseService {

  protected val log = LoggerFactory.getLogger(this.getClass)

  val idGenerator = new Random()

  val videoDetailsDAO: VideoItemDetailsDAO = storageService.videoItemDetailsDAO

  def route =
    pathPrefix("videos") {
      import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
      import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

      respondWithMediaType(json) {
        authenticate(cookieAuth) { user =>
          pathEnd {
            get {
              complete(videoDetailsDAO.findAll())
            } ~ post {
              entity(as[AddVideoRequest]) { addVideoRequest =>

                log.debug("Video download request: " + AddVideoRequest)


                complete {

                  val newId: Some[String] = Some(Math.abs(idGenerator.nextLong()).toString)

                  val videoItemDetails: VideoItemDetails = downloadService.getVideoDetails(addVideoRequest.baseUrl).copy(id = newId)


                  log.debug("Saving newly created video item: " + videoItemDetails)
                  videoDetailsDAO.insert(
                    videoItemDetails
                  ).map { id =>

                    val addedVideoItemEntry = videoDetailsDAO.findOneById(id)

                    log.debug("Initiating download of video file for: " + videoItemDetails)
                    addedVideoItemEntry.map(videoItem => downloadService.download(videoItem, user))

                    addedVideoItemEntry

                  }
                }
              }
            }
          } ~
            pathPrefix(entityIdParameter) { videoId =>

              pathEnd {
                get {
                  complete(videoDetailsDAO.findOneById(videoId))
                } ~ delete {

                  videoDetailsDAO.findOneById(videoId).flatMap {
                    videoDetailsDAO.removeById(videoId)
                    _.fileMetaId
                  }.map(meta => binaryStorageService.remove(meta, user))

                  complete(StatusCodes.OK)
                }
              }


            }
        }
      }
    }


}
