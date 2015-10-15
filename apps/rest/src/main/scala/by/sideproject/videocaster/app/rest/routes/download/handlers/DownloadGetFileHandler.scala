package by.sideproject.videocaster.app.rest.routes.download.handlers

import akka.actor.Actor
import by.sideproject.instavideo.filestorage.base.FileStorageService
import by.sideproject.videocaster.app.rest.routes.download.requests.DownloadGetFileRequest
import by.sideproject.videocaster.model.filestorage.FileData
import by.sideproject.videocaster.services.storage.base.dao.FileMetaDAO
import org.slf4j.LoggerFactory
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes._
import spray.http._
import spray.httpx.marshalling.{Marshaller, MarshallingContext}

import scala.concurrent.ExecutionContext

class DownloadGetFileHandler(binaryStorageService: FileStorageService, fileMetaDAO: FileMetaDAO)(implicit executionContext: ExecutionContext)
  extends Actor {
  val log = LoggerFactory.getLogger(this.getClass)

  implicit val FileDataMarshaller = Marshaller.of[FileData](`video/mp4`){(binaryData: FileData, contentType: ContentType, ctx: MarshallingContext) =>
      log.debug(s"Processing file download request for: ${binaryData.meta}")
      binaryData.data.map{data =>
          ctx.marshalTo(HttpData(data), RawHeader("Content-Disposition", s"attachment; filename='${binaryData.meta.name}'"))
      }
  }


  override def receive = {
    case DownloadGetFileRequest(ctx, id) => {

      fileMetaDAO.findDownloadId(id).map { fileOption =>


        fileOption.map { file =>
          file.id.map { fileMetaId =>

              binaryStorageService.getData(fileMetaId).map { data =>
                data.map { binaryData =>
                  binaryData.meta.placement match {
                    case "local" => {
                      log.debug(s"Sending binary information by fileId: ${binaryData.meta}")
                      ctx.complete(binaryData)
                    }
                    case "remote" =>
                      binaryData.meta.secondaryDownloadURL.map { reditectUrl =>
                        log.debug(s"Redirecting to actual file URL: ${binaryData.meta}")
                        ctx.redirect(reditectUrl, StatusCodes.Found)
                      }
                  }
                }
              }
          }


        }
      }

    }
  }

}
