package keep.place.services.storage.h2.dao

import keep.place.model.rss.PodcastItem
import keep.place.model.video.VideoItemDetailsDTO
import keep.place.services.storage.base.dao._
import slick.driver.H2Driver.api.{Database, _}

import scala.concurrent.{ExecutionContext, Future}


class H2VideoItemDtoDAO
(implicit executionContext: ExecutionContext,
 database: Database,
 videoItemDetailsDAO: H2VideoItemDetailsDAO,
 fileMetaDAO: H2FileMetaDAO)
  extends VideoItemDtoDAO {

  override def findAllByProfileId(profileId: Int): Future[Seq[VideoItemDetailsDTO]] = {

    val podcastItemAttrs = for {
      (details, file) <- videoItemDetailsDAO.tableQuery join fileMetaDAO.tableQuery on (_.fileMetaId === _.id) if details.profileId === profileId
    } yield (details.id, details.title.?, details.description.?, details.originUrl, details.addDate, details.status, details.author.?, details.pubDate.?, file.downloadURL.?, details.thumbnail.?)

    database.run(podcastItemAttrs.result).map{items =>
      items.map{
        case (id, title, description, originUrl, addDate,status, author, pubDate, downlodadURL, thumbnail) => VideoItemDetailsDTO(id, title, description, originUrl, addDate, status,pubDate, author,  thumbnail, downlodadURL)
      }
    }

  }
  override def fetchPodcastItems(profileId: Int): Future[Seq[PodcastItem]] = {

   val podcastItemAttrs=  for {
      (details, file) <- videoItemDetailsDAO.tableQuery join fileMetaDAO.tableQuery on (_.fileMetaId === _.id) if details.status === "downloaded" && details.profileId === profileId
   } yield (details.id, details.title, details.description, details.author, details.pubDate, file.downloadURL, details.thumbnail.?)

    database.run(podcastItemAttrs.result).map{items =>
      items.map{
        case (id, title, description, author, pubDate, downlodadURL, thumbnail) => PodcastItem(id, title, description, author, pubDate, downlodadURL, thumbnail)
      }
    }

  }



}
