package by.sideproject.videocaster.services.downloader.sync

import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.youtubedl.YoutubeDL
import org.slf4j.LoggerFactory


class SynchronusDownloadService(youDl: YoutubeDL, storage: StorageService, binaryStorageService: FileStorageService)
  extends DownloadService {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def getVideoDetails(url: String): VideoItemDetails = {
    log.debug("Fetching information on video file: " + url)
    val videoInfo = youDl.getInfo(url)
    log.debug("Information on video item has been retrieved: " + videoInfo)
    VideoItemDetails(None, Some(videoInfo.title), Some(videoInfo.description), None, url, "today", "info", Some(videoInfo.pubDate), Some(videoInfo.author))
  }

  override def download(item: VideoItemDetails, account: Identity): Unit = {

    log.debug("Starting to download video file: " + item)
    val downloadInfo = youDl.download(item.originURL)
    log.debug("Video item has been downloaded: " + downloadInfo)

    log.debug("Storing data in local file storage: " + downloadInfo.fileName)
    val storedDataOption = binaryStorageService.upload(downloadInfo.fileName, account)

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
