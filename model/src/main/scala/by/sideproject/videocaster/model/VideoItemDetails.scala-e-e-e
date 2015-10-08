package by.sideproject.videocaster.model

case class VideoItemDetails(id: Option[Int],
                            title: Option[String],
                            description: Option[String],
                            fileMetaId: Option[Int],
                            originURL: String,
                            addDate: String,
                            status: String,
                            pubDate: Option[String],
                            author: Option[String],
                            note: Option[String]) extends BaseObject[Int] {

  def isDownloaded = "downloaded".equals(status)
}
