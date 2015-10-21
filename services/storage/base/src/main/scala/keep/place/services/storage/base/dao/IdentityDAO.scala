package keep.place.services.storage.base.dao

import keep.place.model.auth.Identity

import scala.concurrent.Future

trait IdentityDAO extends BaseDAO[Identity, Int]{
  def findBySessionId(sessionId: String): Future[Option[Identity]]
}
