package by.sideproject.videocaster.model.auth

import by.sideproject.videocaster.model.auth.oauth.OAuth2Info

case class DropboxIdentity(uid: String,
                    fullName: String,
                    oAuth2Info: OAuth2Info)
