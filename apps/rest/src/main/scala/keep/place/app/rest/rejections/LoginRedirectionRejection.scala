package keep.place.app.rest.rejections

import spray.routing.Rejection

case class LoginRedirectionRejection(resolution: String) extends Rejection
