package by.sideproject.videocaster.services.storage.h2

import by.sideproject.videocaster.model.BaseObject
import by.sideproject.videocaster.services.storage.base.dao.BaseDAO
import by.sideproject.videocaster.services.storage.h2.dao.tables.BaseT
import org.slf4j.LoggerFactory
import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class H2BaseDAO[T <: BaseObject[Int], R <: BaseT[T]](val tableQuery: TableQuery[R])
                                                    (implicit executionContext: ExecutionContext, database: Database)
  extends BaseDAO[T, Int] {

  private val log = LoggerFactory.getLogger(this.getClass)
  private val createReturningId = tableQuery returning tableQuery.map { item : R => item.id }

  def findOneById(id: Int): Future[Option[T]] = {
    database.run(tableQuery.filter(_.id === id).result.headOption)
  }

  override def insert(entity: T): Future[Int] = database.run(createReturningId += entity)

  // TODO It makes sense to to pass id and entity (id: Int, entity: T) to this method
  override def update(entity: T): Unit = {
    database.run(tableQuery.filter(_.id === entity.id).update(entity)).map(_ => ())
  }

  override def findAll(): Future[Seq[T]] = database.run(tableQuery.result)

  override def removeById(id: Int): Unit = database.run(tableQuery.filter(_.id === id).delete).map(_ => ())

}
