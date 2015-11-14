package keep.place.services.storage.h2

import keep.place.services.storage.base.StorageService
import keep.place.services.storage.h2.components._
import keep.place.services.storage.h2.dao.H2VideoItemDetailsDAO
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext

class H2StorageService(implicit executionContext: ExecutionContext)
  extends StorageService
  with H2VideoItemDetailsDAOComponent
  with H2FileMetaDAOComponent
  with H2IdentityDAOComponent
  with H2ProfileDAOComponent
  with H2VideoItemDtoDAOComponent {


  implicit val database = Database.forConfig("database")
  implicit val ec = executionContext

  def shoutdown: Unit = database.close

}
