package keep.place.services.storage.h2.components

import keep.place.services.storage.base.components.FileMetaDAOComponent
import keep.place.services.storage.base.dao.FileMetaDAO
import keep.place.services.storage.h2.dao.H2FileMetaDAO
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2FileMetaDAOComponent
  extends FileMetaDAOComponent {

  implicit def database: Database

  implicit def ec: ExecutionContext

  lazy val h2FileMetaDAO: H2FileMetaDAO = new H2FileMetaDAO
  lazy val fileMetaDAO: FileMetaDAO = h2FileMetaDAO

}
