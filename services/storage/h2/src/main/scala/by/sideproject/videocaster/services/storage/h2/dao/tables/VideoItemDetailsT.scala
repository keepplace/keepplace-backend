package by.sideproject.videocaster.services.storage.h2.dao.tables

import by.sideproject.videocaster.model.VideoItemDetails
import slick.lifted.Tag
import slick.driver.H2Driver.api._


class VideoItemDetailsT(tag: Tag) extends BaseT[VideoItemDetails](tag ,"VIDEO_ITEM_DETAILS") {

  def title = column[String]("TITLE")
  def description = column[String]("DESCRIPTION")
  def fileMetaId= column[Int]("FILE_META_ID")
  def originUrl= column[String]("ORIGIN_URL")
  def addDate= column[String]("ADD_DATE")
  def pubDate = column[String]("PUB_DATE")
  def author = column[String]("AUTHOR")
  def note = column[String]("NOTE")
  def status = column[String]("STATUS")

  override def * =  (id.?, title.?, description.?, fileMetaId.?, originUrl, addDate, status, pubDate.?, author.?, note.?) <> ((VideoItemDetails.apply _).tupled, VideoItemDetails.unapply)
}
