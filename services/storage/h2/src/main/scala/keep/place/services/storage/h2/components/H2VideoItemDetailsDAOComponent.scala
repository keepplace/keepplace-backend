package keep.place.services.storage.h2.components

import keep.place.services.storage.base.components.VideoItemDetailsDAOComponent
import keep.place.services.storage.base.dao.VideoItemDetailsDAO
import keep.place.services.storage.h2.dao.H2VideoItemDetailsDAO
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2VideoItemDetailsDAOComponent
  extends VideoItemDetailsDAOComponent {
  implicit def database: Database
  implicit def ec: ExecutionContext

  lazy val h2VideoItemDetailsDAO: H2VideoItemDetailsDAO = new H2VideoItemDetailsDAO

  lazy val videoItemDetailsDAO: VideoItemDetailsDAO = h2VideoItemDetailsDAO

}
