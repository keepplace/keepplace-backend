package keep.place.services.storage.base.dao

import keep.place.model.rss.PodcastItem

import scala.concurrent.Future

trait PodcastItemDAO {
  def fetchPodcastItems(profileId: Int): Future[Seq[PodcastItem]]
}
