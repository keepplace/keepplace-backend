package by.sideproject.videocaster.services.downloader.base

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.downloads.DownloadedFileInfo
import by.sideproject.videocaster.model.util.Error

trait DownloadService {
  def download(item: VideoItemDetails, account: Identity)
  def getVideoDetails(url: String): Either[Error, VideoItemDetails]
}
