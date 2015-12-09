package keep.place.app.rest.routes

import akka.actor.ActorContext
import keep.place.app.rest.oauth.dropbox.DropboxAuthService
import keep.place.app.rest.routes.base.BaseAPI
import keep.place.services.storage.base.StorageService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes

class LoginHandler(storageService: StorageService)
                  (implicit context: ActorContext)
  extends DropboxAuthService(storageService.identityDAO, storageService.profileDAO)
  with BaseAPI {


  protected val log = LoggerFactory.getLogger(this.getClass)


  val route = pathPrefix("auth") {
    authRoutes ~ oauth2Routes
  }

  lazy val authRoutes =
    path("profile") {
      import keep.place.app.rest.formaters.json.InstaVideoJsonProtocol._
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
            redirect("/api/auth/profile", StatusCodes.Found)
          }
        }
      }

}
