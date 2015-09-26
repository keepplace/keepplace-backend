package by.sideproject.videocaster.services.storage.inmemory

import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.inmemory.components.InmemoryVideoItemDetailsDAOComponent

object InmemoryStorageService
  extends StorageService
  with InmemoryVideoItemDetailsDAOComponent
