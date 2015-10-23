package keep.place.services.storage.h2.dao

import keep.place.model.filestorage.FileMeta
import keep.place.services.storage.base.dao._
import keep.place.services.storage.h2.H2BaseDAO
import keep.place.services.storage.h2.dao.tables.FileMetaT
import slick.driver.H2Driver.api.Database
import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.{Future, ExecutionContext}


class H2FileMetaDAO
(implicit executionContext: ExecutionContext, database: Database)
  extends H2BaseDAO[FileMeta, FileMetaT](TableQuery[FileMetaT])
  with FileMetaDAO{
  override def findDownloadId(downloadId: String): Future[Option[FileMeta]] =
    database.run(tableQuery.filter(_.downloadID === downloadId).result.headOption)
}