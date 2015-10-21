package keep.place.model.auth

import keep.place.model.BaseObject

case class Profile(id: Option[Int], username: Option[String], country: Option[String], dropboxId: String, rssToken: String) extends BaseObject[Int]
