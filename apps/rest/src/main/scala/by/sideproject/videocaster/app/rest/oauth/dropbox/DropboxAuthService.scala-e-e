package by.sideproject.videocaster.app.rest.oauth.dropbox

import java.util.Locale

import akka.actor.ActorRefFactory
import by.sideproject.videocaster.app.rest.oauth.base.{AuthService, OAuth2Provider}
import by.sideproject.videocaster.model.auth.{Profile, Identity}
import by.sideproject.videocaster.services.storage.base.dao.{ProfileDAO, IdentityDAO}
import com.dropbox.core.{DbxClient, DbxRequestConfig}
import org.slf4j.LoggerFactory

class DropboxAuthService(identityStorage: IdentityDAO, profileStorage: ProfileDAO)
                        (implicit actorFactory: ActorRefFactory)
  extends AuthService {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def service: OAuth2Provider = new DropboxProvider()

  override def identityDAO: IdentityDAO = identityStorage

  override def profileDAO: ProfileDAO = profileStorage

  override implicit def actorRefFactory: ActorRefFactory = actorFactory

  implicit val executionContext = actorFactory.dispatcher

  val config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString())

  def getProfile(identity: Identity): Option[Profile] = {

    withClient(identity) { client =>
      val accountInfo = client.getAccountInfo()

      val theLatestProfile = Profile(None
        , username = Some(accountInfo.displayName)
        , dropboxId = Some(accountInfo.userId.toString)
        , country = Some(accountInfo.country))

      profileDAO.findByDropboxId(identity.uid) match {
        case Some(profile) => profileDAO.update(profile.copy(username = theLatestProfile.username))
        case None => profileDAO.insert(theLatestProfile.copy(id = Some(profileDAO.getNewId)))
      }

      profileDAO.findByDropboxId(identity.uid)
    }
  }


  private def withClient[T](identity: Identity)(f: (DbxClient) => Option[T]): Option[T] = {
    log.debug(s"Setting up client for identity: $identity")
    identity.oAuth2Info.flatMap { oauthInfo =>
      val client: DbxClient = new DbxClient(config, oauthInfo.accessToken)
      f(client)
    }
  }

}
