package by.sideproject.instavideo.filestorage.dropbox

import java.io._
import java.util.Locale

import by.sideproject.videocaster.model.auth.Identity
import com.dropbox.core._

import java.io.File

import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.filestorage.{FileData, FileMeta}
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory

import scala.util.Random

class DropboxFileStorageService(fileMetaDao: FileMetaDAO) extends FileStorageService {

  val log = LoggerFactory.getLogger(this.getClass)

  val config: DbxRequestConfig = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString())


  private val idGenerator = new Random()

  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  override def upload(path: String, account: Identity): Option[FileMeta] = {
    log.debug("Storing file to Drobox account: " + path + " " + account)
    account.oAuth2Info.map { oauthInfo =>
      val accessToken: String = oauthInfo.accessToken

      val client: DbxClient = new DbxClient(config, accessToken)

      val inputFile: File = new File(path)
      val inputStream: FileInputStream = new FileInputStream(inputFile)

      val uploadedFilePath: String = s"/${inputFile.getName}"

      val uploadedFile: DbxEntry.File = client.uploadFile(uploadedFilePath, DbxWriteMode.add(), inputFile.length(), inputStream)
      log.debug("Uploaded: " + uploadedFile.toString())

      val sharableUrl: String = client.createShareableUrl(uploadedFilePath)
      val id: String = Math.abs(idGenerator.nextLong()).toString

      val meta = new FileMeta(Some(id), sharableUrl.replace("dl=0", "dl=1"), inputFile.getName, uploadedFilePath)
      fileMetaDao.update(meta)

      inputStream.close()
//      TODO remove files in future
//      inputFile.delete()

      meta

    }
  }

  override def getData(id: String): Option[FileData] = None

  override def remove(id: String): Unit = {
    getInfo(id).map { fileForRemoval =>
      log.debug("Removing file from the file storage")

      fileForRemoval.id.map(fileMetaDao.removeById(_))

      log.warn("TODO/ Remove file from file system")
    }
  }

  override def getInfo(id: String): Option[FileMeta] = fileMetaDao.findOneById(id)

}

