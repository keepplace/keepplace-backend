package by.sideproject.instavideo.filestorage.dropbox

import java.io.{File, _}
import java.util.Locale

import by.sideproject.instavideo.filestorage.local.LocalFileStorageService
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.filestorage.{FileData, FileMeta}
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import com.dropbox.core._
import org.slf4j.LoggerFactory

import scalaz.Scalaz._
import scalaz._

class DropboxFileStorageService(fileMetaDao: FileMetaDAO, domain: String) extends LocalFileStorageService(fileMetaDao, domain) {

  val log = LoggerFactory.getLogger(this.getClass)

  val config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString())

  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  override def upload(path: String, identity: Identity): Option[FileMeta] = {
    log.debug("Storing file to Dropbox account: " + path + " " + identity)

    withClient(identity) { client =>
      log.debug("With client for: " + client.getAccountInfo)

      val inputFile = new File(path)
      val inputStream = new FileInputStream(inputFile)

      val filePath = s"/${inputFile.getName}"

      val uploadedFile = client.uploadFile(filePath, DbxWriteMode.add(), inputFile.length(), inputStream)
      log.debug("Uploaded: " + uploadedFile.toString())

      val uploadedFilePath = uploadedFile.path

      val sharableUrl: String = client.createShareableUrl(uploadedFilePath)
      val id: String = Math.abs(idGenerator.nextLong()).toString

      val meta = new FileMeta(Some(id), fileURL(id), Some(sharableUrl.replace("dl=0", "dl=1")), inputFile.getName, "remote", uploadedFilePath)
      fileMetaDao.update(meta)

      inputStream.close()
      //      TODO remove files in future
      //      inputFile.delete()

      meta.some

    }

  }

  override def getData(id: String): Option[FileData] = {
    fileMetaDao.findOneById(id).map(FileData(_, None))
  }

  override def remove(id: String, identity: Identity): Unit = {
    withClient(identity) { client =>
      getInfo(id).map { fileForRemoval =>
        log.debug("Removing file from the file storage")

        client.delete(fileForRemoval.path)
        fileForRemoval.id.map(fileMetaDao.removeById(_))

        log.warn("TODO/ Remove file from file system")
      }
    }


  }

  private def withClient[T](identity: Identity)(f: (DbxClient) => Option[T]): Option[T] = {
    log.debug(s"Setting up client for identity: $identity")
    identity.oAuth2Info.flatMap { oauthInfo =>
      val client: DbxClient = new DbxClient(config, oauthInfo.accessToken)
      f(client)
    }
  }

}

