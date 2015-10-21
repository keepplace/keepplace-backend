package keep.place.model.auth

import keep.place.model.BaseObject
import keep.place.model.auth.oauth.OAuth2Info

case class Identity(id: Option[Int],
                    sessionId: String,
                    profileId: Int,
                    accessToken: String) extends BaseObject[Int]
