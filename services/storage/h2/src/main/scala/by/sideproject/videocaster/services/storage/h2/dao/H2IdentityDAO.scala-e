package by.sideproject.videocaster.services.storage.h2.dao

import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.services.storage.base.dao.IdentityDAO
import by.sideproject.videocaster.services.storage.h2.H2BaseDAO
import by.sideproject.videocaster.services.storage.h2.dao.tables.IdentityT
import slick.driver.H2Driver.api.{Database, _}
import slick.lifted.TableQuery

import scala.concurrent.{Future, ExecutionContext}

class H2IdentityDAO
  (implicit executionContext: ExecutionContext, database: Database)
  extends H2BaseDAO[Identity, IdentityT](TableQuery[IdentityT])
  with IdentityDAO {
  override def findBySessionId(sessionId: String): Future[Option[Identity]] ={
    database.run(tableQuery.filter(_.sessionid === sessionId).result.headOption)
  }
}
