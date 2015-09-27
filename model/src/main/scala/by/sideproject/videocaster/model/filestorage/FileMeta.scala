package by.sideproject.videocaster.model.filestorage

import by.sideproject.videocaster.model.BaseObject

case class FileMeta(id: Option[String], downloadURL: String, name: String, path: String) extends BaseObject
