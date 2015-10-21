package keep.place.app.rest.formaters.json

import keep.place.model.VideoItemDetails
import keep.place.model.auth.{Profile, Identity}
import keep.place.model.auth.oauth.OAuth2Info
import keep.place.model.rss.{PodcastChannel, PodcastItem}
import keep.place.model.util.Error
import keep.place.model.video.AddVideoRequest
import spray.json.{CollectionFormats, DefaultJsonProtocol}

object InstaVideoJsonProtocol extends DefaultJsonProtocol with CollectionFormats {

  implicit val podcastItemFormat = jsonFormat6(PodcastItem)
  implicit val podcastChannelFormat = jsonFormat1(PodcastChannel)

  implicit val videoItemDetailsFormat = jsonFormat11(VideoItemDetails)
  implicit val addVideoRequestFormat = jsonFormat1(AddVideoRequest)
  implicit val oAuth2InfoFormat = jsonFormat5(OAuth2Info)
  implicit val identityFormat = jsonFormat4(Identity)
  implicit val profileFormat = jsonFormat5(Profile)
  implicit val errorFormat = jsonFormat2(Error)

}
