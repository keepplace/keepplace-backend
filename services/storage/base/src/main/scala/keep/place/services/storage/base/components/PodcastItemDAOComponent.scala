package keep.place.services.storage.base.components

import keep.place.services.storage.base.dao.{PodcastItemDAO, ProfileDAO}


trait PodcastItemDAOComponent {
  val podcastItemDAO: PodcastItemDAO
}
