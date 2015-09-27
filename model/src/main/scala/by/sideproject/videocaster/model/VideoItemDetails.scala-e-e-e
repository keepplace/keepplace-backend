package by.sideproject.videocaster.model

case class VideoItemDetails(id: Option[String],
                            title: Option[String],
                            description: Option[String],
                            fileMetaId: Option[String],
                            originURL: String,
                            addDate: String,
                            status: String,
                            pubDate: Option[String],
                            author: Option[String]
                             ) extends BaseObject {

  def isDownloaded = "downloaded".equals(status)
}
