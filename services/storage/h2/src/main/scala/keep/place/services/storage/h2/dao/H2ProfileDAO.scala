package keep.place.services.storage.h2.dao

import keep.place.model.auth.Profile
import keep.place.services.storage.base.dao.ProfileDAO
import keep.place.services.storage.h2.H2BaseDAO
import keep.place.services.storage.h2.dao.tables.ProfileT
import slick.driver.H2Driver.api.{Database, _}
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class H2ProfileDAO
  (implicit executionContext: ExecutionContext, database: Database)
  extends H2BaseDAO[Profile, ProfileT](TableQuery[ProfileT])
  with ProfileDAO {

  override def findByDropboxId(dropboxId: String): Future[Option[Profile]] = {
    database.run(tableQuery.filter(_.dropboxId === dropboxId).result.headOption)
  }

  override def findByRssToken(rssToken: String): Future[Option[Profile]] = {
    database.run(tableQuery.filter(_.rssToken === rssToken).result.headOption)
  }
}
