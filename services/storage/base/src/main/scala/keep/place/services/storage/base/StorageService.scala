package keep.place.services.storage.base

import keep.place.services.storage.base.components._

trait StorageService
  extends VideoItemDetailsDAOComponent
  with FileMetaDAOComponent
  with IdentityDAOComponent
  with ProfileDAOComponent
  with PodcastItemDAOComponent {

  def shoutdown
}
