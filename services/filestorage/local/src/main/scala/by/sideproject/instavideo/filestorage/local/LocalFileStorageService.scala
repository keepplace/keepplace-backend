package by.sideproject.instavideo.filestorage.local

import java.io.File
import java.util.UUID

import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.filestorage.{FileData, FileMeta}
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}


abstract class LocalFileStorageService(fileMetaDao: FileMetaDAO, domain: String)(implicit executionContext: ExecutionContext) extends FileStorageService {

  private val log = LoggerFactory.getLogger(this.getClass)

  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  override def upload(path: String, account: Identity): Future[Option[FileMeta]] = {

    val fileName: String = new File(path).getName

    val downloadId = generateDownloadToken
    val meta = new FileMeta(None, downloadId, fileURL(downloadId), None, fileName, "local", path)

    for {
      insertedFileMetaId <- fileMetaDao.insert(meta)
    } yield {
      Some(meta)
    }

  }

  override def getData(id: Int): Future[Option[FileData]] = {

    import java.nio.file.{Files, Paths}
    log.debug(s"Fetching data for FileID: $id")
    for {
      fileMetaOption <- fileMetaDao.findOneById(id)
    } yield {
      fileMetaOption.map { fileMeta =>
        val data = Files.readAllBytes(Paths.get(fileMeta.path))
        FileData(fileMeta, Some(data))
      }
    }
  }

  override def remove(id: Int, identity: Identity): Unit = {
    for {
      fileInfoOption <- getInfo(id)
    } yield {
      fileInfoOption.map { fileForRemoval =>
        log.debug("Removing file from the file storage")

        fileForRemoval.id.map(fileMetaDao.removeById(_))

        log.warn("TODO/ Remove file from file system")
      }
    }
  }

  override def getInfo(id: Int): Future[Option[FileMeta]] = fileMetaDao.findOneById(id)

  protected def fileURL(id: String) = domain + "/data/" + id + "/download"

  protected def generateDownloadToken = UUID.randomUUID().toString
}
