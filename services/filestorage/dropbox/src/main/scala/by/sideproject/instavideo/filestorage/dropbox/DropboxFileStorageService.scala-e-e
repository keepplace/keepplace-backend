package by.sideproject.instavideo.filestorage.dropbox

import java.util.Locale
import com.dropbox.core._
import java.io._


class DropboxFileStorageService {
  // Get your app key and secret from the Dropbox developers website.
  val APP_KEY = "INSERT_APP_KEY"
  val APP_SECRET = "INSERT_APP_SECRET"

  def storeFile(filePath: String) = {
    val appInfo: DbxAppInfo = new DbxAppInfo(APP_KEY, APP_SECRET)

    val config: DbxRequestConfig = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString())
    val webAuth: DbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(config, appInfo)

    val webAuth1: DbxWebAuthNoRedirect = new DbxWebAuth(config, appInfo, "http://localhost:8080/dbx-redirect", new DbxStandardSessionStore())

    // Have the user sign in and authorize your app.
    val authorizeUrl: String = webAuth.start()
    println("1. Go to: " + authorizeUrl)
    println("2. Click \"Allow\" (you might have to log in first)")
    println("3. Copy the authorization code.")
    val code: String = new BufferedReader(new InputStreamReader(System.in)).readLine().trim()

    // This will fail if the user enters an invalid authorization code.
    val authFinish: DbxAuthFinish = webAuth.finish(code)
    val accessToken: String = authFinish.accessToken

    val client: DbxClient = new DbxClient(config, accessToken)

    println("Linked account: " + client.getAccountInfo().displayName)

    val inputFile: File = new File(filePath)
    val inputStream: FileInputStream = new FileInputStream(inputFile)
    try {
      val uploadedFile: DbxEntry.File = client.uploadFile("/magnum-opus.txt", DbxWriteMode.add(), inputFile.length(), inputStream)
      println("Uploaded: " + uploadedFile.toString())


      val sharableUrl: String = client.createShareableUrl(uploadedFile.path)
      println(sharableUrl)
    } finally {
      inputStream.close()
    }

    //    DbxEntry.WithChildren listing = client.getMetadataWithChildren("/")
    //    System.out.println("Files in the root path:")
    //    for (DbxEntry child : listing.children) {
    //      System.out.println("	" + child.name + ": " + child.toString())
    //    }
    //
    //    FileOutputStream outputStream = new FileOutputStream("magnum-opus.txt")
    //    try {
    //      DbxEntry.File downloadedFile = client.getFile("/magnum-opus.txt", null,
    //        outputStream)
    //      System.out.println("Metadata: " + downloadedFile.toString())
    //    } finally {
    //      outputStream.close()
    //    }
  }

}
