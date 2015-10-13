package by.sideproject.videocaster.services.storage.inmemory

import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.inmemory.components.{InmemoryFileMetaDAOComponent, InmemoryIdentityDAOComponent, InmemoryProfileDAOComponent, InmemoryVideoItemDetailsDAOComponent}

object InmemoryStorageService
  extends StorageService
  with InmemoryVideoItemDetailsDAOComponent
  with InmemoryFileMetaDAOComponent
  with InmemoryIdentityDAOComponent
  with InmemoryProfileDAOComponent {
  override def shoutdown: Unit = {}
}
