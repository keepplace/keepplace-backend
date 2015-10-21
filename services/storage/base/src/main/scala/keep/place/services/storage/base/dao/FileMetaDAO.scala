package keep.place.services.storage.base.dao

import keep.place.model.filestorage.FileMeta

import scala.concurrent.Future

trait FileMetaDAO extends BaseDAO[FileMeta, Int]{
  def findDownloadId(id: String): Future[Option[FileMeta]]
}
