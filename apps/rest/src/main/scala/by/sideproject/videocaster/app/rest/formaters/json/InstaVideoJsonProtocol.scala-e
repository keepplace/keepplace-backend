package by.sideproject.videocaster.app.rest.formaters.json

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.rss.{PodcastChannel, PodcastItem}
import by.sideproject.videocaster.model.video.AddVideoRequest
import spray.json.{CollectionFormats, DefaultJsonProtocol}

object InstaVideoJsonProtocol extends DefaultJsonProtocol with CollectionFormats {


  implicit val podcastItemFormat = jsonFormat3(PodcastItem)
  implicit val podcastChannelFormat = jsonFormat1(PodcastChannel)

  implicit val videoItemDetailsFormat = jsonFormat6(VideoItemDetails)
  implicit val addVideoRequestFormat = jsonFormat1(AddVideoRequest)

}
