package by.sideproject.videocaster.model

case class VideoItemDetails(id: Option[String],
                            name: String,
                            description: String,
                            fileMetaId: Option[String],
                            originURL: String,
                            addDate: String,
                            status: String) extends BaseObject {

  def isDownloaded = "downloaded".equals(status)
}
