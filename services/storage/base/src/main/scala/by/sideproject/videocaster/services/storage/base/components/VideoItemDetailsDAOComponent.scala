package by.sideproject.videocaster.services.storage.base.components

import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO


trait VideoItemDetailsDAOComponent {
  val videoItemDetailsDAO: VideoItemDetailsDAO
}
