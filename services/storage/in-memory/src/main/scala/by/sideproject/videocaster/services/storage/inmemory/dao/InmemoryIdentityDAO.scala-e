package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.services.storage.base.dao.IdentityDAO

class InmemoryIdentityDAO
  extends InmemoryBaseDAO[Identity]
  with IdentityDAO {
  override def findBySessionId(sessionId: String): Option[Identity] = {
    storage.values.find(_.sessionId == sessionId)
  }
}
