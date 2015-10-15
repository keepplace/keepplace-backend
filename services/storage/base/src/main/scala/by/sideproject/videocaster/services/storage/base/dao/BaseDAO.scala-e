package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.BaseObject

import scala.concurrent.Future

trait BaseDAO[T <: BaseObject[ID], ID] {

  def findOneById(id: ID): Future[Option[T]]

  def insert(entity: T): Future[ID]

  def update(entity: T): Unit

  def findAll(): Future[Seq[T]]

  def removeById(id: ID): Unit


}
