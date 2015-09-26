package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.model.rss.{PodcastChannel, PodcastItem}
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.http.MediaTypes._
import spray.http._
import spray.httpx.marshalling.Marshaller

class RssService(storageService: StorageService)(implicit context: ActorContext) extends BaseService {

  def actorRefFactory = context

  def route =
    path("rss") {
      implicit val PodcatChannelMarshaller = podcastChannelMarshaller(`application/xml`, `application/xml`)
      get {
        respondWithMediaType(`application/xml`) {
          complete {
            PodcastChannel(
              Seq(PodcastItem("title1", "description1", "http://url.com"), PodcastItem("title2", "description2", "http://url.com"))
            )
          }
        }
      }
    }


  def podcastChannelMarshaller(contentType: ContentType, more: ContentType*): Marshaller[PodcastChannel] =
    Marshaller.delegate[PodcastChannel, xml.NodeSeq](contentType +: more: _*) { (data: PodcastChannel) ⇒
      <channel>
        {podcastItemsMarshaller(data.items)}
      </channel>
    }

  def podcastItemsMarshaller(items: Seq[PodcastItem]): xml.NodeSeq =
    items.map { data =>
      <item>
        <title>
          {data.name}
        </title>
        <description>
          {data.description}
        </description>
        <enclosure url={data.downloadUrl} type="video/mpeg"/>
      </item>
    }


}
