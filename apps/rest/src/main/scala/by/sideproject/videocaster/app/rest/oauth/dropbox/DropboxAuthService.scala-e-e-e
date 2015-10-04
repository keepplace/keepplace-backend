package by.sideproject.videocaster.app.rest.oauth.dropbox

import akka.actor.ActorRefFactory
import by.sideproject.videocaster.app.rest.oauth.base.{AuthService, OAuth2Provider}
import by.sideproject.videocaster.services.storage.base.dao.IdentityDAO

class DropboxAuthService(identityStorage: IdentityDAO)(implicit actorFactory: ActorRefFactory) extends AuthService {
  override def service: OAuth2Provider = new DropboxProvider()
  override def identityDAO: IdentityDAO = identityStorage

  override implicit def actorRefFactory: ActorRefFactory = actorFactory

  implicit val executionContext = actorFactory.dispatcher
}
