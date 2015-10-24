package keep.place.model.downloads

case class DownloadedFileInfo(fileName: String, title: String, description: String, pubDate: String, author: String, thumbnail: Option[String], jsonOutput: String)
