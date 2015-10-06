package by.sideproject.videocaster.app.rest.formaters.json

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.Identity
import by.sideproject.videocaster.model.auth.oauth.OAuth2Info
import by.sideproject.videocaster.model.rss.{PodcastChannel, PodcastItem}
import by.sideproject.videocaster.model.video.AddVideoRequest
import spray.json.{CollectionFormats, DefaultJsonProtocol}

object InstaVideoJsonProtocol extends DefaultJsonProtocol with CollectionFormats {


  implicit val podcastItemFormat = jsonFormat6(PodcastItem)
  implicit val podcastChannelFormat = jsonFormat1(PodcastChannel)

  implicit val videoItemDetailsFormat = jsonFormat9(VideoItemDetails)
  implicit val addVideoRequestFormat = jsonFormat1(AddVideoRequest)
  implicit val oAuth2InfoFormat = jsonFormat5(OAuth2Info)
  implicit val identityFormat = jsonFormat8(Identity)

}
