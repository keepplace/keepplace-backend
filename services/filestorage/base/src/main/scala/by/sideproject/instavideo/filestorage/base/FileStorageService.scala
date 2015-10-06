package by.sideproject.instavideo.filestorage.base

import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.filestorage.{FileMeta, FileData}

trait FileStorageService {
  /**
   * 1) Takes file on filesystem.
   * 2) Stores it in internal storage.
   * 3) Removes original file.
   * @param path - path to the file on file system.
   * @return unique identifier of the file
   */
  def upload(path: String, account: Identity): Option[FileMeta]

  def remove(id: String): Unit

  def getInfo(id: String): Option[FileMeta]

  def getData(id: String): Option[FileData]

}
