package by.sideproject.videocaster.app.rest.formaters.json

import by.sideproject.videocaster.app.rest.oauth.base.{OAuth2Constants, OAuth2Info}
import spray.json._

object DropboxOAuth2InfoProtocol extends DefaultJsonProtocol {
  implicit object OAuth2InfoJsonFormat extends RootJsonFormat[OAuth2Info] {

    def write(a: OAuth2Info) = ???

    def read(value: JsValue): OAuth2Info = {

      value.asJsObject.getFields(OAuth2Constants.AccessToken, OAuth2Constants.TokenType, OAuth2Constants.IdToken) match {
        case Seq(JsString(accessToken), JsString(tokenType), JsString(id_token)) =>
          OAuth2Info(accessToken, Some(tokenType), None, Some(id_token))
        case _ => throw new DeserializationException("OAuth2Info expected")
      }
    }
  }
}


object DropboxJsonProtocol extends DefaultJsonProtocol {
  implicit val oauth2InfoFormat = DropboxOAuth2InfoProtocol.OAuth2InfoJsonFormat
}
