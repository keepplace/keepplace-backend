package by.sideproject.instavideo.filestorage.local

import java.io.File

import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.filestorage.{FileData, FileMeta}
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory

import scala.util.Random

class LocalFileStorageService(fileMetaDao: FileMetaDAO) extends FileStorageService {

  val log = LoggerFactory.getLogger(this.getClass)

  private val idGenerator = new Random()

  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  override def upload(path: String): Option[FileMeta] = {

    val id: String = Math.abs(idGenerator.nextLong()).toString
    val fileName: String = new File(path).getName

    val meta = new FileMeta(Some(id), fileURL(id), fileName, path)

    fileMetaDao.update(meta)

    Some(meta)
  }

  override def getData(id: String): Option[FileData] = {
    fileMetaDao.findOneById(id).map { fileMeta =>
      log.debug("Reading data: " + fileMeta)

      import java.nio.file.{Files, Paths}
      val data = Files.readAllBytes(Paths.get(fileMeta.path))


      FileData(fileMeta, data)
    }
  }

  override def remove(id: String): Unit = fileMetaDao.removeById(id)

  override def getInfo(id: String): Option[FileMeta] = fileMetaDao.findOneById(id)

  private def fileURL(id: String) = "http://localhost:8080/data/" + id + "/download"
}
