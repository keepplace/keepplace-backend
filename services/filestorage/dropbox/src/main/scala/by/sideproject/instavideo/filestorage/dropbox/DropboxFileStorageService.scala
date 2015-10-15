package by.sideproject.instavideo.filestorage.dropbox

import java.io.{File, _}
import java.util.Locale

import akka.actor.{ActorSystem, Props}
import by.sideproject.instavideo.filestorage.dropbox.actors.FilesRemover
import by.sideproject.instavideo.filestorage.local.LocalFileStorageService
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.filestorage.{FileData, FileMeta}
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import com.dropbox.core._
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}
import scalaz.Scalaz._

class DropboxFileStorageService(fileMetaDao: FileMetaDAO, domain: String)
                               (implicit actorSystem: ActorSystem, executionContext: ExecutionContext)
  extends LocalFileStorageService(fileMetaDao, domain) {

  val log = LoggerFactory.getLogger(this.getClass)

  val config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString())

  private val fileRemover = actorSystem.actorOf(Props[FilesRemover])

  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  override def upload(path: String, identity: Identity): Future[Option[FileMeta]] = {
    log.debug("Storing file to Dropbox account: " + path + " " + identity)


    val inputFile = new File(path)
    val inputStream = new FileInputStream(inputFile)
    val filePath = s"/${inputFile.getName}"

    Future {
      withClient(identity) { client =>
        log.debug("With client for: " + client.getAccountInfo)

        val uploadedFile = client.uploadFile(filePath, DbxWriteMode.add(), inputFile.length(), inputStream)
        log.debug("Uploaded: " + uploadedFile.toString())

        val uploadedFilePath = uploadedFile.path

        val sharableUrl: String = client.createShareableUrl(uploadedFilePath)

        val downloadId = generateDownloadId

        val meta = new FileMeta(None, downloadId, fileURL(downloadId), Some(sharableUrl.replace("dl=0", "dl=1")), inputFile.getName, "remote", uploadedFilePath)
        fileMetaDao.update(meta)

        inputStream.close()
        //      TODO remove files in future
        //      inputFile.delete()

        meta.some
      }
    }

  }

  override def getData(id: Int): Future[Option[FileData]] = fileMetaDao.findOneById(id).map(_.map(FileData(_, None)))


  override def remove(id: Int, identity: Identity): Unit = {

    for {
      fileInfoOption <- getInfo(id)
    } yield {
      withClient(identity) { client =>
        fileInfoOption.map { fileForRemoval =>
          log.debug("Removing file from the file storage")

          fileRemover !(client, fileForRemoval)

          fileForRemoval.id.map(fileMetaDao.removeById)

          log.warn("TODO/ Remove file from file system")
        }
      }

    }
  }

  private def withClient[T](identity: Identity)(f: (DbxClient) => Option[T]): Option[T] = {
    log.debug(s"Setting up client for identity: $identity")
    val client: DbxClient = new DbxClient(config, identity.accessToken)
    f(client)

  }

}

