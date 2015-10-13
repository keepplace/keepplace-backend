package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.auth.Profile

import scala.concurrent.Future

trait ProfileDAO extends BaseDAO[Profile, Int]{
  def findByDropboxId(dropboxId: String) : Future[Option[Profile]]
}
