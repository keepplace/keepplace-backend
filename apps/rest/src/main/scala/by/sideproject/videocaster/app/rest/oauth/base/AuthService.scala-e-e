package by.sideproject.videocaster.app.rest.oauth.base

import java.util.UUID

import akka.actor.Props
import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import by.sideproject.videocaster.app.rest.routes.video.handlers.VideosGetEntitiesHandler
import by.sideproject.videocaster.model.auth.{DropboxIdentity, Profile, Identity}
import by.sideproject.videocaster.services.storage.base.dao.{ProfileDAO, IdentityDAO}
import com.dropbox.core.DbxClient
import org.slf4j.LoggerFactory
import spray.http.HttpCookie
import spray.http.StatusCodes._
import spray.routing
import spray.routing._
import spray.routing.authentication._
import spray.routing.directives.AuthMagnet

import scala.concurrent.{ExecutionContext, Future}
import scala.util
import scala.util.Random

/**
 * TODO: Introduce 2 separate services for storing Identities and Sessions.
 */
trait AuthService
  extends HttpService {
  private val log = LoggerFactory.getLogger(this.getClass)

  def service: OAuth2Provider

  def identityDAO: IdentityDAO

  def profileDAO: ProfileDAO

  implicit val executionContext: ExecutionContext

  implicit def fromFutureAuth[T](auth: ⇒ Future[Authentication[T]]): AuthMagnet[T] = new AuthMagnet(onSuccess(auth))

  private val CredentialsMissingReject = Left(AuthenticationFailedRejection(AuthenticationFailedRejection.CredentialsMissing, List.empty))
  private val CredentialsIncorrectReject = Left(AuthenticationFailedRejection(AuthenticationFailedRejection.CredentialsRejected, List.empty))

  def sessionCookie(ctx: RequestContext) = ctx.request.cookies.find(_.name.equals(OauthConfig.SESSION_NAME))

  def cookieAuth: RequestContext => Future[Authentication[Identity]] = { ctx: RequestContext =>

    Future.successful {
      sessionCookie(ctx) match {
        case Some(sessionId) =>
          identityDAO.findBySessionId(sessionId.content) match {
            case Some(user) => Right(user)
            case _ => {
              clearSession
              CredentialsIncorrectReject
            }
          }
        case None => CredentialsMissingReject
      }
    }
  }

  val securedDirective: Directive1[Identity] = {
    optionalCookie(OauthConfig.SESSION_NAME).flatMap { sessionCookieId =>
      sessionCookieId match {
        case Some(cookie) => {
          identityDAO.findBySessionId(cookie.content) match {
            case Some(session) => provide(session)
            case _ => redirect(OauthConfig.REDIRECT_ROUTE, Found)
          }
        }
        case None => redirect(OauthConfig.REDIRECT_ROUTE, Found)
      }
    }
  }

  def getProfileId(dropboxUID: DropboxIdentity): Future[Int]

  def clearSession = deleteCookie(OauthConfig.SESSION_NAME)

  val oauth2Routes =
    (path(OauthConfig.CALLBACK_ROUTE) & parameters('code)) { code=>


    //    TODO Use more secure algorithm for generating session IDs
      val sessionId = UUID.randomUUID().toString

//      TODO I'm not sure if it's a good idea to set cookie before completing auth process.
    setCookie(HttpCookie(OauthConfig.SESSION_NAME, sessionId, path = Some("/"))) {    ctx =>
            val dropboxIdentity = service.requestAccessToken(code)
      getProfileId(dropboxIdentity).map { profileId =>
        identityDAO.insert(Identity(None,dropboxIdentity.uid,sessionId,None,None,Some(dropboxIdentity.fullName),None,None,profileId,Some(dropboxIdentity.oAuth2Info)))

      }.map{ id =>
          log.debug(s"Redirecting client with session id: $sessionId")
          ctx.redirect(OauthConfig.ON_LOGIN_GO_TO, Found)
        }
      }
    } ~
      path(OauthConfig.REDIRECT_ROUTE) {
        redirect(service.getAuthorizationCodeRequestUrl(), Found)
      }
}

