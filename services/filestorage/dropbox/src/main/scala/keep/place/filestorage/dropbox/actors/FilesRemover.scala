package keep.place.filestorage.dropbox.actors

import akka.actor.Actor
import keep.place.model.filestorage.FileMeta
import com.dropbox.core.DbxClient
import org.slf4j.LoggerFactory

class FilesRemover extends Actor {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def receive: Receive = {
    case (client: DbxClient, fileForRemoval: FileMeta) => {
      log.debug(s"Calling Dropbox API for removing file: $fileForRemoval")
      client.delete(fileForRemoval.path)
      log.debug("File has been successfully removed from Dropbox")
    }

  }
}
