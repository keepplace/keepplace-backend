package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import by.sideproject.videocaster.app.rest.oauth.dropbox.DropboxAuthService
import by.sideproject.videocaster.app.rest.rejections.LoginRedirectionRejection
import by.sideproject.videocaster.app.rest.routes.base.BaseAPI
import by.sideproject.videocaster.services.storage.base.StorageService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes
import spray.http.StatusCodes._
import spray.routing.{RejectionHandler, Rejection}

class LoginHandler(storageService: StorageService, domain: String)
                  (implicit context: ActorContext)
  extends DropboxAuthService(storageService.identityDAO, storageService.profileDAO)
  with BaseAPI {


  protected val log = LoggerFactory.getLogger(this.getClass)


  val route = pathPrefix("auth") {
    authRoutes ~ oauth2Routes
  }

  lazy val authRoutes =
    path("profile") {
      import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
      import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

      respondWithMediaType(json) {

        authenticate(cookieAuth) { identity =>
          pathEnd {
            get {
              complete{
                storageService.profileDAO.findOneById(identity.profileId)
              }
            }
          }
        }
      }
    } ~
      path("login") {
        get{
          authenticate(loginRedirectionAuth) { identity =>
            redirect("/auth/profile", StatusCodes.Found)
          }
        }
      }

}
