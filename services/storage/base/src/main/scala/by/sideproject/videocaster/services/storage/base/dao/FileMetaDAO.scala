package by.sideproject.videocaster.services.storage.base.dao

import by.sideproject.videocaster.model.filestorage.FileMeta

import scala.concurrent.Future

trait FileMetaDAO extends BaseDAO[FileMeta, Int]{
  def findDownloadId(id: String): Future[Option[FileMeta]]
}
