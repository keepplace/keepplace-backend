package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.BaseObject
import by.sideproject.videocaster.services.storage.base.dao.BaseDAO
import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.collection.mutable.Map
import scala.concurrent.Future
import scala.util.Random

abstract class InmemoryBaseDAO[T <: BaseObject[Int]] extends BaseDAO[T, Int] {

  import scala.concurrent.ExecutionContext.Implicits.global

  private val log = LoggerFactory.getLogger(this.getClass)

  val storage: Map[Int, T] = new mutable.HashMap


  override def findOneById(id: Int): Future[Option[T]] = Future.successful{
    log.debug(s"Fetching entity by id $id")
    val maybeT = storage.get(id)
    log.debug(s"Fetching entity by id $maybeT")
    maybeT
  }

  override def update(entity: T): Unit = {
    log.debug(s"Updating entity by ID: $entity")
    entity.id.map(storage.put(_, entity))
  }

  override def insert(entity: T): Future[Int] = {

    Future.successful {
      val id = Math.abs(new Random().nextInt())
      log.debug(s"Insering entity $entity with id $id")
      storage.put(id, entity)
      id
    }

  }


  override def findAll(): Future[Seq[T]] = Future.successful(storage.values.toSeq)

  override def removeById(id: Int): Unit = storage.remove(id)

}
