package by.sideproject.videocaster.app.rest.oauth.base

import by.sideproject.videocaster.app.rest.oauth.base.model.Identity

trait IdentityProvider{
  def getAuthorizationCodeRequestUrl() :String
  def requestAccessToken(code: String) : Identity
}

