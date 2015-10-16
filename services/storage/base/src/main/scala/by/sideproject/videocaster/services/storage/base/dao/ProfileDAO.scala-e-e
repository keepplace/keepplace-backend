package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.auth.{Identity, Profile}

import scala.concurrent.Future

trait ProfileDAO extends BaseDAO[Profile, Int]{
  def findByDropboxId(dropboxId: String) : Future[Option[Profile]]
  def findByRssToken(rssToken: String): Future[Option[Profile]]
}
