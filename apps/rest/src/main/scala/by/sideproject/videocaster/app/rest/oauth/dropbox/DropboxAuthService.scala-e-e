package by.sideproject.videocaster.app.rest.oauth.dropbox

import akka.actor.ActorRefFactory
import by.sideproject.videocaster.app.rest.oauth.base.{SessionStore, AuthService, OAuth2Provider}

class DropboxAuthService(baseSessionStore: SessionStore)(implicit actorFactory: ActorRefFactory) extends AuthService {
  override def service: OAuth2Provider = new DropboxProvider()
  override def sessionStore: SessionStore = baseSessionStore

  override implicit def actorRefFactory: ActorRefFactory = actorFactory

  implicit val executionContext = actorFactory.dispatcher
}
