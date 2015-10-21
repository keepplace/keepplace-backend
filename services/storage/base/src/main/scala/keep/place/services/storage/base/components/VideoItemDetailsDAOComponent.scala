package keep.place.services.storage.base.components

import keep.place.services.storage.base.dao.VideoItemDetailsDAO


trait VideoItemDetailsDAOComponent {
  val videoItemDetailsDAO: VideoItemDetailsDAO
}
