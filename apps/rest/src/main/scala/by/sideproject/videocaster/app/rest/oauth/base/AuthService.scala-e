package by.sideproject.videocaster.app.rest.oauth.base

import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.services.storage.base.dao.IdentityDAO
import org.slf4j.LoggerFactory
import spray.http.HttpCookie
import spray.http.StatusCodes._
import spray.routing._
import spray.routing.authentication._
import spray.routing.directives.AuthMagnet

import scala.concurrent.{ExecutionContext, Future}

/**
 * TODO: Introduce 2 separate services for storing Identities and Sessions.
 */
trait AuthService
  extends HttpService {
  private val log = LoggerFactory.getLogger(this.getClass)

  def service: OAuth2Provider

  def identityDAO: IdentityDAO

  implicit def fromFutureAuth[T](auth: ⇒ Future[Authentication[T]])(implicit executor: ExecutionContext): AuthMagnet[T] =
    new AuthMagnet(onSuccess(auth))

  private val CredentialsMissingReject = Left(AuthenticationFailedRejection(AuthenticationFailedRejection.CredentialsMissing, List.empty))
  private val CredentialsIncorrectReject = Left(AuthenticationFailedRejection(AuthenticationFailedRejection.CredentialsRejected, List.empty))

  def sessionCookie(ctx: RequestContext) = ctx.request.cookies.find(_.name.equals(OauthConfig.SESSION_NAME))

  def cookieAuth(implicit executor: ExecutionContext): RequestContext => Future[Authentication[Identity]] = { ctx: RequestContext =>

    Future {
      val cookie1: Option[HttpCookie] = sessionCookie(ctx)
      cookie1 match {
        case Some(sessionId) =>
          identityDAO.findOneById(sessionId.content) match {
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

  //TODO : Always redirect to REDIRECT_ROUTE when there is no session
  val securedDirective: Directive1[Identity] = {
    optionalCookie(OauthConfig.SESSION_NAME).flatMap { sessionCookieId =>
      sessionCookieId match {
        case Some(cookie) => {
          identityDAO.findOneById(cookie.content) match {
            case Some(session) => provide(session)
            case _ => redirect(OauthConfig.REDIRECT_ROUTE, Found)
          }
        }
        case None => redirect(OauthConfig.REDIRECT_ROUTE, Found)
      }


    }

  }

  def clearSession = {
    deleteCookie(OauthConfig.SESSION_NAME)
  }


  val oauth2Routes =
    (path(OauthConfig.CALLBACK_ROUTE) & parameters('code)) { code =>
      val identity = service.requestAccessToken(code)
      val sessionId = identityDAO.getRandomSessionId


      identityDAO.update(identity.copy(id = Some(sessionId)))

      setCookie(HttpCookie(OauthConfig.SESSION_NAME, sessionId, path = Some("/"))) {
        redirect(OauthConfig.ON_LOGIN_GO_TO, Found)
      }
    } ~
      path(OauthConfig.REDIRECT_ROUTE) {
        redirect(service.getAuthorizationCodeRequestUrl(), Found)
      }
}

