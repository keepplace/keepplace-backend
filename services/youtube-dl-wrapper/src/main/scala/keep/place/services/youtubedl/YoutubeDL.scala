package keep.place.services.youtubedl

import java.io.InputStream
import java.util.UUID

import keep.place.model.downloads.DownloadedFileInfo
import keep.place.model.util.Error
import org.json.{JSONException, JSONObject}
import org.slf4j.LoggerFactory

class YoutubeDL {

  val log = LoggerFactory.getLogger(this.getClass)

  def download(url: String): Either[Error, DownloadedFileInfo] = {
    log.debug(s"Downloading video by URL: $url")
    execute(downloadCommand, url)
  }

  def getInfo(url: String): Either[Error, DownloadedFileInfo] = {
    log.debug(s"Fetching information by URL: $url")
    execute(getInfoCommand, url)
  }

  private def execute(command: String, url: String) = {
    val youtubeDlProcess = Runtime.getRuntime.exec(command + url)
    val inputStream: InputStream = youtubeDlProcess.getInputStream

    val jsonData = scala.io.Source.fromInputStream(inputStream).mkString
    log.debug(s"Fetched information by $url: $jsonData")

    youtubeDlProcess.waitFor
    try {
      val youtubeDlJsonOutput: JSONObject = new JSONObject(jsonData)

      val fileName: String = youtubeDlJsonOutput.getString("_filename")
      val title: String = youtubeDlJsonOutput.getString("fulltitle")
      val description: String = youtubeDlJsonOutput.getString("description")
      val author: String = youtubeDlJsonOutput.getString("uploader")
      val pubDate: String = youtubeDlJsonOutput.getString("upload_date") //20150820

      Right(DownloadedFileInfo(fileName, title, description, pubDate, author, youtubeDlJsonOutput.toString(4)))

    } catch {
      case e: JSONException => {
        import UUID._
        val message = s"Page by URL ($url) doesn't have video for downloading."
        log.warn(message)
        Left(Error(message, randomUUID().toString))
      }
    }
  }

  private lazy val getInfoCommand = downloadCommand + " --simulate "
  private lazy val downloadCommand = "youtube-dl -o filestorage/%(uploader)s/%(title)s-%(id)s.%(ext)s -f mp4 --print-json "
}

