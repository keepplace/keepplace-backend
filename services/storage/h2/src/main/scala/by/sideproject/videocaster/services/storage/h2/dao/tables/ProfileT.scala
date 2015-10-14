package by.sideproject.videocaster.services.storage.h2.dao.tables

import by.sideproject.videocaster.model.auth.Profile
import slick.driver.H2Driver.api._
import slick.lifted.Tag


class ProfileT(tag: Tag) extends BaseT[Profile](tag ,"PROFILES") {

  def username = column[String]("USERNAME")
  def country = column[String]("COUNTRY")
  def dropboxId= column[String]("DROPBOX_ID")


  override def * =  (id.?, username.?, country.?, dropboxId) <> ((Profile.apply _).tupled, Profile.unapply)
}