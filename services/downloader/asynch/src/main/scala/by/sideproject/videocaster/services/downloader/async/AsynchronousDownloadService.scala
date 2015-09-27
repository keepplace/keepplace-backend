package by.sideproject.videocaster.services.downloader.async

import akka.actor.ActorRef
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.services.downloader.base.DownloadService
import org.slf4j.LoggerFactory

class AsynchronousDownloadService(downloadServiceActor: ActorRef) extends DownloadService {

  protected val log = LoggerFactory.getLogger(this.getClass)

  override def download(item: VideoItemDetails): Unit = {
    log.debug("Sending message to download actor for processing: " + item)
    downloadServiceActor ! item
  }

}
