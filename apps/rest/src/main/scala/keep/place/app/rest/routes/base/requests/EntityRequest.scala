package keep.place.app.rest.routes.base.requests

import keep.place.model.auth.Identity
import spray.routing.RequestContext

case class EntityRequest(ctx: RequestContext, id: Int, identity: Identity)
