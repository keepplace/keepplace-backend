package keep.place.services.storage.h2.components

import keep.place.services.storage.base.components.ProfileDAOComponent
import keep.place.services.storage.base.dao.ProfileDAO
import keep.place.services.storage.h2.dao.H2ProfileDAO
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2ProfileDAOComponent
  extends ProfileDAOComponent {
  implicit def database: Database
  implicit def ec: ExecutionContext

  lazy val profileDAO: ProfileDAO = new H2ProfileDAO

}
