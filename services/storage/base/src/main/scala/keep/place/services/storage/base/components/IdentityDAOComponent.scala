package keep.place.services.storage.base.components

import keep.place.services.storage.base.dao.IdentityDAO


trait IdentityDAOComponent {
  val identityDAO: IdentityDAO
}
