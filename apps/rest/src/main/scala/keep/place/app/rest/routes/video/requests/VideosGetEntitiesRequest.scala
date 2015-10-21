package keep.place.app.rest.routes.video.requests

import spray.routing.RequestContext

case class VideosGetEntitiesRequest(ctx: RequestContext, id: Int)
