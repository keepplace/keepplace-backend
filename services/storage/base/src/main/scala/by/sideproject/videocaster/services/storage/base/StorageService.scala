package by.sideproject.videocaster.services.storage.base

import by.sideproject.videocaster.services.storage.base.components.{FileMetaDAOComponent, VideoItemDetailsDAOComponent}

trait StorageService
  extends VideoItemDetailsDAOComponent
  with FileMetaDAOComponent
