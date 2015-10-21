package keep.place.app.rest.routes.download.requests

import spray.routing.RequestContext

case class DownloadGetFileRequest(ctx: RequestContext, downloadId: String)
