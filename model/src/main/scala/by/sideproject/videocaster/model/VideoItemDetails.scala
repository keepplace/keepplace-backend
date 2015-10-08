package by.sideproject.videocaster.model

case class VideoItemDetails(id: Option[Long],
                            title: Option[String],
                            description: Option[String],
                            fileMetaId: Option[Long],
                            originURL: String,
                            addDate: String,
                            status: String,
                            pubDate: Option[String],
                            author: Option[String],
                            note: Option[String]) extends BaseObject[Long] {

  def isDownloaded = "downloaded".equals(status)
}
