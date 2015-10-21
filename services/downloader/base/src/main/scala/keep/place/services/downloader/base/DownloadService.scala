package keep.place.services.downloader.base

import keep.place.model.auth.Identity
import keep.place.model.util.Error
import keep.place.model.{VideoItemDetails, VideoItemDownloadDetails}

trait DownloadService {
  def download(item: VideoItemDetails, account: Identity)
  def getVideoDetails(url: String): Either[Error, VideoItemDownloadDetails]
}
