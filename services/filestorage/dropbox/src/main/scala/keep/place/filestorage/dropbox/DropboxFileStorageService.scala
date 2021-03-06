package keep.place.filestorage.dropbox

import java.io.{File, _}
import java.util.Locale

import akka.actor.{ActorSystem, Props}
import keep.place.filestorage.dropbox.actors.FilesRemover
import keep.place.filestorage.local.LocalFileStorageService
import keep.place.model.auth.Identity
import keep.place.model.filestorage.{FileData, FileMeta}
import keep.place.services.storage.base.dao.FileMetaDAO
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


    withClient(identity) { client =>
      log.debug("With client for: " + client.getAccountInfo)

      val uploadedFile = client.uploadFile(filePath, DbxWriteMode.add(), inputFile.length(), inputStream)
      inputStream.close()


      log.debug("Uploaded: " + uploadedFile.toString())

      val uploadedFilePath = uploadedFile.path

      val sharableUrl: String = client.createShareableUrl(uploadedFilePath)

      val fileName: String = inputFile.getName
      inputFile.delete()

      val meta = new FileMeta(None, sharableUrl.replace("dl=0", "dl=1"), fileName, "remote", uploadedFilePath)
      fileMetaDao.insert(meta).map(generatedId => meta.copy(id = generatedId.some).some)

    }

  }

  override def getData(id: Int): Future[Option[FileData]] = fileMetaDao.findOneById(id).map(_.map(FileData(_, None)))


  override def remove(id: Int, identity: Identity) = {

    for {
      fileInfoOption <- getInfo(id)

    } yield {
      fileInfoOption.foreach { fileForRemoval =>
        withClient(identity) { client =>

          log.debug("Removing file from the file storage")

          fileRemover !(client, fileForRemoval)

          fileForRemoval.id.map(fileMetaDao.removeById)
        }
      }

    }
  }

  private def withClient[T](identity: Identity)(f: (DbxClient) => T): T = {
    log.debug(s"Setting up client for identity: $identity")
    val client: DbxClient = new DbxClient(config, identity.accessToken)
    f(client)

  }

}

