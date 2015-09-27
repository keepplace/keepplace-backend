package by.sideproject.videocaster.services.downloader.async

import akka.actor.Actor
import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.services.downloader.base.DownloadService
import org.slf4j.LoggerFactory

class DownloadActor(downloadService: DownloadService) extends Actor {
  protected val log = LoggerFactory.getLogger(this.getClass)

  override def receive: Receive = {
    case item: VideoItemDetails => {
      println(downloadService)
      println(item)
      downloadService.download(item)
    }
    case _ => log.info("received unknown message")
  }

}
