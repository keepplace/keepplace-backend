package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.app.rest.oauth.dropbox.DropboxAuthService
import by.sideproject.videocaster.services.storage.base.StorageService
import org.slf4j.LoggerFactory
import spray.http.StatusCodes

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
              val profile = identity.profileId.map(storageService.profileDAO.findOneById(_))
              complete(profile)
            }
          }
        }
      }
    } ~
      path("login") {
        (get & securedDirective) ( identity => redirect("/auth/profile", StatusCodes.Found))
      }

}
