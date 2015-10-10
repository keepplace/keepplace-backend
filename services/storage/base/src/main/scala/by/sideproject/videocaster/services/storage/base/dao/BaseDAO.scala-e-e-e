package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.BaseObject

import scala.util.Random

trait BaseDAO[T <: BaseObject[ID], ID] {


  val random: Random = new Random()
  def getNewId: Int = Math.abs(random.nextInt())


  def findOneById(id: ID): Option[T]

  def insert(entity: T): Option[ID]

  def update(entity: T): Unit

  def findAll(): Vector[T]

  def removeById(id: ID): Unit


}
