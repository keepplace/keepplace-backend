package by.sideproject.videocaster.services.storage.h2

import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.h2.components.{H2IdentityDAOComponent, H2ProfileDAOComponent, H2VideoItemDetailsDAOComponent}
import by.sideproject.videocaster.services.storage.inmemory.components._
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext

class H2StorageService(implicit executionContext: ExecutionContext)
  extends StorageService
  with H2VideoItemDetailsDAOComponent
  with InmemoryFileMetaDAOComponent
  with H2IdentityDAOComponent
  with H2ProfileDAOComponent {


  implicit val database = Database.forConfig("database")
  implicit val ec = executionContext

  def shoutdown: Unit = database.close

}
