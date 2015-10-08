package by.sideproject.videocaster.app.rest.routes.video

import akka.actor.{ActorContext, Props}
import akka.util.Timeout
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.oauth.dropbox.DropboxAuthService
import by.sideproject.videocaster.app.rest.routes.BaseAPI
import by.sideproject.videocaster.app.rest.routes.video.handlers._
import by.sideproject.videocaster.app.rest.routes.video.requests._
import by.sideproject.videocaster.model.video.AddVideoRequest
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

class VideoAPI(storageService: StorageService,
               downloadService: DownloadService,
               binaryStorageService: FileStorageService)(implicit context: ActorContext, timeout: Timeout)
  extends DropboxAuthService(storageService.identityDAO, storageService.profileDAO)
  with BaseAPI {

  protected val log = LoggerFactory.getLogger(this.getClass)

  private val videosGetentitiesHandler = context.actorOf(Props(new VideosGetEntitiesHandler(videoDetailsDAO)))
  private val videosGetEntityHandler = context.actorOf(Props(new VideosGetEntityHandler(videoDetailsDAO)))
  private val videosAddHandler = context.actorOf(Props(new VideosAddHandler(videoDetailsDAO, downloadService)))
  private val videosDeleteEntityHandler = context.actorOf(Props(new VideosDeleteEntityHandler(videoDetailsDAO, binaryStorageService)))

  val videoDetailsDAO: VideoItemDetailsDAO = storageService.videoItemDetailsDAO
  val route = {
    import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
    import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

    pathPrefix("videos") {
      authenticate(cookieAuth) { identity =>
        pathEnd {
          get {
            ctx => videosGetentitiesHandler ! VideosGetEntitiesRequest(ctx)
          } ~
            post {
              entity(as[AddVideoRequest]) { request =>
                ctx => videosAddHandler ! VideosAddRequest(ctx, request, identity)
              }
            }
        } ~
          pathPrefix(IntNumber) {
            entityId => {
              pathEnd {
                get {
                  ctx => videosGetEntityHandler ! VideosEntityRequest(ctx, entityId, identity)
                } ~ delete {
                  ctx => videosDeleteEntityHandler ! VideosEntityRequest(ctx, entityId, identity)
                }
              }
            }
          }
      }
    }
  }

}
