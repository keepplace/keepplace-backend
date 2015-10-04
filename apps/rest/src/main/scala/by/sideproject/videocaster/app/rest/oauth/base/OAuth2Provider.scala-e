package by.sideproject.videocaster.app.rest.oauth.base

import by.sideproject.videocaster.app.rest.formaters.json.DropboxJsonProtocol
import by.sideproject.videocaster.app.rest.oauth.base.model.Identity
import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import com.google.api.client.auth.oauth2.{AuthorizationCodeRequestUrl, AuthorizationCodeTokenRequest, ClientParametersAuthentication}
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import org.slf4j.LoggerFactory
import spray.json.JsonParser

class OAuth2Provider(providerId: String) extends IdentityProvider {

  private val log = LoggerFactory.getLogger(this.getClass)
  val settings: OAuth2Settings = OAuth2Settings.forProvider(providerId)

  def getAuthorizationCodeRequestUrl(): String = {
    val url: AuthorizationCodeRequestUrl =
      new AuthorizationCodeRequestUrl(settings.authorizationUrl, settings.clientId)
        .setRedirectUri(OauthConfig.CALLBACK_URL)
        .setScopes(settings.scope)

    url.build()
  }

  def requestAccessToken(code: String): Identity = {
    import DropboxJsonProtocol._

    val authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
      new NetHttpTransport(),
      new JacksonFactory(),
      new GenericUrl(settings.accessTokenUrl),
      code)
      .setRedirectUri(OauthConfig.CALLBACK_URL)
      .setScopes(settings.scope)
      .setClientAuthentication(new ClientParametersAuthentication(settings.clientId, settings.clientSecret))

    val tokenResponse = authorizationCodeTokenRequest.execute()
    val oauth2Info = JsonParser(tokenResponse.toString).convertTo[OAuth2Info]

    Identity(oauth2Info.IdToken.getOrElse(""), None, None, None, None, None, Some(oauth2Info))
  }


}


