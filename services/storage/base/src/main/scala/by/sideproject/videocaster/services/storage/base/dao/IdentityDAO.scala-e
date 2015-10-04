package by.sideproject.videocaster.services.storage.base.dao

import java.util.UUID

import by.sideproject.videocaster.model.auth.Identity

trait IdentityDAO extends BaseDAO[Identity, String]{
  def getRandomSessionId: String = UUID.randomUUID().toString
}
