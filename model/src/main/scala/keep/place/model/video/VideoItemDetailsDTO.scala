package keep.place.model.video

case class VideoItemDetailsDTO(id: Int,
                               title: Option[String],
                               description: Option[String],
                               originURL: String,
                               addDate: String,
                               status: String,
                               pubDate: Option[String],
                               author: Option[String],
                               thumbnail: Option[String],
                               downloadUrl: Option[String])