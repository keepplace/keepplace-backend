package keep.place.app.rest.oauth.base

abstract class OAuth2Provider(providerId: String) extends IdentityProvider {

  val settings: OAuth2Settings = OAuth2Settings.forProvider(providerId)

}


