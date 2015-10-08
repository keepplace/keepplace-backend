package by.sideproject.videocaster.model.auth

import by.sideproject.videocaster.model.BaseObject
import by.sideproject.videocaster.model.auth.oauth.OAuth2Info

case class Identity(id: Option[Long],
                    uid: String,
                    sessionId: String,
                    firstName: Option[String],
                    lastName: Option[String],
                    fullName: Option[String],
                    email: Option[String],
                    avatarUrl: Option[String],
                    profileId: Option[Long],
                    oAuth2Info: Option[OAuth2Info] = None) extends BaseObject[Long]
