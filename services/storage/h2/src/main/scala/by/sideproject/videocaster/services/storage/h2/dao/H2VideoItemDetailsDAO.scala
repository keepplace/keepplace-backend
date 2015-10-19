package by.sideproject.videocaster.services.storage.h2.dao

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import by.sideproject.videocaster.services.storage.h2.H2BaseDAO
import by.sideproject.videocaster.services.storage.h2.dao.tables.VideoItemDetailsT
import slick.driver.H2Driver.api.{Database, _}
import slick.lifted.TableQuery

import scala.concurrent.{Future, ExecutionContext}

class H2VideoItemDetailsDAO
  (implicit executionContext: ExecutionContext, database: Database)
  extends H2BaseDAO[VideoItemDetails, VideoItemDetailsT](TableQuery[VideoItemDetailsT])
  with VideoItemDetailsDAO {

  override def findAllByProfileId(profileId: Int): Future[Seq[VideoItemDetails]] = {
    database.run(tableQuery.filter(_.profileId === profileId).result)
  }

}
