package by.sideproject.instavideo.filestorage.local

import java.io.File

import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.filestorage.{FileData, FileMeta}
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory

import scala.util.Random

class LocalFileStorageService(fileMetaDao: FileMetaDAO, domain: String) extends FileStorageService {

  private val log = LoggerFactory.getLogger(this.getClass)

  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  override def upload(path: String, account: Identity): Option[FileMeta] = {

    val id = fileMetaDao.getNewId
    val fileName: String = new File(path).getName

    val meta = new FileMeta(Some(id), fileURL(id), None, fileName, "local", path)

    fileMetaDao.update(meta)

    Some(meta)
  }

  override def getData(id: Long): Option[FileData] = {
    fileMetaDao.findOneById(id).map { fileMeta =>
      log.debug("Reading data: " + fileMeta)

      import java.nio.file.{Files, Paths}
      val data = Files.readAllBytes(Paths.get(fileMeta.path))

      FileData(fileMeta, Some(data))
    }
  }

  override def remove(id: Long, identity: Identity): Unit = {
    getInfo(id).map { fileForRemoval =>
      log.debug("Removing file from the file storage")

      fileForRemoval.id.map(fileMetaDao.removeById(_))

      log.warn("TODO/ Remove file from file system")
    }
  }

  override def getInfo(id: Long): Option[FileMeta] = fileMetaDao.findOneById(id)

  protected def fileURL(id: Long) = "http://" + domain + "/data/" + id + "/download"
}
