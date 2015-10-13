package by.sideproject.videocaster.app.rest.oauth.base

import by.sideproject.videocaster.model.auth.{DropboxIdentity, Identity}

trait IdentityProvider{
  def getAuthorizationCodeRequestUrl() :String
  def requestAccessToken(code: String) : DropboxIdentity
}

