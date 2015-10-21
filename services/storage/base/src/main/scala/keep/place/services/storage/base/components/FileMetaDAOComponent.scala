package keep.place.services.storage.base.components

import keep.place.services.storage.base.dao.FileMetaDAO


trait FileMetaDAOComponent {
  val fileMetaDAO: FileMetaDAO
}
