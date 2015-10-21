package keep.place.services.storage.h2.dao

import keep.place.model.auth.Identity
import keep.place.services.storage.base.dao.IdentityDAO
import keep.place.services.storage.h2.H2BaseDAO
import keep.place.services.storage.h2.dao.tables.IdentityT
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
