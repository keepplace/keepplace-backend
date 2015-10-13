package by.sideproject.videocaster.services.storage.base

import by.sideproject.videocaster.services.storage.base.components.{ProfileDAOComponent, FileMetaDAOComponent, IdentityDAOComponent, VideoItemDetailsDAOComponent}

trait StorageService
  extends VideoItemDetailsDAOComponent
  with FileMetaDAOComponent
  with IdentityDAOComponent
  with ProfileDAOComponent {

  def shoutdown
}
