package by.sideproject.videocaster.model.auth

import by.sideproject.videocaster.model.BaseObject

case class Profile(id: Option[Int], username: Option[String], country: Option[String], dropboxId: String, rssToken: String) extends BaseObject[Int]
