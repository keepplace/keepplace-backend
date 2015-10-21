package keep.place.app.rest.oauth.base.utils

import com.typesafe.config.ConfigFactory
import java.util

object OauthConfig {

  val CALLBACK_URL = ConfigFactory.load().getString("spray-oauth2-client.callbackUrl")
  val SESSION_NAME = ConfigFactory.load().getString("spray-oauth2-client.sessionName")
  val CALLBACK_ROUTE = ConfigFactory.load().getString("spray-oauth2-client.callbackRoute")
  val REDIRECT_ROUTE = ConfigFactory.load().getString("spray-oauth2-client.redirectRoute")
  val ON_LOGIN_GO_TO = ConfigFactory.load().getString("spray-oauth2-client.onLoginGoTo")



  def loadProperty(providerId:String, property:String) : String = {
    val key = s"spray-oauth2-client.$providerId.$property"
    ConfigFactory.load().getString(key)
  }

  def loadProperty(property:String) : String = {
    val key = s"spray-oauth2-client.$property"
    ConfigFactory.load().getString(key)
  }

  //TODO : Find a better solution loading lists
  def loadPropertyOfScope(providerId:String, property:String) : util.List[String] = {
    val key = s"spray-oauth2-client.$providerId.$property"
    ConfigFactory.load().getStringList(key)
  }

}
