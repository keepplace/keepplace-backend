package keep.place.services.storage.h2.components

import keep.place.services.storage.base.components.{VideoItemDtoDAOComponent, IdentityDAOComponent}
import keep.place.services.storage.base.dao.{FileMetaDAO, VideoItemDetailsDAO, VideoItemDtoDAO, IdentityDAO}
import keep.place.services.storage.h2.dao.{H2FileMetaDAO, H2VideoItemDetailsDAO, H2VideoItemDtoDAO, H2IdentityDAO}
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2VideoItemDtoDAOComponent
  extends VideoItemDtoDAOComponent {
  implicit def database: Database
  implicit def ec: ExecutionContext
  implicit def h2VideoItemDetailsDAO: H2VideoItemDetailsDAO
  implicit def h2FileMetaDAO: H2FileMetaDAO

  lazy val videoItemDtoDAO: VideoItemDtoDAO = new H2VideoItemDtoDAO

}
