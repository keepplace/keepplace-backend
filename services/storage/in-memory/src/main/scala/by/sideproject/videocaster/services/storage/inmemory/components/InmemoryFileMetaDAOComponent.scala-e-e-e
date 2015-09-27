package by.sideproject.videocaster.services.storage.inmemory.components

import by.sideproject.videocaster.services.storage.base.components.FileMetaDAOComponent
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import by.sideproject.videocaster.services.storage.inmemory.dao.InmemoryFileMetaDAO

trait InmemoryFileMetaDAOComponent extends FileMetaDAOComponent {

  lazy val fileMetaDAO: FileMetaDAO = new InmemoryFileMetaDAO

}
