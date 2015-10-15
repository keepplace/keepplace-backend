package by.sideproject.videocaster.services.storage.h2.dao

import by.sideproject.videocaster.model.rss.PodcastItem
import by.sideproject.videocaster.services.storage.base.dao._
import by.sideproject.videocaster.services.storage.h2.dao.tables.VideoItemDetailsT
import slick.driver.H2Driver.api.Database
import slick.driver.H2Driver.api._
import slick.lifted.TableQuery

import scala.concurrent.{Future, ExecutionContext}


class H2PodcastItemDAO
(implicit executionContext: ExecutionContext,
 database: Database,
 videoItemDetailsDAO: H2VideoItemDetailsDAO,
 fileMetaDAO: H2FileMetaDAO)
  extends PodcastItemDAO {

  override def fetchPodcastItems(profileId: Int): Future[Seq[PodcastItem]] = {

   val podcastItemAttrs=  for {
      (videoItem, fileMeta) <- videoItemDetailsDAO.tableQuery join fileMetaDAO.tableQuery on (_.fileMetaId === _.id) filter(_._1.status === "downloaded")

    } yield (videoItem.id, videoItem.title, videoItem.description, videoItem.author, videoItem.pubDate, fileMeta.downloadURL)

    database.run(podcastItemAttrs.result).map{items =>
      items.map{
        case (id, title, description, author, pubDate, downlodadURL) => PodcastItem(id,title,description,author,pubDate,downlodadURL)
      }
    }

  }



}
