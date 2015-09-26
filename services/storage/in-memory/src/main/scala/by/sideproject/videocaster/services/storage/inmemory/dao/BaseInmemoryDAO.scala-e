package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.BaseObject
import by.sideproject.videocaster.model.util.{Page, PageParameter}
import by.sideproject.videocaster.services.storage.base.dao.BaseDAO

import scala.collection.mutable
import scala.collection.mutable.Map
import scala.util.Random

abstract class BaseInmemoryDAO[T <: BaseObject] extends BaseDAO[T, String]{

  val storage: Map[String, T] = new mutable.HashMap


  override def findOneById(id: String): Option[T] = storage.get(id)

  override def count(): Long = storage.size

  override def update(entity: T): Unit = entity.id.map(storage.put(_, entity))

  override def insert(entity: T): Option[String] = {
    entity.id.map(storage.put(_,entity))

    entity.id
  }

  override def insert(docs: Iterable[T]): Vector[String] = docs.flatMap(doc => insert(doc)).toVector

  override def findAll(): Vector[T] = storage.values.toVector

  override def removeById(id: String): Unit = storage.remove(id)

  override def isUniqueByField(entity: T, query: Pair[String, _]*)(clause: (T, T) => Boolean): Boolean = true

  override def find(pageParameter: PageParameter): Page[T] = Page(findAll(), PageParameter(0, count()), count())

  override def unsetField(id: String, fieldName: String*): Unit = {}
}
