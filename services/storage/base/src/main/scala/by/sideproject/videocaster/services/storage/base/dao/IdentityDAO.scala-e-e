package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.auth.Identity

import scala.concurrent.Future

trait IdentityDAO extends BaseDAO[Identity, Int]{
  def findBySessionId(sessionId: String): Future[Option[Identity]]
}
