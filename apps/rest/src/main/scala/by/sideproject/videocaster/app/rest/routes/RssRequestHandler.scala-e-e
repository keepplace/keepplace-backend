package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.app.rest.oauth.dropbox.DropboxAuthService
import by.sideproject.videocaster.model.rss.{PodcastChannel, PodcastItem}
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.http._
import spray.httpx.marshalling.Marshaller

import scala.xml.Elem

class RssRequestHandler(storageService: StorageService, domain: String)
                       (implicit context: ActorContext)
  extends DropboxAuthService(storageService.identityDAO, storageService.profileDAO)
  with BaseAPI {

  def route =

    path("rss") {
      authenticate(cookieAuth) { user =>
        implicit val PodcatChannelMarshaller = podcastChannelMarshaller(xml, xml)
        pathEnd {
          get {
            respondWithMediaType(xml) {
              complete {
                PodcastChannel(getPodcastItems)
              }
            }
          }
        }
      }
    }


  private def getPodcastItems: Vector[PodcastItem] = {
    storageService
      .videoItemDetailsDAO
      .findAll()
      .filter(_.isDownloaded)
      .flatMap(item =>
      item.fileMetaId.flatMap(binaryFileId =>
        storageService.fileMetaDAO.findOneById(binaryFileId).map(fileMeta =>
          PodcastItem(
            item.id.getOrElse(-1),
            item.title.getOrElse(""),
            item.description.getOrElse(""),
            item.author.getOrElse(""),
            item.pubDate.getOrElse(""),
            fileMeta.downloadURL)
        )
      )
      )
  }

  private def podcastChannelMarshaller(contentType: ContentType, more: ContentType*): Marshaller[PodcastChannel] =
    Marshaller.delegate[PodcastChannel, Elem](contentType +: more: _*) { (data: PodcastChannel) ⇒
      <rss xmlns:itunes="http://www.itunes.com/dtds/podcast-1.0.dtd" xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
        <channel>
          <atom:link href={rssUrl} type="application/rss+xml" rel="self"></atom:link>
          <itunes:owner>
            <itunes:email>info@sideproject.by</itunes:email>
          </itunes:owner>
          <language>ru-ru</language>
          <itunes:explicit>no</itunes:explicit>
          <link>http://{domain}/</link>
          <title>My instavideo feed</title>
          <description>
            Description: My instavideo feed
          </description>
          <itunes:summary>
            Description: My instavideo feed
          </itunes:summary>
          <itunes:author>info@sideproject.by</itunes:author>{podcastItemsMarshaller(data.items)}
        </channel>
      </rss>
    }

  private def rssUrl = s"http://$domain/rss/"
  private def videoUrl(id: Long) = s"http://$domain/videos/$id"

  private def podcastItemsMarshaller(items: Seq[PodcastItem]) =
    items.map { data =>
      <item>
        <title>
          {data.name}
        </title>
        <description>
          {data.description}
        </description>

        <itunes:subtitle>
          {data.name}
        </itunes:subtitle>

        <itunes:summary>
          {data.description}
        </itunes:summary>

        <enclosure url={data.downloadUrl} type="video/mp4"/>
        <link>
          {data.downloadUrl}
        </link>
        <guid>
          {videoUrl(data.id)}
        </guid>
        <itunes:author>
          {data.author}
        </itunes:author>
        <pubDate>
          {data.pubDate}
        </pubDate>
      </item>
    }


}
