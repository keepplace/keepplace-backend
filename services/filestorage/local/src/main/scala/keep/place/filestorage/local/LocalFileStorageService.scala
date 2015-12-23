package keep.place.filestorage.local

import java.util.UUID

import keep.place.filestorage.base.FileStorageService
import keep.place.model.auth.Identity
import keep.place.model.filestorage.{FileData, FileMeta}
import keep.place.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}


abstract class LocalFileStorageService(fileMetaDao: FileMetaDAO, domain: String)(implicit executionContext: ExecutionContext) extends FileStorageService {

  private val log = LoggerFactory.getLogger(this.getClass)

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

  protected def generateDownloadToken = UUID.randomUUID().toString
}
