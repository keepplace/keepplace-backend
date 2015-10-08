package by.sideproject.videocaster.app.rest.formaters.json

import by.sideproject.videocaster.model.VideoItemDetails
import by.sideproject.videocaster.model.auth.{Profile, Identity}
import by.sideproject.videocaster.model.auth.oauth.OAuth2Info
import by.sideproject.videocaster.model.rss.{PodcastChannel, PodcastItem}
import by.sideproject.videocaster.model.util.Error
import by.sideproject.videocaster.model.video.AddVideoRequest
import spray.json.{CollectionFormats, DefaultJsonProtocol}

object InstaVideoJsonProtocol extends DefaultJsonProtocol with CollectionFormats {

  implicit val podcastItemFormat = jsonFormat6(PodcastItem)
  implicit val podcastChannelFormat = jsonFormat1(PodcastChannel)

  implicit val videoItemDetailsFormat = jsonFormat10(VideoItemDetails)
  implicit val addVideoRequestFormat = jsonFormat1(AddVideoRequest)
  implicit val oAuth2InfoFormat = jsonFormat5(OAuth2Info)
  implicit val identityFormat = jsonFormat10(Identity)
  implicit val profileFormat = jsonFormat4(Profile)
  implicit val errorFormat = jsonFormat2(Error)

}
