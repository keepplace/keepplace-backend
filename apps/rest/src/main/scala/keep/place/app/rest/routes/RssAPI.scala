package keep.place.app.rest.routes

import java.util.UUID

import akka.actor.ActorContext
import keep.place.app.rest.oauth.dropbox.DropboxAuthService
import keep.place.app.rest.routes.base.BaseAPI
import keep.place.model.rss.{PodcastChannel, PodcastItem}
import keep.place.services.storage.base.StorageService
import spray.http._
import spray.httpx.marshalling.Marshaller

import scala.xml.Elem

class RssAPI(storageService: StorageService, domain: String)
                       (implicit context: ActorContext)
  extends DropboxAuthService(storageService.identityDAO, storageService.profileDAO)
  with BaseAPI {

  private val podcastItemDAO = storageService.videoItemDtoDAO

  private implicit def uuidToString(uuid: UUID): String = uuid.toString

  def route =

    path("rss" / JavaUUID) { token =>
      authenticate(rssTokenAuth(token)) { user =>
        implicit val PodcatChannelMarshaller = podcastChannelMarshaller(xml, xml)
        pathEnd {
          get {
            respondWithMediaType(xml) {
              complete {
                user.id.map(id => podcastItemDAO.fetchPodcastItems(id).map(PodcastChannel(_)))
              }
            }
          }
        }
      }
    }

  private def podcastChannelMarshaller(contentType: ContentType, more: ContentType*): Marshaller[PodcastChannel] =
    Marshaller.delegate[PodcastChannel, Elem](contentType +: more: _*) { (data: PodcastChannel) ⇒
      <rss xmlns:itunes="http://www.itunes.com/dtds/podcast-1.0.dtd" xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
        <channel>
          <atom:link href={rssUrl} type="application/rss+xml" rel="self"></atom:link>
          <itunes:owner>
            <itunes:email>admin@keep.place</itunes:email>
          </itunes:owner>
          <language>en-us</language>
          <itunes:explicit>no</itunes:explicit>
          <link>{domain}/</link>
          <title>Keep Place feed</title>
          <description>
            My Keep.Place feed
          </description>
          <itunes:summary>
            My Keep.Place feed
          </itunes:summary>
          <itunes:author>admin@keep.place</itunes:author>
          {podcastItemsMarshaller(data.items)}
        </channel>
      </rss>
    }

  private def rssUrl = s"$domain/rss/"

  private def videoUrl(id: Int) = s"$domain/videos/$id"

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
        {data.thumbnail.map(thumbnail => <itunes:image href={thumbnail}/>).getOrElse("")}
      </item>
    }


}
