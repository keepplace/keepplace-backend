package keep.place.services.storage.base.components

import keep.place.services.storage.base.dao.ProfileDAO


trait ProfileDAOComponent {
  val profileDAO: ProfileDAO
}
