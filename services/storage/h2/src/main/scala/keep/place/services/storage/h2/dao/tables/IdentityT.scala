package keep.place.services.storage.h2.dao.tables

import keep.place.model.auth.Identity
import slick.driver.H2Driver.api._
import slick.lifted.Tag


class IdentityT(tag: Tag) extends BaseT[Identity](tag ,"IDENTITIES") {

  def sessionid= column[String]("SESSION_ID")
  def profileId= column[Int]("PROFILE_ID")
  def accessToken= column[String]("ACCESS_TOKEN")


  override def * = (id.?, sessionid, profileId, accessToken) <>((Identity.apply _).tupled, Identity.unapply)
}
