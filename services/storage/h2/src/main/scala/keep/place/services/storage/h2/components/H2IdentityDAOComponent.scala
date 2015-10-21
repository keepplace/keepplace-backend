package keep.place.services.storage.h2.components

import keep.place.services.storage.base.components.{IdentityDAOComponent, ProfileDAOComponent}
import keep.place.services.storage.base.dao.{IdentityDAO, ProfileDAO}
import keep.place.services.storage.h2.dao.{H2IdentityDAO, H2ProfileDAO}
import slick.driver.H2Driver.api.Database

import scala.concurrent.ExecutionContext


trait H2IdentityDAOComponent
  extends IdentityDAOComponent {
  implicit def database: Database
  implicit def ec: ExecutionContext

  lazy val identityDAO: IdentityDAO = new H2IdentityDAO

}
