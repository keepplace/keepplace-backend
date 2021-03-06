package keep.place.services.downloader.sync

import keep.place.filestorage.base.FileStorageService
import keep.place.model.auth.Identity
import keep.place.model.util.Error
import keep.place.model.{VideoItemDetails, VideoItemDownloadDetails}
import keep.place.services.downloader.base.DownloadService
import keep.place.services.storage.base.StorageService
import keep.place.services.youtubedl.YoutubeDL
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext


class SynchronusDownloadService(youDl: YoutubeDL, storage: StorageService, binaryStorageService: FileStorageService)
                               (implicit executionContext: ExecutionContext)
  extends DownloadService {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def getVideoDetails(url: String): Either[Error, VideoItemDownloadDetails] = {
    log.debug("Fetching information on video file: " + url)
    val videoInfoResult = youDl.getInfo(url)
    videoInfoResult match {
      case Right(videoInfo) => {
        log.debug("Information on video item has been retrieved: " + videoInfo)
        Right(VideoItemDownloadDetails(videoInfo.title, videoInfo.description, url, videoInfo.pubDate, videoInfo.author, videoInfo.thumbnail ))
      }
      case Left(error) => Left(error)
    }
  }

  override def download(item: VideoItemDetails, account: Identity): Unit = {

    log.debug("Starting to download video file: " + item)
    val downloadInfoResult = youDl.download(item.originURL)
    log.debug("Video item has been downloaded: " + downloadInfoResult)

    downloadInfoResult match {
      case Right(downloadInfo) => {
        log.debug("Storing data in local file storage: " + downloadInfo.fileName)
        for {
          storedDataOption <- binaryStorageService.upload(downloadInfo.fileName, account)
        } yield {
          storedDataOption.map { storedData =>
            val updatedVideItemDetails = item.copy(
              title = Some(downloadInfo.title),
              description = Some(downloadInfo.description),
              author = Some(downloadInfo.author),
              pubDate = Some(downloadInfo.pubDate),
              fileMetaId = storedData.id,
              status = "downloaded"
            )
            log.debug("Updating an information about video file: " + updatedVideItemDetails)

            storage.videoItemDetailsDAO.update(updatedVideItemDetails)
          }
        }
      }
      case Left(error) => {
        val errorMessage: String = s"Video couldn't be downloaded due to error: ${error.message}"
        log.debug(errorMessage)
        storage.videoItemDetailsDAO.update(item.copy(status = "error", note = Some(errorMessage)))
      }
    }
  }
}
