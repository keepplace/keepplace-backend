package keep.place.model.filestorage

import keep.place.model.BaseObject

case class FileMeta(id: Option[Int], downloadURL: String, name: String, placement: String, path: String) extends BaseObject[Int]
