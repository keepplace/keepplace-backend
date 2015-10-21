package keep.place.services.downloader.async

import akka.actor.ActorRef
import keep.place.model.auth.Identity
import keep.place.model.util.Error
import keep.place.model.{VideoItemDetails, VideoItemDownloadDetails}
import keep.place.services.downloader.base.DownloadService
import org.slf4j.LoggerFactory

class AsynchronousDownloadService(downloadServiceActor: ActorRef, syncDownloadService: DownloadService) extends DownloadService {

  protected val log = LoggerFactory.getLogger(this.getClass)

  override def getVideoDetails(url: String): Either[Error, VideoItemDownloadDetails] = {
    syncDownloadService.getVideoDetails(url)
  }

  override def download(item: VideoItemDetails, account: Identity): Unit = {
    log.debug("Sending message to download actor for processing: " + item)
    downloadServiceActor ! Tuple2(item, account)
  }

}
