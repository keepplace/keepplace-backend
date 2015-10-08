package by.sideproject.videocaster.app.rest.routes

import spray.http.MediaType
import spray.http.MediaTypes._
import spray.routing._

trait BaseAPI extends HttpService {

  val json: MediaType = `application/json`
  val text: MediaType = `text/plain`
  val mp4: MediaType = `video/mp4`
  val xml: MediaType = `application/xml`
  val entityIdParameter = IntNumberR

  def route: Route

}
