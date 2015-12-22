package keep.place.services.storage.base.dao

import keep.place.model.rss.PodcastItem
import keep.place.model.video.VideoItemDetailsDTO

import scala.concurrent.Future

trait VideoItemDtoDAO {
  def fetchPodcastItems(profileId: Int): Future[Seq[PodcastItem]]
  def findAllByProfileId(profileId: Int): Future[Seq[VideoItemDetailsDTO]]
  def findOneById(profileId: Int, id: Int): Future[Option[VideoItemDetailsDTO]]

}
