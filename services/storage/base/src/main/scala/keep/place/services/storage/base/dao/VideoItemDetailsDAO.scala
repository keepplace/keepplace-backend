package keep.place.services.storage.base.dao

import keep.place.model.VideoItemDetails

import scala.concurrent.Future

trait VideoItemDetailsDAO extends BaseDAO[VideoItemDetails, Int] {
  def findAllByProfileId(profileId: Int): Future[Seq[VideoItemDetails]]
}
