package by.sideproject.videocaster.app.rest.routes

import akka.actor.ActorContext
import by.sideproject.videocaster.model.rss.{PodcastChannel, PodcastItem}
import by.sideproject.videocaster.services.storage.base.StorageService
import spray.http.MediaTypes._
import spray.http._
import spray.httpx.marshalling.Marshaller

class RssRequestHandler(storageService: StorageService)(implicit context: ActorContext) extends BaseService {

  def actorRefFactory = context

  def route =
    path("rss") {
      implicit val PodcatChannelMarshaller = podcastChannelMarshaller(`application/xml`, `application/xml`)
      get {
        respondWithMediaType(`application/xml`) {
          complete {

            PodcastChannel(
              storageService
                .videoItemDetailsDAO
                .findAll()
                .filter(_.isDownloaded)
                .flatMap(item =>
                item.fileMetaId.flatMap(binaryFileId =>
                  storageService.fileMetaDAO.findOneById(binaryFileId).map(fileMeta =>
                    PodcastItem(item.title.getOrElse(""),
                      item.description.getOrElse(""),
                      item.author.getOrElse(""),
                      item.pubDate.getOrElse(""),
                      fileMeta.downloadURL)
                  )
                )
                )

            )
          }
        }
      }
    }


  def podcastChannelMarshaller(contentType: ContentType, more: ContentType*): Marshaller[PodcastChannel] =
    Marshaller.delegate[PodcastChannel, xml.NodeSeq](contentType +: more: _*) { (data: PodcastChannel) ⇒
      <rss xmlns:itunes="http://www.itunes.com/dtds/podcast-1.0.dtd" xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
        <channel>
          <atom:link href="http://localhost:8080/rss/" type="application/rss+xml" rel="self"></atom:link>
          <itunes:owner>
            <itunes:email>info@sideproject.by</itunes:email>
          </itunes:owner>
          <language>ru-ru</language>
          <itunes:explicit>no</itunes:explicit>
          <link>http://localhost:8080/</link>
          <title>My instavideo feed</title>
          <description>
            Description: My instavideo feed
          </description>
          <itunes:summary>
            Description: My instavideo feed
          </itunes:summary>
          <itunes:author>info@sideproject.by</itunes:author>
          <category>all</category>
          <lastBuildDate>Sun, 27 Sep 2015 18:45:09 +0300</lastBuildDate>{podcastItemsMarshaller(data.items)}
        </channel>
      </rss>
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
          {data.downloadUrl}
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
