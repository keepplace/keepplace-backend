package by.sideproject.videocaster.services.downloader.sync

import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.services.downloader.base.DownloadService
import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.youtubedl.YoutubeDL
import org.slf4j.LoggerFactory


class SynchronusDownloadService(youDl: YoutubeDL, storage: StorageService, binaryStorageService: FileStorageService) extends DownloadService {
  private val log = LoggerFactory.getLogger(this.getClass)


  override def download(item: VideoItemDetails): Unit = {

    log.debug("Starting to download video file: " + item)
    val downloadInfo = youDl.download(item.originURL)
    log.debug("Video item has been downloaded: " + downloadInfo)

    log.debug("Storing data in local file storage: " + downloadInfo.fileName)
    val storedDataOption = binaryStorageService.upload(downloadInfo.fileName)



    storedDataOption.map { storedData =>
      val updatedVideItemDetails = item.copy(fileMetaId = storedData.id, status = "downloaded")
      log.debug("Updating an information about video file: " + updatedVideItemDetails)

      storage.videoItemDetailsDAO.update(updatedVideItemDetails)
    }
  }
}
