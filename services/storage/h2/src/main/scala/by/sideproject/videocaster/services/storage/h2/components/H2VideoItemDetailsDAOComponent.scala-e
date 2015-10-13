package by.sideproject.videocaster.services.storage.h2.components

import by.sideproject.videocaster.services.storage.base.components.VideoItemDetailsDAOComponent
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO
import by.sideproject.videocaster.services.storage.h2.dao.H2VideoItemDetailsDAO
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2VideoItemDetailsDAOComponent
  extends VideoItemDetailsDAOComponent {
  implicit def database: Database
  implicit def ec: ExecutionContext

  lazy val videoItemDetailsDAO: VideoItemDetailsDAO = new H2VideoItemDetailsDAO

}
