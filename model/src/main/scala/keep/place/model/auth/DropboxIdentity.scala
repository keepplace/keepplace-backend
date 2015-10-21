package keep.place.model.auth

import keep.place.model.auth.oauth.OAuth2Info

case class DropboxIdentity(uid: String,
                    fullName: String,
                    oAuth2Info: OAuth2Info)
