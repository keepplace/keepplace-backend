package by.sideproject.videocaster.services.downloader.base

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.Identity

trait DownloadService {
  def download(item: VideoItemDetails, account: Identity)
  def getVideoDetails(url: String): VideoItemDetails
}
