package keep.place.services.storage.h2.dao.tables

import keep.place.model.auth.Profile
import slick.driver.H2Driver.api._
import slick.lifted.Tag


class ProfileT(tag: Tag) extends BaseT[Profile](tag ,"PROFILES") {

  def username = column[String]("USERNAME")
  def country = column[String]("COUNTRY")
  def dropboxId= column[String]("DROPBOX_ID")
  def rssToken = column[String]("RSS_TOKEN")


  override def * =  (id.?, username.?, country.?, dropboxId, rssToken) <> ((Profile.apply _).tupled, Profile.unapply)
}
