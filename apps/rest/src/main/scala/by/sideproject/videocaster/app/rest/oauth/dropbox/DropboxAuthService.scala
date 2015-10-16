package by.sideproject.videocaster.app.rest.oauth.dropbox

import java.util.UUID

import akka.actor.ActorRefFactory
import by.sideproject.videocaster.app.rest.oauth.base.{AuthService, OAuth2Provider}
import by.sideproject.videocaster.model.auth.{DropboxIdentity, Profile}
import by.sideproject.videocaster.services.storage.base.dao.{IdentityDAO, ProfileDAO}
import org.slf4j.LoggerFactory

import scala.concurrent.Future

class DropboxAuthService(identityStorage: IdentityDAO, profileStorage: ProfileDAO)
                        (implicit actorFactory: ActorRefFactory)
  extends AuthService {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def service: OAuth2Provider = new DropboxProvider()

  override def identityDAO: IdentityDAO = identityStorage

  override def profileDAO: ProfileDAO = profileStorage

  override implicit def actorRefFactory: ActorRefFactory = actorFactory

  implicit val executionContext = actorFactory.dispatcher


  def getProfileId(identity: DropboxIdentity): Future[Int] = {

    log.debug(s"Setting up client for identity: $identity")

    profileDAO.findByDropboxId(identity.uid).flatMap { currentProfile =>
      log.debug(s"Fetched information on current profile: $currentProfile")
      currentProfile match {
        case Some(Profile(Some(id),_,_,_,_)) => Future.successful(id)
        case None => {
          val rssToken = UUID.randomUUID().toString
          val newProfile = Profile(None, Some(identity.fullName), None, identity.uid,rssToken)
          profileDAO.insert(newProfile)
        }
      }
    }
  }

}
