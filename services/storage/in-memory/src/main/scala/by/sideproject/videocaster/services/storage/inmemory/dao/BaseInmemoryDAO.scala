package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.BaseObject
import by.sideproject.videocaster.services.storage.base.dao.BaseDAO
import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.collection.mutable.Map
import scala.concurrent.{ExecutionContext, Future}

abstract class BaseInmemoryDAO[T <: BaseObject[Int]] extends BaseDAO[T, Int] {
  import scala.concurrent.ExecutionContext.Implicits.global

  private val log = LoggerFactory.getLogger(this.getClass)

  val storage: Map[Int, T] = new mutable.HashMap


  override def findOneById(id: Int): Option[T] = storage.get(id)

  override def update(entity: T): Unit = {
    log.debug(s"Updating entity by ID: $entity")
    entity.id.map(storage.put(_, entity))
  }

  override def insert(entity: T): Option[Int] = {
    entity.id.map { id =>
      storage.put(id, entity)
      log.debug("Inserting item to storage: " + id + " / " + entity)
      id
    }
  }

  override def findAll(): Future[Seq[T]] = Future(storage.values.toSeq)

  override def removeById(id: Int): Unit = storage.remove(id)

}
