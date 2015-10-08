package by.sideproject.videocaster.model.filestorage

import by.sideproject.videocaster.model.BaseObject

case class FileMeta(id: Option[Int], downloadURL: String, secondaryDownloadURL: Option[String], name: String, placement: String, path: String) extends BaseObject[Int]
