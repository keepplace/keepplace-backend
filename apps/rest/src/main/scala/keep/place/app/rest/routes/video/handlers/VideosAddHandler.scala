package keep.place.app.rest.routes.video.handlers

import java.text.SimpleDateFormat
import java.util.Calendar

import akka.actor.Actor
import keep.place.app.rest.routes.video.requests.VideosAddRequest
import keep.place.model.auth.Identity
import keep.place.model.{VideoItemDetails, VideoItemDownloadDetails}
import keep.place.services.downloader.base.DownloadService
import keep.place.services.storage.base.dao.VideoItemDetailsDAO
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scalaz.Scalaz._

class VideosAddHandler(videoDetailsDAO: VideoItemDetailsDAO, downloadService: DownloadService)
                      (implicit executionContext: ExecutionContext) extends Actor {

  import keep.place.app.rest.formaters.json.InstaVideoJsonProtocol._
  import spray.httpx.SprayJsonSupport.sprayJsonMarshaller

  val log = LoggerFactory.getLogger(this.getClass)

  val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

  def currentDateTime = dateFormat.format(Calendar.getInstance().getTime())




  implicit def videoItemDownloadDetailsToVideoItemDetails(downloadDetails: VideoItemDownloadDetails)(implicit identity: Identity) : VideoItemDetails =
    VideoItemDetails(None, downloadDetails.title.some, downloadDetails.description.some, None, downloadDetails.ur, currentDateTime,
      "info", downloadDetails.pubDate.some, downloadDetails.author.some, None, downloadDetails.thumbnail, identity.profileId)

  override def receive = {
    case VideosAddRequest(ctx, request, user) => {
      log.debug(s"Processing add video request: $request")

      val videoDetailsResponse = downloadService.getVideoDetails(request.baseUrl)

      videoDetailsResponse.fold(
        error => ctx.complete(error),
        videoItemDetails => {

          log.debug("Saving newly created video item: " + videoItemDetails)

          implicit val identity = user
          val insertionResult = for {
            videoItemID <- videoDetailsDAO.insert(videoItemDetails)
            addedVideoItemEntry <- videoDetailsDAO.findOneById(videoItemID)
          } yield {
            log.debug("Initiating download of video file for: " + videoItemDetails)
            addedVideoItemEntry.map(downloadService.download(_, user))

            addedVideoItemEntry
          }

          ctx.complete(insertionResult)
        })


    }
  }
}
