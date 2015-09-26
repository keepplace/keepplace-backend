package by.sideproject.videocaster.app.rest.routes

import spray.http.MediaType
import spray.http.MediaTypes._
import spray.routing._

trait BaseService extends HttpService {

  val json: MediaType = `application/json`
  val entityIdParameter = "\\d+".r

  def route: Route

}