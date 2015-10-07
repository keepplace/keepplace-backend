package by.sideproject.videocaster.services.downloader.async

import akka.actor.ActorRef
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.util.Error
import by.sideproject.videocaster.services.downloader.base.DownloadService
import org.slf4j.LoggerFactory

class AsynchronousDownloadService(downloadServiceActor: ActorRef, syncDownloadService: DownloadService) extends DownloadService {

  protected val log = LoggerFactory.getLogger(this.getClass)

  override def getVideoDetails(url: String): Either[Error, VideoItemDetails] = {
    syncDownloadService.getVideoDetails(url)
  }

  override def download(item: VideoItemDetails, account: Identity): Unit = {
    log.debug("Sending message to download actor for processing: " + item)
    downloadServiceActor ! Tuple2(item, account)
  }

}
