package keep.place.services.storage.h2.dao.tables

import keep.place.model.filestorage.FileMeta
import slick.driver.H2Driver.api._
import slick.lifted.Tag


class FileMetaT(tag: Tag) extends BaseT[FileMeta](tag ,"FILE_METAS") {

  def downloadURL = column[String]("DOWNLOAD_URL")
  def name= column[String]("NAME")
  def placement= column[String]("PLACEMENT")
  def path= column[String]("PATH")


  override def * =  (id.?, downloadURL,name, placement,path) <> ((FileMeta.apply _).tupled, FileMeta.unapply)
}
