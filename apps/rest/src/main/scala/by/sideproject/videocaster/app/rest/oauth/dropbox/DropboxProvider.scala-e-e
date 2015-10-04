package by.sideproject.videocaster.app.rest.oauth.dropbox

import by.sideproject.videocaster.app.rest.formaters.json.DropboxJsonProtocol
import by.sideproject.videocaster.app.rest.oauth.base.model.Identity
import by.sideproject.videocaster.app.rest.oauth.base.{OAuth2Info, OAuth2Provider}
import by.sideproject.videocaster.app.rest.oauth.base.utils.OauthConfig
import com.google.api.client.auth.oauth2.{AuthorizationCodeRequestUrl, AuthorizationCodeTokenRequest, ClientParametersAuthentication}
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import spray.json.JsonParser


class DropboxProvider(providerId: String = DropboxProvider.Dropbox) extends OAuth2Provider(providerId){
  override def getAuthorizationCodeRequestUrl(): String = {
    val url: AuthorizationCodeRequestUrl =
      new AuthorizationCodeRequestUrl(settings.authorizationUrl, settings.clientId)
        .setRedirectUri(OauthConfig.CALLBACK_URL)
    url.build()
  }


  override def requestAccessToken(code: String): Identity = {
    import DropboxJsonProtocol._

    val authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
      new NetHttpTransport(),
      new JacksonFactory(),
      new GenericUrl(settings.accessTokenUrl), code)
      .setRedirectUri(OauthConfig.CALLBACK_URL)
      .setClientAuthentication(new ClientParametersAuthentication(settings.clientId, settings.clientSecret))


    val tokenResponse = authorizationCodeTokenRequest.execute()
    val oauth2Info = JsonParser(tokenResponse.toString).convertTo[OAuth2Info]

    // todo request user info
    Identity(oauth2Info.IdToken.getOrElse(""), None, None, None, None, None, Some(oauth2Info))
  }

}

object DropboxProvider {
  val Dropbox = "dropbox"
}
