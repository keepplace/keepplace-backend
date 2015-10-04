package by.sideproject.videocaster.services.youtubedl

import java.io.InputStream

import by.sideproject.videocaster.model.downloads.DownloadedFileInfo
import org.json.JSONObject

class YoutubeDL {

  def download(url: String): DownloadedFileInfo = execute(downloadCommand, url)
  def getInfo(url: String): DownloadedFileInfo = execute(getInfoCommand, url)

  private def execute(command: String, url: String) = {
    val youtubeDlProcess = Runtime.getRuntime.exec(command + url)
    val inputStream: InputStream = youtubeDlProcess.getInputStream

    val jsonData = scala.io.Source.fromInputStream(inputStream).mkString
    val youtubeDlJsonOutput: JSONObject = new JSONObject(jsonData)

    val fileName: String = youtubeDlJsonOutput.getString("_filename")
    val title: String = youtubeDlJsonOutput.getString("fulltitle")
    val description: String = youtubeDlJsonOutput.getString("description")
    val author: String = youtubeDlJsonOutput.getString("uploader")
    val pubDate: String = youtubeDlJsonOutput.getString("upload_date") //20150820

    youtubeDlProcess.waitFor

    DownloadedFileInfo(fileName, title, description, pubDate, author, youtubeDlJsonOutput.toString(4))
  }

  private lazy val getInfoCommand = downloadCommand + " --simulate "
  private lazy val downloadCommand = "youtube-dl -o filestorage/%(uploader)s/%(title)s-%(id)s.%(ext)s -f mp4 --print-json "
}

