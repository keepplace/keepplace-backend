package by.sideproject.videocaster.services.downloader.base

import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.util.Error
import by.sideproject.videocaster.model.{VideoItemDetails, VideoItemDownloadDetails}

trait DownloadService {
  def download(item: VideoItemDetails, account: Identity)
  def getVideoDetails(url: String): Either[Error, VideoItemDownloadDetails]
}
