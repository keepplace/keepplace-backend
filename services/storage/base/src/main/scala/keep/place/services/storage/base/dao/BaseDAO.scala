package keep.place.services.storage.base.dao

import keep.place.model.BaseObject

import scala.concurrent.Future

trait BaseDAO[T <: BaseObject[ID], ID] {

  def findOneById(id: ID): Future[Option[T]]

  def insert(entity: T): Future[ID]

  def update(entity: T): Unit

  def findAll(): Future[Seq[T]]

  def removeById(id: ID): Unit


}
