package by.sideproject.videocaster.app.rest.actors

import akka.actor.Actor
import by.sideproject.videocaster.app.rest.routes.{RssService, VideoService}
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.routing.HttpService

class AppActor(storageService: StorageService) extends Actor with HttpService {

  def actorRefFactory = context

  def receive = runRoute(new RssService(storageService).route ~ new VideoService(storageService).route)

}
