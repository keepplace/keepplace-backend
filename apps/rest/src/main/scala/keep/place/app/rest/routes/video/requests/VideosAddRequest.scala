package keep.place.app.rest.routes.video.requests

import keep.place.model.auth.Identity
import keep.place.model.video.AddVideoRequest
import spray.routing.RequestContext

case class VideosAddRequest(ctx: RequestContext, request: AddVideoRequest, user: Identity)
