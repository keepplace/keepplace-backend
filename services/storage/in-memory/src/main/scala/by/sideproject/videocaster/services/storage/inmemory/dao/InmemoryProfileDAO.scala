package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.auth.Profile
import by.sideproject.videocaster.services.storage.base.dao.ProfileDAO

import scala.concurrent.Future

class InmemoryProfileDAO
  extends InmemoryBaseDAO[Profile]
  with ProfileDAO {

  override def findByDropboxId(dropboxId: String): Future[Option[Profile]] = Future.successful(storage.values.find(_.dropboxId == Some(dropboxId)))

}
