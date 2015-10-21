package keep.place.app.rest.oauth.dropbox

import java.util.{Locale, UUID}

import keep.place.app.rest.formaters.json.DropboxJsonProtocol
import keep.place.app.rest.oauth.base.OAuth2Provider
import keep.place.app.rest.oauth.base.utils.OauthConfig
import keep.place.model.auth.{DropboxIdentity, Profile, Identity}
import keep.place.model.auth.oauth.OAuth2Info
import com.dropbox.core.{DbxRequestConfig, DbxClient}
import com.google.api.client.auth.oauth2.{AuthorizationCodeRequestUrl, AuthorizationCodeTokenRequest, ClientParametersAuthentication}
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import spray.json.JsonParser


class DropboxProvider(providerId: String = DropboxProvider.Dropbox) extends OAuth2Provider(providerId){

  private val config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString())

  override def getAuthorizationCodeRequestUrl(): String = {
    val url: AuthorizationCodeRequestUrl =
      new AuthorizationCodeRequestUrl(settings.authorizationUrl, settings.clientId)
        .setRedirectUri(OauthConfig.CALLBACK_URL)
    url.build()
  }


  override def requestAccessToken(code: String): DropboxIdentity = {
    import DropboxJsonProtocol._

    val authorizationCodeTokenRequest = new AuthorizationCodeTokenRequest(
      new NetHttpTransport(),
      new JacksonFactory(),
      new GenericUrl(settings.accessTokenUrl), code)
      .setRedirectUri(OauthConfig.CALLBACK_URL)
      .setClientAuthentication(new ClientParametersAuthentication(settings.clientId, settings.clientSecret))


    val tokenResponse = authorizationCodeTokenRequest.execute()
    val oauth2Info = JsonParser(tokenResponse.toString).convertTo[OAuth2Info]


    val client: DbxClient = new DbxClient(config, oauth2Info.accessToken)
    val accountInfo = client.getAccountInfo()

    val sessionId = UUID.randomUUID().toString
    // TODO It worse to get rid of getOrElse
    DropboxIdentity(oauth2Info.IdToken.getOrElse(""), accountInfo.displayName, oauth2Info)
  }

}

object DropboxProvider {
  val Dropbox = "dropbox"
}
