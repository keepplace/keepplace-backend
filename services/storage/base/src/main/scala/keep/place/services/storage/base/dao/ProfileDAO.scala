package keep.place.services.storage.base.dao

import keep.place.model.auth.{Identity, Profile}

import scala.concurrent.Future

trait ProfileDAO extends BaseDAO[Profile, Int]{
  def findByDropboxId(dropboxId: String) : Future[Option[Profile]]
  def findByRssToken(rssToken: String): Future[Option[Profile]]
}
