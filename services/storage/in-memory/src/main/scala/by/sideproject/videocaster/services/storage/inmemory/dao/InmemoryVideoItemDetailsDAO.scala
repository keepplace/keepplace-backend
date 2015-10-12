package by.sideproject.videocaster.services.storage.inmemory.dao

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.services.storage.base.dao.VideoItemDetailsDAO

class InmemoryVideoItemDetailsDAO
  extends InmemoryBaseDAO[VideoItemDetails]
  with VideoItemDetailsDAO
