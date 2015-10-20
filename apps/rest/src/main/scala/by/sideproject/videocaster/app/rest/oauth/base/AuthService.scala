package by.sideproject.videocaster.app.rest.oauth.base

import java.util.UUID

import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import by.sideproject.videocaster.app.rest.rejections.LoginRedirectionRejection
import by.sideproject.videocaster.model.auth.{DropboxIdentity, Identity, Profile}
import by.sideproject.videocaster.services.storage.base.dao.{IdentityDAO, ProfileDAO}
import org.slf4j.LoggerFactory
import spray.http.HttpCookie
import spray.http.StatusCodes._
import spray.routing._
import spray.routing.authentication._
import spray.routing.directives.AuthMagnet

import scala.concurrent.{ExecutionContext, Future}

trait AuthService
  extends HttpService {
  private val log = LoggerFactory.getLogger(this.getClass)

  def service: OAuth2Provider

  def identityDAO: IdentityDAO

  def profileDAO: ProfileDAO

  implicit val executionContext: ExecutionContext



  private val CredentialsMissingReject = Left(AuthenticationFailedRejection(AuthenticationFailedRejection.CredentialsMissing, List.empty))
  private val CredentialsIncorrectReject = Left(AuthenticationFailedRejection(AuthenticationFailedRejection.CredentialsRejected, List.empty))
  private def sendToLoginReject(resolution: String) = Left(LoginRedirectionRejection(resolution))

  def sessionCookie(ctx: RequestContext) = ctx.request.cookies.find(_.name.equals(OauthConfig.SESSION_NAME))

  implicit def fromFutureAuth[T](auth: ⇒ Future[Authentication[T]]): AuthMagnet[T] = new AuthMagnet(onSuccess(auth))


  def cookieAuth: RequestContext => Future[Authentication[Identity]] = { ctx: RequestContext =>
      sessionCookie(ctx).map { sessionCookieId =>
        identityDAO.findBySessionId(sessionCookieId.content).map {
          case Some(user) => Right(user)
          case _ => {
            clearSession
            CredentialsIncorrectReject
          }
        }
      }.getOrElse(Future.successful(CredentialsMissingReject))
  }

  def rssTokenAuth(rssToken: String): RequestContext => Future[Authentication[Profile]] = { ctx: RequestContext =>
    profileDAO.findByRssToken(rssToken).map {
      case Some(user) => Right(user)
      case _ => CredentialsMissingReject
    }
  }


  def loginRedirectionAuth: RequestContext => Future[Authentication[Identity]] = { ctx: RequestContext =>

    sessionCookie(ctx).map { sessionCookieId =>
      identityDAO.findBySessionId(sessionCookieId.content).map {
        case Some(user) => Right(user)
        case _ => {
          clearSession
          sendToLoginReject("Can't find any session by provided ID")
        }
      }
    }.getOrElse(Future.successful(sendToLoginReject("Session ID is not provided")))
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
        identityDAO.insert(Identity(None,sessionId,profileId,dropboxIdentity.oAuth2Info.accessToken))

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

