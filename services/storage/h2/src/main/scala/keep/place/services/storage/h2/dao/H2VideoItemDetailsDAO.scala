package keep.place.services.storage.h2.dao

import keep.place.model.VideoItemDetails
import keep.place.services.storage.base.dao.VideoItemDetailsDAO
import keep.place.services.storage.h2.H2BaseDAO
import keep.place.services.storage.h2.dao.tables.VideoItemDetailsT
import slick.driver.H2Driver.api.{Database, _}
import slick.lifted.TableQuery

import scala.concurrent.{Future, ExecutionContext}

class H2VideoItemDetailsDAO
  (implicit executionContext: ExecutionContext, database: Database)
  extends H2BaseDAO[VideoItemDetails, VideoItemDetailsT](TableQuery[VideoItemDetailsT])
  with VideoItemDetailsDAO {

  override def findAllByProfileId(profileId: Int): Future[Seq[VideoItemDetails]] = {
    database.run(tableQuery.filter(_.profileId === profileId).sortBy(_.addDate.desc).result)
  }

}
