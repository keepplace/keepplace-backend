package by.sideproject.videocaster.services.storage.h2.dao.tables

import by.sideproject.videocaster.model.filestorage.FileMeta
import slick.driver.H2Driver.api._
import slick.lifted.Tag


class FileMetaT(tag: Tag) extends BaseT[FileMeta](tag ,"FILE_METAS") {

  def downloadURL = column[String]("DOWNLOAD_URL")
  def secondaryDownloadURL = column[String]("SECONDARY_DOWNLOAD_URL")
  def name= column[String]("NAME")
  def placement= column[String]("PLACEMENT")
  def path= column[String]("PATH")


  override def * =  (id.?, downloadURL, secondaryDownloadURL.?, name, placement,path) <> ((FileMeta.apply _).tupled, FileMeta.unapply)
}
