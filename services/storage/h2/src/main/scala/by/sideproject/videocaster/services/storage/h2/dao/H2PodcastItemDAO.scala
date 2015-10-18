package by.sideproject.videocaster.services.storage.h2.dao

import by.sideproject.videocaster.model.rss.PodcastItem
import by.sideproject.videocaster.services.storage.base.dao._
import slick.driver.H2Driver.api.{Database, _}

import scala.concurrent.{ExecutionContext, Future}


class H2PodcastItemDAO
(implicit executionContext: ExecutionContext,
 database: Database,
 videoItemDetailsDAO: H2VideoItemDetailsDAO,
 fileMetaDAO: H2FileMetaDAO)
  extends PodcastItemDAO {

  override def fetchPodcastItems(profileId: Int): Future[Seq[PodcastItem]] = {

   val podcastItemAttrs=  for {
      (details, file) <- videoItemDetailsDAO.tableQuery join fileMetaDAO.tableQuery on (_.fileMetaId === _.id) if details.status === "downloaded" && details.profileId === profileId
    } yield (details.id, details.title, details.description, details.author, details.pubDate, file.secondaryDownloadURL)

    database.run(podcastItemAttrs.result).map{items =>
      items.map{
        case (id, title, description, author, pubDate, downlodadURL) => PodcastItem(id,title,description,author,pubDate,downlodadURL)
      }
    }

  }



}
