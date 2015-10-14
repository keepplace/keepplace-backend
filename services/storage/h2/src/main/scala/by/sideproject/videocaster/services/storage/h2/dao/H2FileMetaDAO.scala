package by.sideproject.videocaster.services.storage.h2.dao

import by.sideproject.videocaster.model.filestorage.FileMeta
import by.sideproject.videocaster.services.storage.base.dao._
import by.sideproject.videocaster.services.storage.h2.H2BaseDAO
import by.sideproject.videocaster.services.storage.h2.dao.tables.FileMetaT
import slick.driver.H2Driver.api.Database
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext


class H2FileMetaDAO
(implicit executionContext: ExecutionContext, database: Database)
  extends H2BaseDAO[FileMeta, FileMetaT](TableQuery[FileMetaT])
  with FileMetaDAO
