package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.auth.Profile
import by.sideproject.videocaster.services.storage.base.dao.ProfileDAO

class InmemoryProfileDAO
  extends BaseInmemoryDAO[Profile]
  with ProfileDAO {

  override def findByDropboxId(dropboxId: String): Option[Profile] = storage.values.find(_.dropboxId == Some(dropboxId))
}
