package by.sideproject.videocaster.model.auth

import by.sideproject.videocaster.model.BaseObject

case class Profile(id: Option[Long], username: Option[String], country: Option[String], dropboxId: Option[String]) extends BaseObject[Long]
