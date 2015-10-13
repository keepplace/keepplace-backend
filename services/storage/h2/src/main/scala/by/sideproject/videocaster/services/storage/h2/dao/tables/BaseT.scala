package by.sideproject.videocaster.services.storage.h2.dao.tables

import slick.lifted.Tag
import slick.driver.H2Driver.api._

abstract class BaseT[T](tag: Tag, tableName: String) extends Table[T](tag, tableName) {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
}
