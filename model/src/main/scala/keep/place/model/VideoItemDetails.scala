package keep.place.model

case class VideoItemDetails(id: Option[Int],
                            title: Option[String],
                            description: Option[String],
                            fileMetaId: Option[Int],
                            originURL: String,
                            addDate: String,
                            status: String,
                            pubDate: Option[String],
                            author: Option[String],
                            note: Option[String],
                            thumbnail: Option[String],
                            profileId: Int
                             ) extends BaseObject[Int]
