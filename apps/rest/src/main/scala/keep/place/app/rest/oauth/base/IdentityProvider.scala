package keep.place.app.rest.oauth.base

import keep.place.model.auth.{DropboxIdentity, Identity}

trait IdentityProvider{
  def getAuthorizationCodeRequestUrl() :String
  def requestAccessToken(code: String) : DropboxIdentity
}

