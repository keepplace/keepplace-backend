package by.sideproject.videocaster.app.rest

import akka.actor.{Props, ActorSystem}
import akka.io.IO

import akka.pattern.ask
import akka.util.Timeout
import by.sideproject.videocaster.app.rest.actors.AppActor
import by.sideproject.videocaster.services.storage.base.StorageService
import by.sideproject.videocaster.services.storage.inmemory.InmemoryStorageService
import spray.can.Http

import scala.concurrent.duration._


class ApplicationKernel extends akka.kernel.Bootable {

  implicit val actorSystem = ActorSystem("instacaster")
  implicit val storageService: StorageService = InmemoryStorageService
  implicit val timeout = Timeout(5.seconds)


  override def startup(): Unit = {
    val service = actorSystem.actorOf(Props(new AppActor(storageService)))
    IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
  }

  override def shutdown(): Unit = {
    /*nothing to do*/
  }
}

object Boot extends App {
  private val kernel = new ApplicationKernel
  kernel.startup()
}
