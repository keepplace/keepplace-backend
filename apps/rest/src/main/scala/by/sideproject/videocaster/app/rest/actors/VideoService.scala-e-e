package by.sideproject.videocaster.app.rest.actors

import akka.actor.Actor
import by.sideproject.videocaster.app.rest.routes.{RssService, VideoService}
import spray.routing.HttpService

class AppActor extends Actor with HttpService {

  def actorRefFactory = context

  def receive = runRoute(new RssService().route ~ new VideoService().route)

}
