package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.VideoItemDetails

import scala.concurrent.Future

trait VideoItemDetailsDAO extends BaseDAO[VideoItemDetails, Int] {
  def findAllByProfileId(profileId: Int): Future[Seq[VideoItemDetails]]
}
