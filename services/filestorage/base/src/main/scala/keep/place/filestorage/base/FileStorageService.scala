package keep.place.filestorage.base

import keep.place.model.auth.Identity
import keep.place.model.filestorage.{FileData, FileMeta}

import scala.concurrent.Future

trait FileStorageService {
  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  def upload(path: String, account: Identity): Future[Option[FileMeta]]

  def remove(id: Int, identity: Identity): Unit

  def getInfo(id: Int): Future[Option[FileMeta]]

  def getData(id: Int): Future[Option[FileData]]

}
