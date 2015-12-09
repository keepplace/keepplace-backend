package keep.place.app.rest.routes.video

import akka.actor.{ActorContext, Props}
import akka.util.Timeout
import keep.place.app.rest.oauth.dropbox.DropboxAuthService
import keep.place.app.rest.routes.base.BaseAPI
import keep.place.app.rest.routes.base.requests.EntityRequest
import keep.place.app.rest.routes.video.handlers._
import keep.place.app.rest.routes.video.requests._
import keep.place.filestorage.base.FileStorageService
import keep.place.model.video.AddVideoRequest
import keep.place.services.downloader.base.DownloadService
import keep.place.services.storage.base.StorageService
import keep.place.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

class VideoAPI(storageService: StorageService,
               downloadService: DownloadService,
               binaryStorageService: FileStorageService)(implicit context: ActorContext, timeout: Timeout)
  extends DropboxAuthService(storageService.identityDAO, storageService.profileDAO)
  with BaseAPI {

  protected val log = LoggerFactory.getLogger(this.getClass)

  private lazy val videoDetailsDAO: VideoItemDetailsDAO = storageService.videoItemDetailsDAO
  private lazy val videosGetentitiesHandler = context.actorOf(Props(new VideosGetEntitiesHandler(storageService.videoItemDtoDAO)))
  private lazy val videosGetEntityHandler = context.actorOf(Props(new VideosGetEntityHandler(videoDetailsDAO)))
  private lazy val videosAddHandler = context.actorOf(Props(new VideosAddHandler(videoDetailsDAO, downloadService)))
  private lazy val videosDeleteEntityHandler = context.actorOf(Props(new VideosDeleteEntityHandler(videoDetailsDAO, storageService.fileMetaDAO, binaryStorageService)))


  val route = {
    import keep.place.app.rest.formaters.json.InstaVideoJsonProtocol._
    import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller

    pathPrefix("videos") {
      authenticate(cookieAuth) { identity =>
        pathEnd {
          get { ctx =>
            videosGetentitiesHandler ! VideosGetEntitiesRequest(ctx, identity.profileId)
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
                  ctx => videosGetEntityHandler ! EntityRequest(ctx, entityId, identity)
                } ~ delete {
                  ctx => videosDeleteEntityHandler ! EntityRequest(ctx, entityId, identity)
                }
              }
            }
          }
      }
    }
  }

}
