package by.sideproject.videocaster.services.storage.inmemory.components

import by.sideproject.videocaster.services.storage.base.components.FileMetaDAOComponent
import by.sideproject.videocaster.services.storage.base.dao.ProfileDAO
import by.sideproject.videocaster.services.storage.inmemory.dao.InmemoryProfileDAO

trait InmemoryProfileDAOComponent extends FileMetaDAOComponent {

  lazy val profileDAO: ProfileDAO = new InmemoryProfileDAO

}
