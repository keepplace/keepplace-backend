package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.BaseObject

import scala.concurrent.Future
import scala.util.Random

trait BaseDAO[T <: BaseObject[ID], ID] {


  val random: Random = new Random()

  def getNewId: Int = Math.abs(random.nextInt())


  def findOneById(id: ID): Future[Option[T]]

  def insert(entity: T): Future[ID]

  def update(entity: T): Unit

  def findAll(): Future[Seq[T]]

  def removeById(id: ID): Unit


}
