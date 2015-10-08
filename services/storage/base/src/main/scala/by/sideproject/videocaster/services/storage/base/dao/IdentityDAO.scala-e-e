package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.auth.Identity

trait IdentityDAO extends BaseDAO[Identity, Int]{
  def getRandomSessionId: Int = getNewId
  def findBySessionId(sessionId: String): Option[Identity]
}
