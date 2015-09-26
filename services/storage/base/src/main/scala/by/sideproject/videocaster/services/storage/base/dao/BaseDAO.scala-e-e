package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.BaseObject
import by.sideproject.videocaster.model.util.{Page, PageParameter}

trait BaseDAO[T <: BaseObject, ID] {

  def findOneById(id: ID): Option[T]

  def insert(entity: T): Option[ID]

  def insert(docs: Iterable[T]): Vector[ID]

  def update(entity: T): Unit

  def findAll(): Vector[T]

  def find(pageParameter: PageParameter): Page[T]

  def count(): Long

  def removeById(id: ID): Unit

  def unsetField(id: ID, fieldName: String*): Unit

  def isUniqueByField(entity: T, query: (String, _)*)
                     (clause: (T,T) ⇒ Boolean): Boolean

}
