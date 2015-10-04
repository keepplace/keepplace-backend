package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.app.rest.oauth.dropbox.DropboxAuthService
import by.sideproject.videocaster.services.storage.base.StorageService
import org.slf4j.LoggerFactory

class LoginHandler(storageService: StorageService, domain: String)
                  (implicit context: ActorContext)
  extends DropboxAuthService(storageService.identityDAO)
  with BaseService {


  protected val log = LoggerFactory.getLogger(this.getClass)


  val route = pathPrefix("auth") {
    authRoutes ~ oauth2Routes
  }

  lazy val authRoutes =
    path("profile") {
      import by.sideproject.videocaster.app.rest.formaters.json.InstaVideoJsonProtocol._
      import spray.httpx.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}

      respondWithMediaType(json) {

        authenticate(cookieAuth) { userInfo =>
          pathEnd {
            get {
              complete(userInfo)
            }
          }
        }
      }
    } ~
      path("login") {
        (get & securedDirective) { identity =>
          complete {
            s"My UID is : ${identity.uid}"
          }
        }
      }

}
