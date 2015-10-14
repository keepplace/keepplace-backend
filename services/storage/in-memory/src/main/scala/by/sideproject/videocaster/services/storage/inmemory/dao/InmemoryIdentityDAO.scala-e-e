package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.services.storage.base.dao.IdentityDAO
import org.slf4j.LoggerFactory

import scala.concurrent.Future

class InmemoryIdentityDAO
  extends InmemoryBaseDAO[Identity]
  with IdentityDAO {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def findBySessionId(sessionId: String): Future[Option[Identity]] = {
    log.debug(s"Looking for a Identity with session with ID $sessionId in storage $storage")
    Future.successful(storage.values.find(_.sessionId == sessionId))
  }
}
