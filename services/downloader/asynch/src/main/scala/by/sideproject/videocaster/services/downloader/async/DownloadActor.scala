package by.sideproject.videocaster.services.downloader.async

import akka.actor.Actor
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.services.downloader.base.DownloadService
import org.slf4j.LoggerFactory

class DownloadActor(downloadService: DownloadService) extends Actor {
  protected val log = LoggerFactory.getLogger(this.getClass)

  override def receive: Receive = {
    case (item: VideoItemDetails, account: Identity) => {
      log.debug(s"Downloading video $item for $account")
      downloadService.download(item, account)
    }
    case _ => log.info("received unknown message")
  }

}
