package by.sideproject.videocaster.services.storage.h2.components

import by.sideproject.videocaster.services.storage.base.components.FileMetaDAOComponent
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import by.sideproject.videocaster.services.storage.h2.dao.H2FileMetaDAO
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2FileMetaDAOComponent
  extends FileMetaDAOComponent {

  implicit def database: Database

  implicit def ec: ExecutionContext

  lazy val fileMetaDAO: FileMetaDAO = new H2FileMetaDAO

}
